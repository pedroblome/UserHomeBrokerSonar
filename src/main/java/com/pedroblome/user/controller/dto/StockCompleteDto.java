package com.pedroblome.user.controller.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StockCompleteDto {

    private Long id;
    private String stockSymbol;
    private String stockName;
    private BigDecimal askMin;
    private BigDecimal askMax;
    private BigDecimal bidMin;
    private BigDecimal bidMax;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    
    public StockCompleteDto(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getaskMin() {
        return askMin;
    }

    public void setaskMin(BigDecimal askMin) {
        this.askMin = askMin;
    }

    public BigDecimal getaskMax() {
        return askMax;
    }

    public void setaskMax(BigDecimal askMax) {
        this.askMax = askMax;
    }

    public BigDecimal getbidMin() {
        return bidMin;
    }

    public void setbidMin(BigDecimal bidMin) {
        this.bidMin = bidMin;
    }

    public BigDecimal getbidMax() {
        return bidMax;
    }

    public void setbidMax(BigDecimal bidMax) {
        this.bidMax = bidMax;
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
    
    public StockCompleteDto(Long id, String stockSymbol, String stockName, BigDecimal askMin, BigDecimal askMax,
            BigDecimal bidMin, BigDecimal bidMax, Timestamp createdOn, Timestamp updatedOn) {
        this.id = id;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.askMin = askMin;
        this.askMax = askMax;
        this.bidMin = bidMin;
        this.bidMax = bidMax;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }

}
