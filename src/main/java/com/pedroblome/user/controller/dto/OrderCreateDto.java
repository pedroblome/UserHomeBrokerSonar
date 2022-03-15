package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;

public class OrderCreateDto {

    private long id_user;
    private long id_stock;
    private String stock_name;
    private String stock_symbol;
    private BigDecimal price;
    private Integer volume;
    private Integer status;
    private Integer type;
    public OrderCreateDto(){
        
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_stock() {
        return id_stock;
    }

    public void setId_stock(long id_stock) {
        this.id_stock = id_stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public OrderCreateDto(long id_user, long id_stock, BigDecimal price, Integer volume, Integer status, Integer type, String stock_name, String stock_symbol) {
        this.id_user = id_user;
        this.id_stock = id_stock;
        this.price = price;
        this.volume = volume;
        this.status = status;
        this.type = type;
        this.stock_name = stock_name;
        this.stock_symbol = stock_symbol;
    }
    

}
