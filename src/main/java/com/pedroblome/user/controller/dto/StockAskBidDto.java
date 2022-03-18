package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockAskBidDto {
    private long id;
    private BigDecimal askMin;
    private BigDecimal askMax;
    private BigDecimal bidMin;
    private BigDecimal bidMax;
    private Timestamp updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Timestamp getupdatedOn() {
        return updatedOn;
    }

    public void setupdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public StockAskBidDto(){
        
    }


    public StockAskBidDto(long id, BigDecimal askMin, BigDecimal askMax, BigDecimal bidMin, BigDecimal bidMax,
            Timestamp updatedOn) {
        this.id = id;
        this.askMin = askMin;
        this.askMax = askMax;
        this.bidMin = bidMin;
        this.bidMax = bidMax;
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "StockAskBidDto [askMax=" + askMax + ", askMin=" + askMin + ", bidMax=" + bidMax + ", bidMin="
                + bidMin + ", id=" + id + ", updatedOn=" + updatedOn + "]";
    }

}
