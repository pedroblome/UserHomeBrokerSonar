package com.pedroblome.user.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "user_stock_balance")
@IdClass(User_stock_ballancePK.class)
public class User_stock_balance {

    @Id
    private Long id_user;
    @Id
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Integer volume;
    private Timestamp created_on;
    private Timestamp updated_on;
    

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Long getId_stock() {
        return id_stock;
    }

    public void setId_stock(Long id_stock) {
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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
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

    public User_stock_balance() {
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }

    public User_stock_balance(Long id_user, Long id_stock, String stock_symbol, String stock_name, Integer volume) {
        this.id_user = id_user;
        this.id_stock = id_stock;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.volume = volume;
    }


    public User_stock_balance(Integer volume) {
        this.volume=volume;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_stock == null) ? 0 : id_stock.hashCode());
        result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());

        return result();
    }

    private int result() {
        return 0;
    }

}
