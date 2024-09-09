package com.reserve.bean;

import java.time.LocalTime;

public class ReserveCheckBean {
	
	private LocalTime startTime;
	private LocalTime endTime;
	private Integer totalTableNumber;
	private Integer reservedTableNumber;
	
	
	
	
	
	public ReserveCheckBean() {
	}
	
	
	
	
	public ReserveCheckBean(LocalTime startTime, LocalTime endTime, Integer totalTableNumber,
			Integer reservedTableNumber) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalTableNumber = totalTableNumber;
		this.reservedTableNumber = reservedTableNumber;
	}




	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public Integer getTotalTableNumber() {
		return totalTableNumber;
	}
	public void setTotalTableNumber(Integer totalTableNumber) {
		this.totalTableNumber = totalTableNumber;
	}
	public Integer getReservedTableNumber() {
		return reservedTableNumber;
	}
	public void setReservedTableNumber(Integer reservedTableNumber) {
		this.reservedTableNumber = reservedTableNumber;
	}
	
	
	@Override
	public String toString() {
		return "ReserveCheckBean [startTime=" + startTime + ", endTime=" + endTime + ", totalTableNumber="
				+ totalTableNumber + ", reservedTableNumber=" + reservedTableNumber + "]";
	}
	
	
	
	

}
