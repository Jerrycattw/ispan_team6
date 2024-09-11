package com.rent.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity@Table(name = "tableware")
public class Tableware implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id@Column(name = "tableware_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tablewareId; 
	@Column(name = "tableware_name")
	private String tablewareName; 
	@Column(name = "tableware_deposit")
	private int tablewareDeposit;
	@Column(name = "tableware_image")
	private String tablewareImage; 
	@Column(name = "tableware_description")
	private String tablewareDescription;
	@Column(name = "tableware_status")
	private int tablewareStatus;
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "tableware")
	private List<TablewareStock> tablewareStocks = new ArrayList<TablewareStock>();
//	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "tableware")
//	private List<RentItem> rentItems = new ArrayList<RentItem>();
	
	public Tableware() {
		super();
	}
	public Tableware(String tablewareName, int tablewareDeposit, String tablewareImage, String tablewareDescription,
			int tablewareStatus) {
		super();
		this.tablewareName = tablewareName;
		this.tablewareDeposit = tablewareDeposit;
		this.tablewareImage = tablewareImage;
		this.tablewareDescription = tablewareDescription;
		this.tablewareStatus = tablewareStatus;
	}

	public int getTablewareId() {
		return tablewareId;
	}
	public void setTablewareId(int tablewareId) {
		this.tablewareId = tablewareId;
	}
	public String getTablewareName() {
		return tablewareName;
	}
	public void setTablewareName(String tablewareName) {
		this.tablewareName = tablewareName;
	}
	public int getTablewareDeposit() {
		return tablewareDeposit;
	}
	public void setTablewareDeposit(int tablewareDeposit) {
		this.tablewareDeposit = tablewareDeposit;
	}
	public String getTablewareImage() {
		return tablewareImage;
	}
	public void setTablewareImage(String tablewareImage) {
		this.tablewareImage = tablewareImage;
	}
	public String getTablewareDescription() {
		return tablewareDescription;
	}
	public void setTablewareDescription(String tablewareDiscription) {
		this.tablewareDescription = tablewareDiscription;
	}
	public int getTablewareStatus() {
		return tablewareStatus;
	}
	public void setTablewareStatus(int tablewareStatus) {
		this.tablewareStatus = tablewareStatus;
	} 
}
