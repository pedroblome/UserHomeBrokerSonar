package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserOrderDto {
    long id;
    long idUser;
    long idStock;
    String stockName;
    String stockSymbol;
    BigDecimal price;
    int volume;
    int remaingVolume;
    int status;
    int type;
    Timestamp createdOn;
    Timestamp updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getRemaingVolume() {
        return remaingVolume;
    }

    public void setRemaingVolume(int remaingVolume) {
        this.remaingVolume = remaingVolume;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public UserOrderDto() {

    }

    public UserOrderDto(long id, long idUser, long idStock, String stockName, String stockSymbol, BigDecimal price,
            int volume, int remaingVolume, int status, int type, Timestamp createdOn, Timestamp updatedOn) {
        this.id = id;
        this.idUser = idUser;
        this.idStock = idStock;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.volume = volume;
        this.remaingVolume = remaingVolume;
        this.status = status;
        this.type = type;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "UserOrderDto [createdOn=" + createdOn + ", id=" + id + ", idStock=" + idStock + ", idUser=" + idUser
                + ", price=" + price + ", remaingVolume=" + remaingVolume + ", status=" + status + ", stockName="
                + stockName + ", stockSymbol=" + stockSymbol + ", type=" + type + ", updatedOn=" + updatedOn
                + ", volume=" + volume + "]";
    }

}
