package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;
import java.security.Timestamp;

public class OrderCreateDto {

    private long idUser;
    private long idStock;
    private String stockName;
    private String stockSymbol;
    private BigDecimal price;
    private Integer volume;
    private Integer status;
    private Integer type;

    public OrderCreateDto(){
        
    }


    public String getstockSymbol() {
        return stockSymbol;
    }

    public void setstockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getstockName() {
        return stockName;
    }

    public void setstockName(String stockName) {
        this.stockName = stockName;
    }

    public long getidUser() {
        return idUser;
    }

    public void setidUser(long idUser) {
        this.idUser = idUser;
    }

    public long getidStock() {
        return idStock;
    }

    public void setidStock(long idStock) {
        this.idStock = idStock;
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

    public OrderCreateDto(long idUser, long idStock, BigDecimal price, Integer volume, Integer status, Integer type, String stockName, String stockSymbol) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.price = price;
        this.volume = volume;
        this.status = status;
        this.type = type;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
    }

    @Override
    public String toString() {
        return "OrderCreateDto [idStock=" + idStock + ", idUser=" + idUser + ", price=" + price + ", status="
                + status + ", stockName=" + stockName + ", stockSymbol=" + stockSymbol + ", type=" + type
                + ", volume=" + volume + "]";
    }
    
    

}
