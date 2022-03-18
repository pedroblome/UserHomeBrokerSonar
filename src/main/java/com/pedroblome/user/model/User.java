package com.pedroblome.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private BigDecimal dollarBalance;
    private boolean enabled;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private boolean bot;

    public Long getId() {
        return id;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getdollarBalance() {
        return dollarBalance;
    }

    public void setdollarBalance(BigDecimal dollarBalance) {
        this.dollarBalance = dollarBalance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getcreatedOn() {
        return createdOn;
    }

    public void setcreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getupdatedOn() {
        return updatedOn;
    }

    public void setupdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public User() {
        this.enabled = true;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }


}
