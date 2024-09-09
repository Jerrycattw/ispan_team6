package com.coupon.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "product_type")
public class ProductTypeBean {
	@Id @Column(name = "product_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer product_type_id;
	
	@Column(name = "product_type_name")
	private String product_type_name;

	public ProductTypeBean() {
	}

	public Integer getProduct_type_id() {
		return product_type_id;
	}

	public void setProduct_type_id(Integer product_type_id) {
		this.product_type_id = product_type_id;
	}

	public String getProduct_type_name() {
		return product_type_name;
	}

	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}
	
	
}
