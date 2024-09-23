package com.shopping.bean;

import org.springframework.stereotype.Component;

@Component
public class ProductDTO {

private Integer productId;
	
	private String productName;
	private Integer productPrice;
	private String productPicture;
	private Integer productStock;
	private Integer productStatus;
	private String productDescription;
	private Integer productTypeId;
	private String productTypeName;

	public ProductDTO() {
	}

	
	
	
	public ProductDTO(Integer productId, String productName, Integer productPrice, String productPicture,
			Integer productStock, Integer productStatus, String productDescription, Integer productTypeId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productStock = productStock;
		this.productStatus = productStatus;
		this.productDescription = productDescription;
		this.productTypeId = productTypeId;
	}

	


	public ProductDTO(String productName, Integer productPrice, String productPicture, Integer productStock,
			Integer productStatus, String productDescription) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productStock = productStock;
		this.productStatus = productStatus;
		this.productDescription = productDescription;
	}




	public ProductDTO(Integer productId, String productName, Integer productPrice, String productPicture,
			Integer productStock, Integer productStatus, String productDescription, Integer productTypeId,
			String productTypeName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productStock = productStock;
		this.productStatus = productStatus;
		this.productDescription = productDescription;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
	}
	
	
	public ProductDTO(ProductBean productBean) {
		
		
		this.productId = productBean.getProductId();
		this.productName = productBean.getProductName();
		this.productPrice = productBean.getProductPrice();
		this.productPicture = productBean.getProductPicture();
		this.productStock = productBean.getProductStock();
		this.productStatus = productBean.getProductStatus();
		this.productDescription = productBean.getProductDescription();
		this.productTypeId = productBean.getProductType().getProductTypeId();
		this.productTypeName = productBean.getProductType().getProductTypeName();
		
		
	}



	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}




	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productPicture=" + productPicture + ", productStock=" + productStock + ", productStatus="
				+ productStatus + ", productDescription=" + productDescription + ", productTypeId=" + productTypeId
				+ ", productTypeName=" + productTypeName + "]";
	}
	
	
	
}
