package com.pedroblome.user.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.pedroblome.user.controller.dto.StockAskBidDto;
import com.pedroblome.user.controller.dto.Stockdto;
import com.pedroblome.user.model.User_order;
import com.pedroblome.user.repository.UserRepository;
import com.pedroblome.user.repository.User_orderRepository;
import com.pedroblome.user.repository.User_stock_balanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class User_orderService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private User_stock_balanceRepository user_stock_balanceRepository;

  @Autowired
  private User_orderRepository user_orderRepository;

  @Autowired
  private BotServices botServices;

  // verifica usuario

  public ResponseEntity<?> addOrder(User_order user_order, String token) {

    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);

    Stockdto stockdto = new Stockdto(user_order.getId_stock(),
        user_order.getStock_name(),
        user_order.getStock_symbol());

    if (user_order.getType() == 1 || user_order.getType() == 0) {
      if (user_order.getStatus() == 1) {
        if (checkUser(user_order)) {
          if (user_order.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {

            if (checkStock(stockdto, token, user_order)) {

              user_order.setRemaing_volume(user_order.getVolume());
              if (user_order.getType() == 0) {
                int volumeUpdate = user_stock_balanceRepository
                    .findByUserStock(user_order.getId_user(), user_order.getId_stock())
                    .getVolume() - user_order.getVolume();
                user_stock_balanceRepository.findByUserStock(user_order.getId_user(), user_order.getId_stock())
                    .setVolume(volumeUpdate);
                user_stock_balanceRepository.findByUserStock(user_order.getId_user(), user_order.getId_stock())
                    .setUpdated_on(timestamp);

              }

              // criando o dto e a conexao

              BigDecimal totalOrder = user_order.getPrice().multiply(BigDecimal.valueOf(user_order.getVolume()));
              BigDecimal newBalance = userRepository.getById(user_order.getId_user()).getDollar_balance()
                  .subtract(totalOrder);

              if (user_order.getType() == 1) {
                userRepository.getById(user_order.getId_user()).setDollar_balance(newBalance);
              }
              botServices.createOrdersBot(token);
              User_order orderSave = user_orderRepository.save(user_order);
              matchOrder(user_order, token);

              try {

                StockAskBidDto newAskBid = this.checkAskBid(user_order);
                RestTemplate restTemplate = new RestTemplate();
                URI uri;
                uri = new URI("http://localhost:8089/stocks/askbid/" + user_order.getId_stock());
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);
                headers.set("Content-Type", "application/json");

                // (instancia,cabecalho)
                HttpEntity requestEntity = new HttpEntity(newAskBid, headers);

                // HttpMethod.PUT , HttpMethod.POST , HttpMethod.GET
                ResponseEntity<StockAskBidDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    StockAskBidDto.class);

              } catch (URISyntaxException e) {
                e.printStackTrace();
              }
              return ResponseEntity.ok().body(orderSave);

            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "stock_name or stock_symbol doesnt match with given id_stock!!");
          }
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Price of order must be a number and bigger than 0!!");

        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Inexistent id or insuficient balance or insuficient volume for stockSell!!");

      }
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Cannot create order with status diferent of 1!!");
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Cannot create order with type diferent of 1 or 0!!");
  }

  public ResponseEntity<User_order> deleteOrder(@PathVariable("order_id") Long order_id, String token) {

    User_order user_order = user_orderRepository.getById(order_id);
    BigDecimal dollarBalanceUser = userRepository.getById(user_order.getId_user()).getDollar_balance();
    BigDecimal reversal = user_order.getPrice().multiply(BigDecimal.valueOf(user_order.getRemaing_volume()));
    userRepository.getById(user_order.getId_user()).setDollar_balance(dollarBalanceUser.add(reversal));
    user_order.setStatus(0);
    User_order orderDelete = user_orderRepository.save(user_order);

    try {

      StockAskBidDto newAskBid = this.checkAskBid(user_order);
      RestTemplate restTemplate = new RestTemplate();
      URI uri;
      uri = new URI("http://localhost:8089/stocks/askbid/" + user_order.getId_stock());
      HttpHeaders headers = new HttpHeaders();

      headers.set("Authorization", token);
      headers.set("Content-Type", "application/json");

      // (instancia,cabecalho)
      HttpEntity requestEntity = new HttpEntity(newAskBid, headers);

      // HttpMethod.PUT , HttpMethod.POST , HttpMethod.GET
      ResponseEntity<StockAskBidDto> response = restTemplate.exchange(
          uri,
          HttpMethod.PUT,
          requestEntity,
          StockAskBidDto.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok().body(orderDelete);
  }

  public boolean checkUser(@RequestBody User_order user_order) {

    if (userRepository.existsById(user_order.getId_user())) {
      BigDecimal totalOrder = user_order.getPrice().multiply(BigDecimal.valueOf(user_order.getVolume()));
      if (user_order.getType() == 1
          && totalOrder.compareTo(userRepository.getById(user_order.getId_user()).getDollar_balance()) <= 0) {
        return true;

      }
      if (user_order.getType() == 0) {
        try {
          Integer volume = user_stock_balanceRepository.volume(user_order.getId_user(), user_order.getId_stock())
              .getVolume();
          if (volume != null && volume >= user_order.getVolume()) {
            return true;
          }
          return false;

        } catch (NullPointerException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "User doesnt have enoug of this stock to sell!");
        }
      }

    }
    return false;

  }

  // consulta o body de um post order, name=id=symbol

  public Boolean checkStock(Stockdto stockdto, String token, User_order user_order) {
    try {

      // URL DESTINO
      RestTemplate restTemplate = new RestTemplate();
      URI uri;
      uri = new URI("http://localhost:8089/stocks/dto/" + stockdto.getId());

      // CABEÇALHO
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      headers.set("Authorization", token);

      // (instancia,cabecalho)
      HttpEntity requestEntity = new HttpEntity(stockdto, headers);

      // HttpMethod.PUT , HttpMethod.POST , HttpMethod.GET
      ResponseEntity<Boolean> response = restTemplate.exchange(
          uri,
          HttpMethod.POST,
          requestEntity,
          Boolean.class);
      return response.getBody();

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return false;

  }

  public ResponseEntity<?> matchOrder(@RequestBody User_order user_order, String token) {
    List<User_order> orderSell = user_orderRepository.listSell(user_order.getId_stock(), user_order.getId_user());
    List<User_order> orderBuy = user_orderRepository.listBuy(user_order.getId_stock(), user_order.getId_user());

    // user_order és o vendedor
    if (user_order.getType() == 0) {// vendedor é User_order logo, comprador = buyOrder
      for (User_order buyOrder : orderBuy) {

        if (user_order.getPrice().compareTo(buyOrder.getPrice()) < 0
            || user_order.getPrice().compareTo(buyOrder.getPrice()) == 0) {
          System.out.println("-----------------");
          System.out.println("deu match com alguma do repository");

          int buyerVolume = buyOrder.getRemaing_volume();
          int sellerVolume = user_order.getRemaing_volume();
          int setVolume;
          if (buyerVolume >= sellerVolume) {
            setVolume = sellerVolume;
          } else {
            setVolume = buyerVolume;
          }
          LocalDateTime now = LocalDateTime.now();
          Timestamp timestamp = Timestamp.valueOf(now);

          BigDecimal dollarBalanceBuyer = userRepository.getById(buyOrder.getId_user()).getDollar_balance();
          BigDecimal dollarBalanceSeller = userRepository.getById(user_order.getId_user()).getDollar_balance();
          BigDecimal dollarOrderTotal = user_order.getPrice().multiply(BigDecimal.valueOf(setVolume));
          BigDecimal reversal = buyOrder.getPrice().subtract(user_order.getPrice())
              .multiply(BigDecimal.valueOf(setVolume));

          user_order.setRemaing_volume(user_order.getRemaing_volume() - setVolume);
          buyOrder.setRemaing_volume(buyOrder.getRemaing_volume() - setVolume);
          user_order.setUpdated_on(timestamp);
          buyOrder.setUpdated_on(timestamp);

          userRepository.getById(user_order.getId_user()).setUpdated_on(timestamp);
          userRepository.getById(buyOrder.getId_user()).setUpdated_on(timestamp);

          user_orderRepository.getById(user_order.getId()).setUpdated_on(timestamp);
          user_orderRepository.getById(buyOrder.getId()).setUpdated_on(timestamp);

          // dollar balance vendedor
          userRepository.getById(user_order.getId_user())
              .setDollar_balance(dollarBalanceSeller.add(dollarOrderTotal));

          if (buyOrder.getPrice().compareTo(user_order.getPrice()) > 0) {
            userRepository.getById(buyOrder.getId_user())
                .setDollar_balance(dollarBalanceBuyer.add(reversal));
          }
          if (user_order.getRemaing_volume() == 0) {
            user_order.closeOrder();
          }
          if (buyOrder.getRemaing_volume() == 0) {
            buyOrder.closeOrder();
          }
          try {
            user_stock_balanceRepository.findByUserStock(user_order.getId_user(),
                user_order.getId_stock())
                .setUpdated_on(timestamp);
            user_stock_balanceRepository.findByUserStock(buyOrder.getId_user(),
                buyOrder.getId_stock())
                .setUpdated_on(timestamp);
            Integer updateVolumeStock = user_stock_balanceRepository
                .findByUserStock(buyOrder.getId_user(), buyOrder.getId_stock()).getVolume() + setVolume;

            user_stock_balanceRepository.findByUserStock(buyOrder.getId_user(), buyOrder.getId_stock())
                .setVolume(updateVolumeStock);

          } catch (NullPointerException e) {
            // cria carteira caso o comprador n tenha
            user_stock_balanceRepository.createStockBalance(buyOrder.getId_stock(), buyOrder.getId_user(),
                buyOrder.getUpdated_on(),
                buyOrder.getStock_name(), buyOrder.getStock_symbol(), buyOrder.getUpdated_on(), setVolume);
          }
        }
      }
    } else {// vendedor é sellOrder, comprador user_Order.
      for (User_order sellOrder : orderSell) {
        if (sellOrder.getPrice().compareTo(user_order.getPrice()) < 0
            || sellOrder.getPrice().compareTo(user_order.getPrice()) == 0) {
          System.out.println("-----------------");
          System.out.println("deu match com alguma do repository");

          int buyerVolume = user_order.getRemaing_volume();
          int sellerVolume = sellOrder.getRemaing_volume();
          int setVolume;
          if (buyerVolume >= sellerVolume) {
            setVolume = sellerVolume;
          } else {
            setVolume = buyerVolume;
          }
          LocalDateTime now = LocalDateTime.now();
          Timestamp timestamp = Timestamp.valueOf(now);

          BigDecimal dollarBalanceBuyer = userRepository.getById(user_order.getId_user()).getDollar_balance();
          BigDecimal dollarBalanceSeller = userRepository.getById(sellOrder.getId_user()).getDollar_balance();
          BigDecimal dollarOrderTotal = sellOrder.getPrice().multiply(BigDecimal.valueOf(setVolume));
          BigDecimal reversal = sellOrder.getPrice().subtract(user_order.getPrice())
              .multiply(BigDecimal.valueOf(setVolume));

          user_order.setRemaing_volume(user_order.getRemaing_volume() - setVolume);
          sellOrder.setRemaing_volume(sellOrder.getRemaing_volume() - setVolume);
          user_order.setUpdated_on(timestamp);
          sellOrder.setUpdated_on(timestamp);

          userRepository.getById(user_order.getId_user()).setUpdated_on(timestamp);
          userRepository.getById(sellOrder.getId_user()).setUpdated_on(timestamp);

          user_orderRepository.getById(user_order.getId()).setUpdated_on(timestamp);
          user_orderRepository.getById(sellOrder.getId()).setUpdated_on(timestamp);

          // dollar balance do vendedor
          userRepository.getById(sellOrder.getId_user())
              .setDollar_balance(dollarBalanceSeller.add(dollarOrderTotal));

          if (sellOrder.getPrice().compareTo(user_order.getPrice()) < 0) {
            userRepository.getById(sellOrder.getId_user())
                .setDollar_balance(dollarBalanceBuyer.add(reversal));
          }

          if (user_order.getRemaing_volume() == 0) {
            user_order.closeOrder();
          }
          if (sellOrder.getRemaing_volume() == 0) {
            sellOrder.closeOrder();
          }
          try {
            user_stock_balanceRepository.findByUserStock(user_order.getId_user(),
                user_order.getId_stock())
                .setUpdated_on(timestamp);
            user_stock_balanceRepository.findByUserStock(sellOrder.getId_user(),
                sellOrder.getId_stock())
                .setUpdated_on(timestamp);
            Integer updateVolumeStock = user_stock_balanceRepository
                .findByUserStock(user_order.getId_user(), user_order.getId_stock()).getVolume() + setVolume;

            user_stock_balanceRepository.findByUserStock(user_order.getId_user(), user_order.getId_stock())
                .setVolume(updateVolumeStock);

          } catch (NullPointerException e) {
            user_stock_balanceRepository.createStockBalance(user_order.getId_stock(), user_order.getId_user(),
                user_order.getUpdated_on(),
                user_order.getStock_name(), user_order.getStock_symbol(), user_order.getUpdated_on(), setVolume);
          }
        }
      }
    }
    User_order orderMatch = user_orderRepository.save(user_order);
    return ResponseEntity.ok().body(orderMatch);

  }

  public StockAskBidDto checkAskBid(@RequestBody User_order user_order) {
    // turn price == null if orders is void.

    BigDecimal askmin = null;
    BigDecimal askmax = null;
    BigDecimal bidmin = null;
    BigDecimal bidmax = null;

    // att o bid e ask se nao forem nulos com base no rpository de cada stock.
    if (user_orderRepository.orderStockBuy(user_order.getId_stock())) {
      bidmin = user_orderRepository.bidMin(user_order.getId_stock());
      bidmax = user_orderRepository.bidMax(user_order.getId_stock());

    }
    if (user_orderRepository.orderStockSell(user_order.getId_stock())) {
      askmin = user_orderRepository.askMin(user_order.getId_stock());
      askmax = user_orderRepository.askMax(user_order.getId_stock());
    }

    StockAskBidDto updateStock = new StockAskBidDto(user_order.getId_stock(), askmin, askmax, bidmin, bidmax,
        user_order.getUpdated_on());
    return updateStock;

  }
}