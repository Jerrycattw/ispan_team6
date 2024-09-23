package com.point.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity @Table(name = "point")
public class PointBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "point_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pointID;
	
	@Column(name = "member_id")
	private int memberID;
	
	@Column(name = "point_change")
	private int pointChange;
	
	@Column(name = "created_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	
	@Column(name = "get_expiry_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;
	
	@Column(name = "point_usage")
	private Integer pointUsage;
	
	@Column(name = "transaction_id")
	private int transactionID;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "transaction_description")
	private String transactionDescription;
	
	public PointBean() {
	}
	
	

	public PointBean(int memberID, int pointChange, Date createDate, Date expiryDate, int transactionID,
			String transactionType) {
		this.memberID = memberID;
		this.pointChange = pointChange;
		this.createDate = createDate;
		this.expiryDate = expiryDate;
		this.transactionID = transactionID;
		this.transactionType = transactionType;
	}

	

	public int getPointID() {
		return pointID;
	}


	public void setPointID(int pointID) {
		this.pointID = pointID;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public int getPointChange() {
		return pointChange;
	}

	public void setPointChange(int pointChange) {
		this.pointChange = pointChange;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getPointUsage() {
		return pointUsage;
	}

	public void setPointUsage(Integer pointUsage) {
		this.pointUsage = pointUsage;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}



	@Override
	public String toString() {
		return "PointBean [pointID=" + pointID + ", memberID=" + memberID + ", pointChange=" + pointChange
				+ ", createDate=" + createDate + ", expiryDate=" + expiryDate + ", pointUsage=" + pointUsage
				+ ", transactionID=" + transactionID + ", transactionType=" + transactionType
				+ ", transactionDescription=" + transactionDescription + "]";
	}
	
	
	
	
	
}
