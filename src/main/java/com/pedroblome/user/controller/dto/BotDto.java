package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;

public class BotDto {
    private String name;
    private String email;
    private String password;
    private BigDecimal dollarBalance;
    private boolean bot;

    public BotDto() {

    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
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

    public BotDto(String name, String email, String password, BigDecimal dollarBalance, Boolean bot) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dollarBalance = dollarBalance;
        this.bot = bot;

    }

}
