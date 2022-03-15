package com.pedroblome.user.controller;

import java.util.List;

import com.pedroblome.user.model.User_order;
import com.pedroblome.user.repository.User_orderRepository;
import com.pedroblome.user.service.User_orderService;

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

@CrossOrigin
@RestController
@RequestMapping("/users_order")
public class User_orderController {

    @Autowired
    private User_orderRepository user_orderRepository;

    @Autowired
    private User_orderService user_orderService;

    @GetMapping
    public List<User_order> list() {
        return user_orderRepository.findAll();
    }

    @GetMapping("/buy/{id_stock}/{id_user}")
    public List<User_order> listBuy(@PathVariable Long id_stock, @PathVariable Long id_user) {
        return user_orderRepository.listBuy(id_stock, id_user);
    }

    @GetMapping("/sell/{id_stock}/{id_user}")
    public List<User_order> listSell(@PathVariable Long id_stock, @PathVariable Long id_user) {
        return user_orderRepository.listBuy(id_stock, id_user);
    }

    @GetMapping("/byUser/{id_user}")
    public List<User_order> listOrderById(@PathVariable Long id_user) {
        return user_orderRepository.findOrdersByIdUser(id_user);
    }

    @PutMapping("closeOrder/{order_id}")
    public ResponseEntity<?> deleteOrder(@RequestHeader("Authorization") String token, @PathVariable("order_id") Long order_id) {
        return user_orderService.deleteOrder(order_id, token);

    }

    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestHeader("Authorization") String token,
            @RequestBody User_order user_order) {
                
        return user_orderService.addOrder(user_order, token);
    }

}