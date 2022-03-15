package com.pedroblome.user.service;

import java.math.BigDecimal;

import com.pedroblome.user.controller.dto.AppendDto;
import com.pedroblome.user.model.User;
import com.pedroblome.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> depositUser(AppendDto appendDto) {

        if (appendDto.amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount in deposit cannot be less than 0!!");

        } else {
            BigDecimal newDollar_balance = userRepository.getById(appendDto.id).getDollar_balance()
                    .add((appendDto.amount));
            userRepository.getById(appendDto.id).setDollar_balance(newDollar_balance);
            User userSaveDeposit = userRepository.save(userRepository.getById(appendDto.id));

            return ResponseEntity.ok().body(userSaveDeposit);
        }

    }

}
