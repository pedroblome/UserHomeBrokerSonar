
package com.pedroblome.user.model;

import java.io.Serializable;

public class UserStockBalancePK implements Serializable {
    private Long idUser;
    private Long idStock;

    public Long getidUser() {
        return idUser;
    }

    public void setidUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getidStock() {
        return idStock;
    }

    public void setidStock(Long idStock) {
        this.idStock = idStock;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idStock == null) ? 0 : idStock.hashCode());
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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
        UserStockBalancePK other = (UserStockBalancePK) obj;
        if (idStock == null) {
            if (other.idStock != null)
                return false;
        } else if (!idStock.equals(other.idStock))
            return false;
        if (idUser == null) {
            if (other.idUser != null)
                return false;
        } else if (!idUser.equals(other.idUser))
            return false;
        return true;
    }

}
