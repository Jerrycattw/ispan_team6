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

@Entity@Table(name = "tableware_stock")
@IdClass(TablewareStock.TablewareStockId.class)
public class TablewareStock implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id@Column(name = "tableware_id")
	private int tablewareId;
	@Id@Column(name = "restaurant_id")
	private int restaurantId;
	@Column(name = "stock")
	private int stock;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableware_id", insertable = false, updatable = false)
	private Tableware tableware;
	
	
	public TablewareStock() {
		super();
	}
	public TablewareStock(int tablewareId, int restaurantId, int stock) {
		super();
		this.tablewareId = tablewareId;
		this.restaurantId = restaurantId;
		this.stock = stock;
	}
	public int getTablewareId() {
		return tablewareId;
	}
	public void setTablewareId(int tablewareId) {
		this.tablewareId = tablewareId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public static class TablewareStockId implements Serializable {
        private int tablewareId;
        private int restaurantId;

        // Default constructor
        public TablewareStockId() {}

        public TablewareStockId(int tablewareId, int restaurantId) {
            this.tablewareId = tablewareId;
            this.restaurantId = restaurantId;
        }

        // Getters and Setters
        public int getTablewareId() {
            return tablewareId;
        }

        public void setTablewareId(int tablewareId) {
            this.tablewareId = tablewareId;
        }

        public int getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(int restaurantId) {
            this.restaurantId = restaurantId;
        }

        // Override equals() and hashCode()
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TablewareStockId that = (TablewareStockId) o;
            return tablewareId == that.tablewareId && restaurantId == that.restaurantId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(tablewareId, restaurantId);
        }
    }
	
}
