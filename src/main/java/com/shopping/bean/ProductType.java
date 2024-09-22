package com.shopping.bean;


import java.util.Set;

import org.springframework.stereotype.Component;

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

@Component
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
		}

		public ProductType(Integer productTypeId, String productTypeName) {
			this.productTypeId = productTypeId;
			this.productTypeName = productTypeName;
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

		public Set<ProductBean> getProducts() {
			return products;
		}

		public void setProducts(Set<ProductBean> products) {
			this.products = products;
		}

		@Override
		public String toString() {
			return "ProductType [productTypeId=" + productTypeId + ", productTypeName=" + productTypeName
					+ ", products=" + products + "]";
		}
		
		
}
