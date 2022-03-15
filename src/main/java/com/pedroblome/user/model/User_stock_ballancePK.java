
package com.pedroblome.user.model;

import java.io.Serializable;

public class User_stock_ballancePK implements Serializable {
    private Long id_user;
    private Long id_stock;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_stock == null) ? 0 : id_stock.hashCode());
        result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User_stock_ballancePK other = (User_stock_ballancePK) obj;
        if (id_stock == null) {
            if (other.id_stock != null)
                return false;
        } else if (!id_stock.equals(other.id_stock))
            return false;
        if (id_user == null) {
            if (other.id_user != null)
                return false;
        } else if (!id_user.equals(other.id_user))
            return false;
        return true;
    }

}
