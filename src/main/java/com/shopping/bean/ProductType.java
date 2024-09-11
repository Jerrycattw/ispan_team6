package com.shopping.bean;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity @Table(name="product_type")
public class ProductType {
	
	
		@Id @Column(name = "product_type_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer productTypeId;
		
		@Column(name = "product_type_name")
		private String productTypeName;

		
		@OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="productType")
		private Set<ProductBean> products;
		
		
		public ProductType() {
			super();
		}

		public ProductType(Integer productId, String productName) {
			this.productTypeId = productId;
			this.productTypeName = productName;
		}

		public Integer getProductId() {
			return productTypeId;
		}

		public void setProductId(Integer productId) {
			this.productTypeId = productId;
		}

		public String getProductName() {
			return productTypeName;
		}

		public void setProductName(String productName) {
			this.productTypeName = productName;
		}
		
		

		public Set<ProductBean> getProducts() {
			return products;
		}

		public void setProducts(Set<ProductBean> products) {
			this.products = products;
		}

		@Override
		public String toString() {
			return "ProductBean [productId=" + productTypeId + ", productName=" + productTypeName + "]";
		}
		
	
}
