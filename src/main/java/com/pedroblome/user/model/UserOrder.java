package com.pedroblome.user.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_order")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    
    private Long id;
    @Column(name="idUser")
    private long idUser;
    @Column(name="idStock")
    private long idStock;
    @Column(name="stockSymbol")
    private String stockSymbol;
    @Column(name="stockName")
    private String stockName;
    @Column(name="volume")
    private Integer volume;
    @Column(name="remaingVolume")
    private Integer remaingVolume;
    @Column(name="price")
    private BigDecimal price;
    private Integer type;
    private Integer status;
    @Column(name="createdOn")
    private Timestamp createdOn;
    @Column(name="updatedOn")
    private Timestamp updatedOn;

    public Long getId() {
        return id;
    }

    public Integer getremaingVolume() {
        return remaingVolume;
    }

    public void setremaingVolume(Integer remaingVolume) {
        this.remaingVolume = remaingVolume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
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
    

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void subractRemaingVolume(){
        this.remaingVolume  -= remaingVolume;
    }
    public void addRemaingVolume(){
        this.remaingVolume += remaingVolume;
    }

    public void closeOrder(){
        this.status=0;
    }
    public UserOrder() {
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());

    }

}