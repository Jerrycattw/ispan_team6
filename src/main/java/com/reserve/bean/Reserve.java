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

@Entity @Table(name = "reserve")
public class Reserve {
	@Expose
	@Id @Column(name = "reserve_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String reserveId;
	@Expose
	@Column(name = "member_id")
	private String memberId;
	@Expose
	@Column(name = "restaurant_id")
	private String restaurantId;
	@Expose
	@Column(name = "reserve_create_time")
	private LocalDateTime reserveCreateTime;
	@Expose
	@Column(name = "reserve_seat")
	private Integer reserveSeat;
	@Expose
	@Column(name = "table_type_id")
	private String tableTypeId;
	@Expose
	@Column(name = "reserve_time")
	private LocalDateTime reserveTime;
	@Expose
	@Column(name = "finished_time")
	private LocalDateTime finishedTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant; // 與 Restaurant 的關聯
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member; // 與 Restaurant 的關聯	
	

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
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
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
	
	
    public Reserve() {
	}
	
	
	public Reserve(String memberId, String restaurantId, Integer reserveSeat, String tableTypeId,
			LocalDateTime reserveTime, LocalDateTime finishedTime) {
		this.memberId = memberId;
		this.restaurantId = restaurantId;
		this.reserveSeat = reserveSeat;
		this.tableTypeId = tableTypeId;
		this.reserveTime = reserveTime;
		this.finishedTime = finishedTime;
	}
	
	@Override
	public String toString() {
		return "ReserveBean [reserveId=" + reserveId + ", memberId=" + memberId + ", restaurantId=" + restaurantId
				+ ", reserveCreateTime=" + reserveCreateTime + ", reserveSeat=" + reserveSeat + ", tableTypeId="
				+ tableTypeId + ", reserveTime=" + reserveTime + ", finishedTime=" + finishedTime + "]";
	}
	
	

}
