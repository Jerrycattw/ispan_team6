package com.shopping.bean;

import java.util.List;

import com.members.bean.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name="product")
public class ProductBean {

	@Id @Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(name = "product_name")
	private String productName;
	
//	@Column(name = "product_type_id")
//	private Integer productTypeId;
	
	@Column(name = "product_price")
	private Integer productPrice;
	
	@Column(name = "product_picture")
	private String productPicture;
	
	@Column(name = "product_stock")
	private Integer productStock;
	
	@Column(name = "product_status")
	private Integer productStatus;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@ManyToOne(fetch= FetchType.LAZY)
//	@JoinColumn(name="product_type_id", insertable = false, updatable = false)
	@JoinColumn(name="product_type_id")
	private ProductType productType;
	
    @OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "product")
    private List<ItemBean> items;
	
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<ItemBean> getItems() {
		return items;
	}

	public void setItems(List<ItemBean> items) {
		this.items = items;
	}

	public String getProductName() {
		return productName;
	}

	 public void setProductName(String productName) {
        this.productName = productName;
    }

	 
	 

//	public Integer getProductTypeId() {
//		return productTypeId;
//	}
//
//	public void setProductTypeId(Integer productTypeId) {
//		this.productTypeId = productTypeId;
//	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
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
	
//	public Member getMember() {
//	    return member;
//	}
//
//	public void setMember(Member member) {
//	    this.member = member;
//	}
	
//	public Set<ProductType> getProductTypes() {
//	    return productTypes;
//	}
//
//	public void setProductTypes(Set<ProductType> productTypes) {
//	    this.productTypes = productTypes;
//	}
	
//	public ProductBean(Integer productId, String productName, Integer productTypeId, Integer productPrice,
//			String productPicture, Integer productStock, Integer productStatus, String productDescription) {
//		super();
//		this.productId = productId;
//		this.productName = productName;
//		this.productTypeId = productTypeId;
//		this.productPrice = productPrice;
//		this.productPicture = productPicture;
//		this.productStock = productStock;
//		this.productStatus = productStatus;
//		this.productDescription = productDescription;
//	}
	
	
	public ProductBean(Integer productId, String productName, Integer productPrice,
			String productPicture, Integer productStock, Integer productStatus, String productDescription) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productStock = productStock;
		this.productStatus = productStatus;
		this.productDescription = productDescription;
	}

//	public ProductBean(String productName, Integer productTypeId, Integer productPrice, String productPicture,
//			Integer productStock, Integer productStatus, String productDescription) {
//		super();
//		this.productName = productName;
//		this.productTypeId = productTypeId;
//		this.productPrice = productPrice;
//		this.productPicture = productPicture;
//		this.productStock = productStock;
//		this.productStatus = productStatus;
//		this.productDescription = productDescription;
//	}
	
	public ProductBean(String productName,  Integer productPrice, String productPicture,
			Integer productStock, Integer productStatus, String productDescription) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productStock = productStock;
		this.productStatus = productStatus;
		this.productDescription = productDescription;
	}

	

	public ProductBean(Integer productId) {
		this.productId = productId;
	}

//	@Override
//	public String toString() {
//		return "ProductBean [productId=" + productId + ", productName=" + productName + ", productTypeId="
//				+ productTypeId + ", productPrice=" + productPrice + ", productPicture=" + productPicture
//				+ ", productStock=" + productStock + ", productStatus=" + productStatus + ", productDescription="
//				+ productDescription + "]";
//	}
	
    @Override
    public String toString() {
        return "ProductBean [productId=" + productId + ", productName=" + productName + ", productPrice="
                + productPrice + ", productPicture=" + productPicture + ", productStock=" + productStock
                + ", productStatus=" + productStatus + ", productDescription=" + productDescription + "]";
    }

	public ProductBean() {
	}



}
