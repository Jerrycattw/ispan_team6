package com.rent.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.TogoOrder.bean.TogoBean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity@Table(name = "rent")
public class Rent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id@Column(name = "rent_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rentId;
	@Column(name = "rent_deposit")
	private int rentDeposit;
	@Column(name = "rent_date")
	@Temporal(TemporalType.DATE)
	private Date rentDate;
	@Column(name = "restaurant_id")
	private int restaurantId;
	@Column(name = "member_id")
	private int memberId;
	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;
	@Column(name = "return_date")
	@Temporal(TemporalType.DATE)
	private Date returnDate;
	@Column(name = "rent_status")
	private int rentStatus;
	@Column(name = "rent_memo")
	private String rentMemo;
	@Column(name = "return_restaurant_id")
	private Integer returnRestaurantId;
	@OneToOne(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private TogoBean togo;
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rent")
//	private List<RentItem> rentItems = new ArrayList<RentItem>();
	
	public Rent() {
		super();
	}
	public Rent(int rentDeposit, Date rentDate, int restaurantId, int memberId, Date dueDate, Date returnDate,
			int rentStatus, String rentMemo, int returnRestaurantId) {
		super();
		this.rentDeposit = rentDeposit;
		this.rentDate = rentDate;
		this.restaurantId = restaurantId;
		this.memberId = memberId;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.rentStatus = rentStatus;
		this.rentMemo = rentMemo;
		this.returnRestaurantId = returnRestaurantId;
	}
	public int getRentId() {
		return rentId;
	}
	public void setRentId(int rentId) {
		this.rentId = rentId;
	}
	public int getRentDeposit() {
		return rentDeposit;
	}
	public void setRentDeposit(int rentDeposit) {
		this.rentDeposit = rentDeposit;
	}
	public Date getRentDate() {
		return rentDate;
	}
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(int rentStatus) {
		this.rentStatus = rentStatus;
	}
	public String getRentMemo() {
		return rentMemo;
	}
	public void setRentMemo(String rentMemo) {
		this.rentMemo = rentMemo;
	}
	public Integer getReturnRestaurantId() {
		return returnRestaurantId;
	}
	public void setReturnRestaurantId(int returnRestaurantId) {
		this.returnRestaurantId = returnRestaurantId;
	}
	@Override
	public String toString() {
		return "Rent [rentDate=" + rentDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate + "]";
	}
//	public List<RentItem> getRentItems() {
//		return rentItems;
//	}
//	public void setRentItems(List<RentItem> rentItems) {
//		this.rentItems = rentItems;
//	}
	public TogoBean getTogo() {
		return togo;
	}
	public void setTogo(TogoBean togo) {
		this.togo = togo;
	}
	public void setReturnRestaurantId(Integer returnRestaurantId) {
		this.returnRestaurantId = returnRestaurantId;
	}
	
	
}