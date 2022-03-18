package com.pedroblome.user.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "user_stock_balance")
@IdClass(UserStockBalancePK.class)
public class UserStockBalance {

    @Id 
    private Long idUser;
    @Id
    private Long idStock;
    private String stockSymbol;
    private String stockName;
    private Integer volume;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    

    public Long getidUser() {
        return idUser;
    }

    public void setidUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getidStock() {
        return idStock;
    }

    public void setidStock(Long idStock) {
        this.idStock = idStock;
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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
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

    public UserStockBalance() {
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }

    public UserStockBalance(Long idUser, Long idStock, String stockSymbol, String stockName, Integer volume) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.volume = volume;
    }


    public UserStockBalance(Integer volume) {
        this.volume=volume;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idStock == null) ? 0 : idStock.hashCode());
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());

        return result();
    }

    private int result() {
        return 0;
    }

}
