package com.shopping.bean;

import java.io.Serializable;
import java.util.Objects;

public class ItemId implements Serializable {
	
    private Integer shoppingId;
    private Integer productId;

    public ItemId() {
    }
 
    public ItemId(Integer shoppingId, Integer productId) {
        this.shoppingId = shoppingId;
        this.productId = productId;
    }

    public Integer getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Integer shoppingId) {
        this.shoppingId = shoppingId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemId itemId = (ItemId) o;
        return Objects.equals(shoppingId, itemId.shoppingId) &&
               Objects.equals(productId, itemId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingId, productId);
    }
}
