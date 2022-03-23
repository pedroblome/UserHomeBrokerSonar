package com.pedroblome.user.controller;

import java.util.List;

import com.pedroblome.user.controller.dto.UserOrderDto;
import com.pedroblome.user.model.UserOrder;
import com.pedroblome.user.repository.UserOrderRepository;
import com.pedroblome.user.service.BotServices;
import com.pedroblome.user.service.UserOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins ={"http://localhost:8081/"})
@RestController
@RequestMapping("/userOrder")
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    UserOrderRepository userOrderRepository;

    @Autowired
    BotServices botServices;

    @GetMapping
    public List<UserOrder> list() {
        return userOrderRepository.findAll();
    }

    @GetMapping("/buy/{idStock}/{idUser}")
    public List<UserOrder> listBuy(@PathVariable Long idStock, @PathVariable Long idUser) {
        return userOrderRepository.listBuy(idStock, idUser);
    }

    @GetMapping("/sell/{idStock}/{idUser}")
    public List<UserOrder> listSell(@PathVariable Long idStock, @PathVariable Long idUser) {
        return userOrderRepository.listBuy(idStock, idUser);
    }

    @GetMapping("/byUser/{idUser}")
    public List<UserOrder> listOrderById(@PathVariable Long idUser) {
        return userOrderRepository.findOrdersByIdUser(idUser);
    }

    @PutMapping("closeOrder/{orderId}")
    public ResponseEntity<UserOrder> deleteOrder(@RequestHeader("Authorization") String token,
            @PathVariable("orderId") Long orderId) {
        return userOrderService.deleteOrder(orderId, token);

    }

    @GetMapping("populateDataBase")
    public void orderBot(@RequestHeader("Authorization") String token) {
        botServices.createOrdersBot(token);
    }

    @PostMapping()
    public ResponseEntity<UserOrder> saveOrder(@RequestHeader("Authorization") String token,
            @RequestBody UserOrderDto userOrder) {
        UserOrder userOrder2 = new UserOrder(userOrder.getId(), userOrder.getIdUser(), userOrder.getIdStock(),
                userOrder.getStockSymbol(), userOrder.getStockName(), userOrder.getVolume(),
                userOrder.getRemaingVolume(),
                userOrder.getPrice(), userOrder.getType(), userOrder.getStatus(), userOrder.getCreatedOn(),
                userOrder.getUpdatedOn());

        return userOrderService.addOrder(userOrder2, token);
    }

}