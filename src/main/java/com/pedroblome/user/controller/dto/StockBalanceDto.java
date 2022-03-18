package com.pedroblome.user.controller.dto;

public class StockBalanceDto {
    private long idUser;
    private long idStock;
    private Integer volume;

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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public StockBalanceDto() {

    }

    public StockBalanceDto(long idUser, long idStock, Integer volume) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.volume = volume;
    }

}
