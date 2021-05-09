package com.bac.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LogSql implements Serializable {
    private static final long serialVersionUID = -6232792870303944018L;

    public enum Type {
        /**
         *
         */
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

    private Integer id;
    private Type type;
    private Integer productId;
    private LocalDateTime updatedDate;
    private String updatedUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
}
