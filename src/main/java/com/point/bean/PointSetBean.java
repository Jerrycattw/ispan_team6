package com.point.bean;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "point_set")
public class PointSetBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "point_set")
	private String pointSetName;
	
	@Column(name = "amount_per_point")
	private int amountPerPoint;
	
	@Column(name = "points_earned")
	private int pointsEarned;
	
	@Column(name = "point_ratio")
	private int pointRatio;
	
	@Column(name = "expiry_month")
	private String expiryMonth;
	
	@Column(name = "expiry_day")
	private String expiryDay;
	
	@Column(name = "birth_type")
	private String birthType;
	
	@Column(name = "set_description")
	private String setDescription;
	
	@Column(name = "is_expiry")
	private String isExpiry;
	
	public PointSetBean() {
	}

	public String getPointSetName() {
		return pointSetName;
	}

	public void setPointSetName(String pointsetName) {
		pointSetName = pointsetName;
	}

	public int getAmountPerPoint() {
		return amountPerPoint;
	}

	public void setAmountPerPoint(int amountPerPoint) {
		this.amountPerPoint = amountPerPoint;
	}

	public int getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public int getPointRatio() {
		return pointRatio;
	}

	public void setPointRatio(int pointRatio) {
		this.pointRatio = pointRatio;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryDay() {
		return expiryDay;
	}

	public void setExpiryDay(String expiryDay) {
		this.expiryDay = expiryDay;
	}

	public String getBirthType() {
		return birthType;
	}

	public void setBirthType(String birthType) {
		this.birthType = birthType;
	}

	public String getSetDescription() {
		return setDescription;
	}

	public void setSetDescription(String setDescription) {
		this.setDescription = setDescription;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	@Override
	public String toString() {
		return "PointSetBean [pointSetName=" + pointSetName + ", amountPerPoint=" + amountPerPoint + ", pointsEarned="
				+ pointsEarned + ", pointRatio=" + pointRatio + ", expiryMonth=" + expiryMonth + ", expiryDay="
				+ expiryDay + ", birthType=" + birthType + ", setDescription=" + setDescription + ", isExpiry="
				+ isExpiry + "]";
	}
	
}
