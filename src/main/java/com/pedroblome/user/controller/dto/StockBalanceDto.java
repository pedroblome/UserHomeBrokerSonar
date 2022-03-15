package com.pedroblome.user.controller.dto;

public class StockBalanceDto {
    private long id_user;
    private long id_stock;
    private Integer volume;

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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public StockBalanceDto() {

    }

    public StockBalanceDto(long id_user, long id_stock, Integer volume) {
        this.id_user = id_user;
        this.id_stock = id_stock;
        this.volume = volume;
    }

}
