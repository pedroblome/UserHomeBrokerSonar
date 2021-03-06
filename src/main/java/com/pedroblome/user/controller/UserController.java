package com.pedroblome.user.controller;

import java.util.List;
import java.util.Optional;

import com.pedroblome.user.controller.dto.AppendDto;
import com.pedroblome.user.model.User;
import com.pedroblome.user.repository.UserRepository;
import com.pedroblome.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins ="http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {

        return userRepository.findAll();

    }

    @GetMapping(value = "/{id}")
    public Optional<User> searchStock(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        User addUser = userRepository.save(user);
        return new ResponseEntity<>(addUser, HttpStatus.CREATED);
    }

    @PutMapping("/deposit")
    public ResponseEntity<User> depositUser(@RequestBody AppendDto appendDto) {
        return userService.depositUser(appendDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        new ResponseEntity<>("user Deleted", HttpStatus.OK);

    }

}