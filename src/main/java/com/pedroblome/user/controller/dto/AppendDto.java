package com.pedroblome.user.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AppendDto {
    @NotNull
    public Long id;
    @NotNull
    @Min(value=1)
    public BigDecimal amount;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    
}
