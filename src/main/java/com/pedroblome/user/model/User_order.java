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
@Table(name = "user_order")
public class User_order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    
    private Long id;
    private long id_user;
    private long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Integer volume;
    private Integer remaing_volume;
    private BigDecimal price;
    private Integer type;
    private Integer status;
    private Timestamp created_on;
    private Timestamp updated_on;

    public Long getId() {
        return id;
    }

    public Integer getRemaing_volume() {
        return remaing_volume;
    }

    public void setRemaing_volume(Integer remaing_volume) {
        this.remaing_volume = remaing_volume;
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

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Timestamp getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Timestamp updated_on) {
        this.updated_on = updated_on;
    }
    

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void subractRemaingVolume(){
        this.remaing_volume  -= remaing_volume;
    }
    public void addRemaingVolume(){
        this.remaing_volume += remaing_volume;
    }

    public void closeOrder(){
        this.status=0;
    }
    public User_order() {
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());

    }

}