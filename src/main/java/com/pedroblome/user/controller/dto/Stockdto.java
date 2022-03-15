package com.pedroblome.user.controller.dto;

public class Stockdto {
    private Long id;
    private String name;
    private String symbol;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Stockdto(Long id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }
    
    
}
