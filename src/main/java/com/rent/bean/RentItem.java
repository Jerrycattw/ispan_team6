package com.rent.bean;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity@Table(name = "rent_item")
@IdClass(RentItem.RentItemId.class)
public class RentItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id@Column(name = "rent_id")
	private int rentId;
	@Id@Column(name = "tableware_id")
	private int tablewareId;
	@Column(name = "rent_item_quantity")
	private int rentItemQuantity;
	@Column(name = "rent_item_deposit")
	private int rentItemDeposit;
	@Column(name = "return_memo")
	private String returnMemo;
	@Column(name = "return_status")
	private int returnStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id", insertable = false, updatable = false)
    private Rent rent;
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "tableware_id")
//	private Tableware tableware;
	
	public RentItem() {
		super();
	}
	
	public RentItem(int rentId, int tablewareId, int rentItemQuantity, int rentItemDeposit, String returnMemo,
			int returnStatus) {
		super();
		this.rentId = rentId;
		this.tablewareId = tablewareId;
		this.rentItemQuantity = rentItemQuantity;
		this.rentItemDeposit = rentItemDeposit;
		this.returnMemo = returnMemo;
		this.returnStatus = returnStatus;
	}


	public int getRentId() {
		return rentId;
	}
	public void setRentId(int rentId) {
		this.rentId = rentId;
	}
	public int getTablewareId() {
		return tablewareId;
	}
	public void setTablewareId(int tablewareId) {
		this.tablewareId = tablewareId;
	}
	public int getRentItemQuantity() {
		return rentItemQuantity;
	}
	public void setRentItemQuantity(int rentItemQuantity) {
		this.rentItemQuantity = rentItemQuantity;
	}
	public int getRentItemDeposit() {
		return rentItemDeposit;
	}
	public void setRentItemDeposit(int rentItemDeposit) {
		this.rentItemDeposit = rentItemDeposit;
	}
	public String getReturnMemo() {
		return returnMemo;
	}
	public void setReturnMemo(String returnMemo) {
		this.returnMemo = returnMemo;
	}
	public int getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

//
//	public Tableware getTableware() {
//		return tableware;
//	}
//
//	public void setTableware(Tableware tableware) {
//		this.tableware = tableware;
//	}
	
	public static class RentItemId implements Serializable {
        private static final long serialVersionUID = 1L;

        private int rentId;
        private int tablewareId;

        // 构造函数
        public RentItemId() {
        }

        public RentItemId(int rentId, int tablewareId) {
            this.rentId = rentId;
            this.tablewareId = tablewareId;
        }

        // getter 和 setter 方法
        public int getRentId() {
            return rentId;
        }

        public void setRentId(int rentId) {
            this.rentId = rentId;
        }

        public int getTablewareId() {
            return tablewareId;
        }

        public void setTablewareId(int tablewareId) {
            this.tablewareId = tablewareId;
        }

        // 重写 equals 和 hashCode 方法
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RentItemId that = (RentItemId) o;
            return rentId == that.rentId && tablewareId == that.tablewareId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rentId, tablewareId);
        }
    }
}
