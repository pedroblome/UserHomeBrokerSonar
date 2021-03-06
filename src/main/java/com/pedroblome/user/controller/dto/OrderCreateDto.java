package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderCreateDto {

    private long idUser;
    private long idStock;
    private String stockName;
    private String stockSymbol;
    private BigDecimal price;
    private Integer volume;
    private Integer status;
    private Integer type;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public OrderCreateDto() {

    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdStock() {
        return idStock;
    }

    public void setIdStock(long idStock) {
        this.idStock = idStock;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public OrderCreateDto(long idUser, long idStock, String stockName, String stockSymbol, BigDecimal price,
            Integer volume, Integer status, Integer type, Timestamp createdOn, Timestamp updatedOn) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.volume = volume;
        this.status = status;
        this.type = type;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "OrderCreateDto [createdOn=" + createdOn + ", idStock=" + idStock + ", idUser=" + idUser + ", price="
                + price + ", status=" + status + ", stockName=" + stockName + ", stockSymbol=" + stockSymbol + ", type="
                + type + ", updatedOn=" + updatedOn + ", volume=" + volume + "]";
    }

}
