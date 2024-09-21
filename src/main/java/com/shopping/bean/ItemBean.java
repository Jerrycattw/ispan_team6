package com.shopping.bean;


import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity @Table(name="shopping_item")
@IdClass(ItemId.class)
public class ItemBean {
	
	@Id
	@Column(name = "shopping_id")
	private Integer shoppingId;
	
	@Id
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "shopping_item_quantity")
	private Integer shoppingItemQuantity;
	
	@Column(name = "shopping_item_price")
	private Integer shoppingItemPrice;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductBean product;
	
	@ManyToOne
	@JoinColumn(name = "shopping_id", insertable = false, updatable = false) 
    private ShoppingBean shoppingBean;
	
	

	public ItemBean(Integer shoppingItemQuantity,Integer shoppingItemPrice) {
		this.shoppingItemQuantity = shoppingItemQuantity;
		this.shoppingItemPrice = shoppingItemPrice;
	}

	

	public ItemBean(Integer shoppingId, Integer productId, Integer shoppingItemQuantity) {
			this.shoppingId = shoppingId;
			this.productId = productId;
			this.shoppingItemQuantity = shoppingItemQuantity;
		}

    public ItemBean(Integer shoppingId, Integer productId, Integer shoppingItemQuantity, Integer shoppingItemPrice) {
        this.shoppingId = shoppingId;
        this.productId = productId;
        this.shoppingItemQuantity = shoppingItemQuantity;
        this.shoppingItemPrice = shoppingItemPrice;
    }

    public ItemBean(Integer shoppingId, Integer productId, Integer shoppingItemQuantity, Integer shoppingItemPrice,
    		ProductBean product, ShoppingBean shoppingBean) {
    	super();
    	this.shoppingId = shoppingId;
    	this.productId = productId;
    	this.shoppingItemQuantity = shoppingItemQuantity;
    	this.shoppingItemPrice = shoppingItemPrice;
    	this.product = product;
    	this.shoppingBean = shoppingBean;
    }


	public ItemBean() {
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


	public Integer getShoppingItemQuantity() {
		return shoppingItemQuantity;
	}

	public void setShoppingItemQuantity(Integer shoppingItemQuantity) {
		this.shoppingItemQuantity = shoppingItemQuantity;
	}


	public Integer getShoppingItemPrice() {
		return shoppingItemPrice;
	}

	public void setShoppingItemPrice(Integer shoppingItemPrice) {
		this.shoppingItemPrice = shoppingItemPrice;
	}
	
	public ProductBean getProduct() {
		return product;
	}
	
	public void setProduct(ProductBean product) {
		this.product = product;
	}
	
	
	public ShoppingBean getShoppingBean() {
		return shoppingBean;
	}
	
	
	public void setShoppingBean(ShoppingBean shoppingBean) {
		this.shoppingBean = shoppingBean;
	}
	


//	@Override
//	public String toString() {
//		return "ItemBean [shopping_Id=" + shopping_Id + ", product_id=" + product_id + ", product_name=" + product_name
//				+ ", shopping_Item_Quantity=" + shopping_Item_Quantity + ", product_price=" + product_price
//				+ ", shopping_Item_Price=" + shopping_Item_Price + "]";
//	}

//	 @Override
//	    public String toString() {
//	        return "ItemBean{" +
//	                "shopping_Id=" + shoppingId +
//	                ", product_id=" + productId +
//	                ", product_name='" + product_name + '\'' +
//	                ", shopping_Item_Quantity=" + shoppingItemQuantity +
//	                ", product_price=" + product_price +
//	                ", shopping_Item_Price=" + shoppingItemPrice +
//	                '}';
//	 }



	@Override
	public String toString() {
		return "ItemBean{" +
				"shopping_Id=" + shoppingId +
				", product_id=" + productId +
				", shopping_Item_Quantity=" + shoppingItemQuantity +
				", shopping_Item_Price=" + shoppingItemPrice +
				'}';
	}
}	