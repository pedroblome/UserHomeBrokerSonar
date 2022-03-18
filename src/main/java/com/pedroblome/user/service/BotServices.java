package com.pedroblome.user.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import javax.validation.Valid;

import com.pedroblome.user.controller.dto.BotDto;
import com.pedroblome.user.controller.dto.OrderCreateDto;
import com.pedroblome.user.controller.dto.StockCompleteDto;
import com.pedroblome.user.repository.UserOrderRepository;
import com.pedroblome.user.repository.UserRepository;
import com.pedroblome.user.repository.UserStockBalanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BotServices {

    @Autowired
    UserOrderRepository userOrderRepository;

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    UserRepository userRepository;

    public void createOrdersBot(String token) {
        Random random = new Random();
        while (userRepository.sevenOrMoreOrdersBot() <= 10) {

            try {
                String name = "bot" + (random.nextInt(100, 300));
                String email = "bot" + (random.nextInt(100, 300)) + "@email.com";
                String password = "passwordbot" + (random.nextInt(100, 300));
                BigDecimal dollarBalance = BigDecimal.valueOf(0);
                Boolean bot = true;
                BotDto botDto = new BotDto(name, email, password, dollarBalance, bot);

                RestTemplate restTemplate = new RestTemplate();
                URI uri;
                uri = new URI("http://localhost:8088/user");
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", token);
                headers.set("Content-Type", "application/json");

                HttpEntity requestEntity = new HttpEntity(botDto, headers);

                ResponseEntity<BotDto> response = restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        requestEntity,
                        BotDto.class);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

        for (long idStock = 1; idStock <= 720; idStock++) {

            try {

                RestTemplate restTemplateStock = new RestTemplate();
                URI uriStock;
                uriStock = new URI("http://localhost:8089/stocks/" + idStock);
                HttpHeaders headersStock = new HttpHeaders();
                headersStock.set("Authorization", token);
                headersStock.set("Content-Type", "application/json");

                HttpEntity requestEntityStock = new HttpEntity(headersStock);

                ResponseEntity<StockCompleteDto> responseStock = restTemplateStock.exchange(
                        uriStock,
                        HttpMethod.GET,
                        requestEntityStock,
                        StockCompleteDto.class);
                String stockName = responseStock.getBody().getstockName();
                String stockSymbol = responseStock.getBody().getstockSymbol();

                int status = 1;
                int volume = random.nextInt(1, 11);
                long idUser = userRepository.randomBot();
                int type = random.nextInt(0, 2);
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
                if (type == 1) {
                    BigDecimal price = BigDecimal.valueOf(random.nextDouble(16, 20));
                    userRepository.getById(idUser)
                            .setdollarBalance(price.multiply(BigDecimal.valueOf(volume)).add(BigDecimal.valueOf(10)));
                    userRepository.save(userRepository.getById(idUser));
                    OrderCreateDto orderCreateDto = new OrderCreateDto(idUser, idStock, price, volume, status,
                            type, stockName, stockSymbol);
                    RestTemplate restTemplateOrder = new RestTemplate();
                    HttpHeaders headersOrder = new HttpHeaders();

                    headersOrder.set("Authorization", token);
                    headersOrder.set("Content-Type", "application/json");

                    HttpEntity requestEntityOrder = new HttpEntity(orderCreateDto, headersOrder);
                    URI uriOrder;

                    uriOrder = new URI("http://localhost:8088/userOrder");
                    ResponseEntity<OrderCreateDto> responseOrder = restTemplateOrder.exchange(
                            "http://localhost:8088/userOrder",
                            HttpMethod.POST,
                            requestEntityOrder,
                            OrderCreateDto.class);

                } else {

                    BigDecimal price = BigDecimal.valueOf(random.nextDouble(12, 15.5));
                    if (userStockBalanceRepository.findByUserStock(idUser, idStock) == null) {
                        userStockBalanceRepository.createStockBalance(idStock, idUser, timestamp, stockName,
                                stockSymbol, timestamp, volume);
                        userRepository.save(userRepository.getById(idUser));

                    } else {
                        userStockBalanceRepository.findByUserStock(idUser, idStock).setVolume(volume);
                        userRepository.save(userRepository.getById(idUser));
                    }
                    OrderCreateDto orderCreateDto = new OrderCreateDto(idUser, idStock, price,
                            volume, status,
                            type, stockName, stockSymbol);

                    RestTemplate restTemplateOrder = new RestTemplate();
                    URI uriOrder;
                    uriOrder = new URI("http://localhost:8088/userOrder");
                    HttpHeaders headersOrder = new HttpHeaders();
                    headersOrder.set("Authorization", token);
                    headersOrder.set("Content-Type", "application/json");

                    // (instancia,cabecalho)
                    HttpEntity requestEntityOrder = new HttpEntity(orderCreateDto, headersOrder);

                    // HttpMethod.PUT , HttpMethod.POST , HttpMethod.GET
                    ResponseEntity<OrderCreateDto> responseOrder = restTemplateOrder.exchange(
                            uriOrder,
                            HttpMethod.POST,
                            requestEntityOrder,
                            OrderCreateDto.class);

                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

    }

}
