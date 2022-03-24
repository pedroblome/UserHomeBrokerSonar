package com.pedroblome.user.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.pedroblome.user.controller.dto.StockAskBidDto;
import com.pedroblome.user.controller.dto.Stockdto;
import com.pedroblome.user.model.UserOrder;
import com.pedroblome.user.repository.UserOrderRepository;
import com.pedroblome.user.repository.UserRepository;
import com.pedroblome.user.repository.UserStockBalanceRepository;

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
public class UserOrderService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserStockBalanceRepository userStockBalanceRepository;

  @Autowired
  private UserOrderRepository userOrderRepository;

  public ResponseEntity<UserOrder> addOrder(UserOrder userOrder, String token) {

    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);

    Stockdto stockdto = new Stockdto(userOrder.getidStock(),
        userOrder.getstockName(),
        userOrder.getstockSymbol());

    if (userOrder.getType() == 1 || userOrder.getType() == 0) {
      if (userOrder.getStatus() == 1) {
        if (checkUser(userOrder) == 0) {
          if (userOrder.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {

            if (Boolean.TRUE.equals(checkStock(stockdto, token))) {

              userOrder.setremaingVolume(userOrder.getVolume());
              if (userOrder.getType() == 0) {
                int volumeUpdate = userStockBalanceRepository
                    .findByUserStock(userOrder.getidUser(), userOrder.getidStock())
                    .getVolume() - userOrder.getVolume();
                userStockBalanceRepository.findByUserStock(userOrder.getidUser(), userOrder.getidStock())
                    .setVolume(volumeUpdate);
                userStockBalanceRepository.findByUserStock(userOrder.getidUser(), userOrder.getidStock())
                    .setupdatedOn(timestamp);

              }

              BigDecimal totalOrder = userOrder.getPrice().multiply(BigDecimal.valueOf(userOrder.getVolume()));
              BigDecimal newBalance = userRepository.getById(userOrder.getidUser()).getdollarBalance()
                  .subtract(totalOrder);

              if (userOrder.getType() == 1) {
                userRepository.getById(userOrder.getidUser()).setdollarBalance(newBalance);
              }
              matchOrder(userOrder);

              try {

                StockAskBidDto newAskBid = this.checkAskBid(userOrder);
                RestTemplate restTemplate = new RestTemplate();
                URI uri = new URI("http://localhost:8089/stocks/askbid/" + userOrder.getidStock());
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);
                headers.set("Content-Type", "application/json");

                HttpEntity requestEntity = new HttpEntity(newAskBid, headers);

                ResponseEntity<StockAskBidDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    StockAskBidDto.class);

              } catch (URISyntaxException e) {
                e.printStackTrace();
              }
              return ResponseEntity.ok().body(userOrder);

            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "stock_name or stockSymbol doesnt match with given id_stock!!");
          }
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Price of order must be a number and bigger than 0!!");

        }
        if (checkUser(userOrder) == 10) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Inexistent idUser!!");

        }
        if (checkUser(userOrder) == 11) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Insuficient dollar balance!!");

        }
        if (checkUser(userOrder) == 12) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Insuficient stock balance!!");

        }

      }
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Cannot create order with status diferent of 1!!");
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Cannot create order with type diferent of 1 or 0!!");
  }

  public ResponseEntity<UserOrder> deleteOrder(@PathVariable("orderId") Long orderId, String token) {
    UserOrder userOrder = userOrderRepository.getById(orderId);

    if (userOrderRepository.getById(orderId).getType() == 1) {
      BigDecimal dollarBalanceUser = userRepository.getById(userOrder.getidUser()).getdollarBalance();
      BigDecimal reversal = userOrder.getPrice().multiply(BigDecimal.valueOf(userOrder.getremaingVolume()));
      userRepository.getById(userOrder.getidUser()).setdollarBalance(dollarBalanceUser.add(reversal));
      userOrder.setStatus(0);

    } else {
      int oldVolume = userStockBalanceRepository.findByUserStock(userOrder.getidUser(), userOrder.getidStock())
          .getVolume();
      userStockBalanceRepository.findByUserStock(userOrder.getidUser(), userOrder.getidStock())
          .setVolume(oldVolume + userOrder.getremaingVolume());
      userOrder.setStatus(0);

    }

    try {

      StockAskBidDto newAskBid = this.checkAskBid(userOrder);
      RestTemplate restTemplate = new RestTemplate();
      URI uri;
      uri = new URI("http://localhost:8089/stocks/askbid/" + userOrder.getidStock());
      HttpHeaders headers = new HttpHeaders();

      headers.set("Authorization", token);
      headers.set("Content-Type", "application/json");

      HttpEntity requestEntity = new HttpEntity(newAskBid, headers);

      ResponseEntity<StockAskBidDto> response = restTemplate.exchange(
          uri,
          HttpMethod.PUT,
          requestEntity,
          StockAskBidDto.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    UserOrder orderDelete = userOrderRepository.save(userOrder);

    return ResponseEntity.ok().body(orderDelete);
  }

  public int checkUser(@RequestBody UserOrder userOrder) {

    if (userRepository.existsById(userOrder.getidUser())) {
      BigDecimal totalOrder = userOrder.getPrice().multiply(BigDecimal.valueOf(userOrder.getVolume()));
      if (userOrder.getType() == 1
          && totalOrder.compareTo(userRepository.getById(userOrder.getidUser()).getdollarBalance()) <= 0) {
        return 0;

      }
      if (userOrder.getType() == 1
          && totalOrder.compareTo(userRepository.getById(userOrder.getidUser()).getdollarBalance()) >= 0) {
        return 11;

      }
      if (userOrder.getType() == 0) {
        try {
          Integer volume = userStockBalanceRepository.volume(userOrder.getidUser(), userOrder.getidStock())
              .getVolume();
          if (volume != null && volume >= userOrder.getVolume()) {
            return 0;
          }
          return 12;

        } catch (NullPointerException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "User doesnt have enough of this stock to sell!");
        }
      }

    }
    return 10;

  }

  public Boolean checkStock(Stockdto stockdto, String token) {
    try {

      RestTemplate restTemplate = new RestTemplate();
      URI uri;
      uri = new URI("http://localhost:8089/stocks/dto/" + stockdto.getId());

      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      headers.set("Authorization", token);

      HttpEntity requestEntity = new HttpEntity(stockdto, headers);

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

  public ResponseEntity<UserOrder> matchOrder(@RequestBody UserOrder userOrder) {
    List<UserOrder> orderSell = userOrderRepository.listSell(userOrder.getidStock(), userOrder.getidUser());
    List<UserOrder> orderBuy = userOrderRepository.listBuy(userOrder.getidStock(), userOrder.getidUser());

    if (userOrder.getType() == 0) {
      for (UserOrder buyOrder : orderBuy) {

        if (userOrder.getPrice().compareTo(buyOrder.getPrice()) < 0
            || userOrder.getPrice().compareTo(buyOrder.getPrice()) == 0) {

          int buyerVolume = buyOrder.getremaingVolume();
          int sellerVolume = userOrder.getremaingVolume();
          int setVolume;
          if (buyerVolume >= sellerVolume) {
            setVolume = sellerVolume;
          } else {
            setVolume = buyerVolume;
          }
          LocalDateTime now = LocalDateTime.now();
          Timestamp timestamp = Timestamp.valueOf(now);

          BigDecimal dollarBalanceBuyer = userRepository.getById(buyOrder.getidUser()).getdollarBalance();
          BigDecimal dollarBalanceSeller = userRepository.getById(userOrder.getidUser()).getdollarBalance();
          BigDecimal dollarOrderTotal = userOrder.getPrice().multiply(BigDecimal.valueOf(setVolume));
          BigDecimal reversal = buyOrder.getPrice().subtract(userOrder.getPrice())
              .multiply(BigDecimal.valueOf(setVolume));

          userOrder.setremaingVolume(userOrder.getremaingVolume() - setVolume);
          buyOrder.setremaingVolume(buyOrder.getremaingVolume() - setVolume);
          userOrder.setupdatedOn(timestamp);
          buyOrder.setupdatedOn(timestamp);

          userRepository.getById(userOrder.getidUser()).setupdatedOn(timestamp);
          userRepository.getById(buyOrder.getidUser()).setupdatedOn(timestamp);

          System.out.println("entrando no erro");
          userOrder.setupdatedOn(timestamp);
          buyOrder.setupdatedOn(timestamp);
          System.out.println("saindo do felipe");

          userRepository.getById(userOrder.getidUser())
              .setdollarBalance(dollarBalanceSeller.add(dollarOrderTotal));

          if (buyOrder.getPrice().compareTo(userOrder.getPrice()) > 0) {
            userRepository.getById(buyOrder.getidUser())
                .setdollarBalance(dollarBalanceBuyer.add(reversal));
          }
          if (userOrder.getremaingVolume() == 0) {
            userOrder.closeOrder();
          }
          if (buyOrder.getremaingVolume() == 0) {
            buyOrder.closeOrder();
          }
          try {
            userStockBalanceRepository.findByUserStock(userOrder.getidUser(),
                userOrder.getidStock())
                .setupdatedOn(timestamp);
            userStockBalanceRepository.findByUserStock(buyOrder.getidUser(),
                buyOrder.getidStock())
                .setupdatedOn(timestamp);
            Integer updateVolumeStock = userStockBalanceRepository
                .findByUserStock(buyOrder.getidUser(), buyOrder.getidStock()).getVolume() + setVolume;

            userStockBalanceRepository.findByUserStock(buyOrder.getidUser(), buyOrder.getidStock())
                .setVolume(updateVolumeStock);

          } catch (NullPointerException e) {

            userStockBalanceRepository.createStockBalance(buyOrder.getidStock(), buyOrder.getidUser(),
                buyOrder.getupdatedOn(),
                buyOrder.getstockName(), buyOrder.getstockSymbol(), buyOrder.getupdatedOn(), setVolume);
          }
        }
      }
    } else {
      for (UserOrder sellOrder : orderSell) {
        if (sellOrder.getPrice().compareTo(userOrder.getPrice()) < 0
            || sellOrder.getPrice().compareTo(userOrder.getPrice()) == 0) {

          int buyerVolume = userOrder.getremaingVolume();
          int sellerVolume = sellOrder.getremaingVolume();
          int setVolume;
          if (buyerVolume >= sellerVolume) {
            setVolume = sellerVolume;
          } else {
            setVolume = buyerVolume;
          }
          LocalDateTime now = LocalDateTime.now();
          Timestamp timestamp = Timestamp.valueOf(now);

          BigDecimal dollarBalanceBuyer = userRepository.getById(userOrder.getidUser()).getdollarBalance();
          BigDecimal dollarBalanceSeller = userRepository.getById(sellOrder.getidUser()).getdollarBalance();
          BigDecimal dollarOrderTotal = sellOrder.getPrice().multiply(BigDecimal.valueOf(setVolume));
          BigDecimal reversal = sellOrder.getPrice().subtract(userOrder.getPrice())
              .multiply(BigDecimal.valueOf(setVolume));

          userOrder.setremaingVolume(userOrder.getremaingVolume() - setVolume);
          sellOrder.setremaingVolume(sellOrder.getremaingVolume() - setVolume);
          userOrder.setupdatedOn(timestamp);
          sellOrder.setupdatedOn(timestamp);

          userRepository.getById(userOrder.getidUser()).setupdatedOn(timestamp);
          userRepository.getById(sellOrder.getidUser()).setupdatedOn(timestamp);

          System.out.println("entrando no erro");
          userOrder.setupdatedOn(timestamp);
          sellOrder.setupdatedOn(timestamp);
          System.out.println("saindo do felipe");

          userRepository.getById(sellOrder.getidUser())
              .setdollarBalance(dollarBalanceSeller.add(dollarOrderTotal));

          if (sellOrder.getPrice().compareTo(userOrder.getPrice()) < 0) {
            userRepository.getById(sellOrder.getidUser())
                .setdollarBalance(dollarBalanceBuyer.add(reversal));
          }

          if (userOrder.getremaingVolume() == 0) {
            userOrder.closeOrder();
          }
          if (sellOrder.getremaingVolume() == 0) {
            sellOrder.closeOrder();
          }
          try {
            userStockBalanceRepository.findByUserStock(userOrder.getidUser(),
                userOrder.getidStock())
                .setupdatedOn(timestamp);
            userStockBalanceRepository.findByUserStock(sellOrder.getidUser(),
                sellOrder.getidStock())
                .setupdatedOn(timestamp);
            Integer updateVolumeStock = userStockBalanceRepository
                .findByUserStock(userOrder.getidUser(), userOrder.getidStock()).getVolume() + setVolume;

            userStockBalanceRepository.findByUserStock(userOrder.getidUser(), userOrder.getidStock())
                .setVolume(updateVolumeStock);

          } catch (NullPointerException e) {
            userStockBalanceRepository.createStockBalance(userOrder.getidStock(), userOrder.getidUser(),
                userOrder.getupdatedOn(),
                userOrder.getstockName(), userOrder.getstockSymbol(), userOrder.getupdatedOn(), setVolume);
          }
        }
      }
    }
    UserOrder orderMatch = userOrderRepository.save(userOrder);
    return ResponseEntity.ok().body(orderMatch);

  }

  public StockAskBidDto checkAskBid(@RequestBody UserOrder userOrder) {
    BigDecimal askmin = null;
    BigDecimal askmax = null;
    BigDecimal bidmin = null;
    BigDecimal bidmax = null;

    if (Boolean.TRUE.equals(userOrderRepository.orderStockBuy(userOrder.getidStock()))) {
      bidmin = userOrderRepository.bidMin(userOrder.getidStock());
      bidmax = userOrderRepository.bidMax(userOrder.getidStock());

    }
    if (Boolean.TRUE.equals(userOrderRepository.orderStockSell(userOrder.getidStock()))) {
      askmin = userOrderRepository.askMin(userOrder.getidStock());
      askmax = userOrderRepository.askMax(userOrder.getidStock());
    }

    return new StockAskBidDto(userOrder.getidStock(), askmin, askmax, bidmin, bidmax,
        userOrder.getupdatedOn());

  }
}