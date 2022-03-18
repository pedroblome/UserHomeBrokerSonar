package com.pedroblome.user.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pedroblome.user.model.UserStockBalance;
import com.pedroblome.user.repository.UserStockBalanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/userStock")
public class UserStockBalanceController {

    @Autowired
    private UserStockBalanceRepository userStockBalanceRepository;

    @GetMapping
    public List<UserStockBalance> list() {
        return userStockBalanceRepository.findAll();
    }

    @GetMapping("/user/{idUser}")
    public List<UserStockBalance> list(@PathVariable("idUser") Long idUser) {

        if (!userStockBalanceRepository.findStockByUser(idUser).isEmpty()) {
            return userStockBalanceRepository.findStockByUser(idUser);
        } else {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user doesnt have stocks or inst registered in the system.");
        }
    }

    @PostMapping
    public ResponseEntity<UserStockBalance> add(
            @RequestBody UserStockBalance stockBalance) {

        UserStockBalance stock = userStockBalanceRepository.save(stockBalance);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);

    }

    public ObjectMapper configureDeserializerDate() {

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));

        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}
