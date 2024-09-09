package com.reserve.bean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.annotations.Expose;
import com.members.bean.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

public class ReserveDTO {
	private String reserveId;
	private String memberId;
	private String restaurantId;
	private LocalDateTime reserveCreateTime;
	private Integer reserveSeat;
	private String tableTypeId;
	private LocalDateTime reserveTime;
	private LocalDateTime finishedTime;
	private String memberName;
	private String phone;
	private String restaurantName;
	private String restaurantAddress;	
	

	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public LocalDateTime getReserveCreateTime() {
		return reserveCreateTime;
	}
	public void setReserveCreateTime(LocalDateTime reserveCreateTime) {
		this.reserveCreateTime = reserveCreateTime;
	}
	public Integer getReserveSeat() {
		return reserveSeat;
	}
	public void setReserveSeat(Integer reserveSeat) {
		this.reserveSeat = reserveSeat;
	}
	public String getTableTypeId() {
		return tableTypeId;
	}
	public void setTableTypeId(String tableTypeId) {
		this.tableTypeId = tableTypeId;
	}
	public LocalDateTime getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(LocalDateTime reserveTime) {
		this.reserveTime = reserveTime;
	}
	public LocalDateTime getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(LocalDateTime finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public String getFormattedReserveCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return reserveCreateTime.format(formatter);
    }

    public String getFormattedReserveTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return reserveTime.format(formatter);
    }

    public String getFormattedFinishedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return finishedTime.format(formatter);
    }
	
	
    public ReserveDTO() {
	}
	
    
    
	public ReserveDTO(Reserve reserve) {
		this.reserveId = reserve.getReserveId();
		this.memberId = reserve.getMemberId();
		this.restaurantId = reserve.getRestaurantId();
		this.reserveCreateTime = reserve.getReserveCreateTime();
		this.reserveSeat = reserve.getReserveSeat();
		this.tableTypeId = reserve.getTableTypeId();
		this.reserveTime = reserve.getReserveTime();
		this.finishedTime = reserve.getFinishedTime();
		this.memberName = reserve.getMember().getMemberName();
		this.phone = reserve.getMember().getPhone();
		this.restaurantName = reserve.getRestaurant().getRestaurantName();
		this.restaurantAddress = reserve.getRestaurant().getRestaurantAddress();
	}
	
	
	
	@Override
	public String toString() {
		return "ReserveBean [reserveId=" + reserveId + ", memberId=" + memberId + ", restaurantId=" + restaurantId
				+ ", reserveCreateTime=" + reserveCreateTime + ", reserveSeat=" + reserveSeat + ", tableTypeId="
				+ tableTypeId + ", reserveTime=" + reserveTime + ", finishedTime=" + finishedTime + "]";
	}
	
	

}
