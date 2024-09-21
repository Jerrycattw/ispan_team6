package com.shopping.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.members.bean.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Component
@Entity @Table(name="shopping_order")
public class ShoppingBean  {
	
	@Id @Column(name = "shopping_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoppingId;
    
	@Column(name = "shopping_date")
    private LocalDateTime shoppingDate;
	
    @PrePersist
    protected void onCreate() {
        if (this.shoppingDate == null) {
            this.shoppingDate = LocalDateTime.now();
        }
    }
    
	@Column(name = "shopping_total")
    private Integer shoppingTotal;
    
	@Column(name = "member_id")
    private Integer memberId;
    
	@Column(name = "shopping_status")
    private Integer shoppingStatus;
    
	@Column(name = "shopping_memo")
    private String shoppingMemo;
	
	@ManyToOne 
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member member;
	
	@OneToMany(mappedBy = "shoppingBean")
    private List<ItemBean> items;
    

    public ShoppingBean() {
        super();
    }

    
    
//    public ShoppingBean(Integer shoppingId, Integer shoppingTotal,LocalDateTime shoppingDate,  Integer memberId,
//			String memberName,Integer shoppingStatus, String shoppingMemo, Integer shoppingItemQuantity, Integer shoppingItemPrice) {
//		super();
//		this.shoppingId = shoppingId;
//		this.shoppingTotal = shoppingTotal;
//		this.shoppingDate = shoppingDate;
//		this.memberId = memberId;
//		this.memberName = memberName;
//		this.shoppingStatus = shoppingStatus;
//		this.shoppingMemo = shoppingMemo;
//		this.shoppingItemQuantity = shoppingItemQuantity;
//		this.shoppingItemPrice = shoppingItemPrice;
//	}

    public ShoppingBean(Integer memberId, Integer shoppingStatus) {
		super();
		this.memberId = memberId;
		this.shoppingStatus = shoppingStatus;
	}



	public ShoppingBean(Integer shoppingId, Integer shoppingTotal,LocalDateTime shoppingDate,  Integer memberId,
			Integer shoppingStatus, String shoppingMemo) {
		this.shoppingId = shoppingId;
		this.shoppingTotal = shoppingTotal;
		this.shoppingDate = shoppingDate;
		this.memberId = memberId;
		this.shoppingStatus = shoppingStatus;
		this.shoppingMemo = shoppingMemo;
	}
    

	public ShoppingBean(Integer shoppingTotal, Integer memberId,
			Integer shoppingStatus, String shoppingMemo, Integer shoppingId) {
		super();
		this.shoppingTotal = shoppingTotal;
		this.memberId = memberId;
		this.shoppingStatus = shoppingStatus;
		this.shoppingMemo = shoppingMemo;
		this.shoppingId = shoppingId;
	}

	public ShoppingBean(int shoppingId) {
		this.shoppingId=shoppingId;
	}



	public String getFormattedShoppingDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return shoppingDate.format(formatter);
    }

	public Integer getShoppingId() {
		return shoppingId;
	}

	public void setShoppingId(Integer shoppingId) {
		this.shoppingId = shoppingId;
	}

	public LocalDateTime getShoppingDate() {
		return shoppingDate;
	}

	public void setShoppingDate(LocalDateTime shoppingDate) {
		this.shoppingDate = shoppingDate;
	}

	public Integer getShoppingTotal() {
		return shoppingTotal;
	}

	public void setShoppingTotal(Integer shoppingTotal) {
		this.shoppingTotal = shoppingTotal;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getShoppingStatus() {
		return shoppingStatus;
	}

	public void setShoppingStatus(Integer shoppingStatus) {
		this.shoppingStatus = shoppingStatus;
	}

	
	public String getShoppingMemo() {
        return (shoppingMemo != null) ? shoppingMemo : "";
    }

    public void setShoppingMemo(String shoppingMemo) {
        this.shoppingMemo = (shoppingMemo != null) ? shoppingMemo : "";
    }

    public List<ItemBean> getItems() {
        return items;
    }

    public void setItems(List<ItemBean> items) {
        this.items = items;
    }
    
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
//	@Override
//    public String toString() {
//    	return "ShoppingBean [shoppgetFormattedShoppingDate()ing_Id=" + shoppingId +  ", shopping_Total="
//    			+ shoppingTotal + ", shopping_Date=" + shoppingDate +", member_Id=" + memberId + ", shopping_Status=" + shoppingStatus
//    			+ ", shopping_Memo=" + shoppingMemo + ", shoppingItem_Quantity=" + shoppingItemQuantity
//    			+ ", shoppingItem_Price=" + shoppingItemPrice + ", member_Name=" + memberName + "]";
//    }
//	
	@Override
    public String toString() {
    	return "ShoppingBean [shoppgetFormattedShoppingDate()ing_Id=" + shoppingId +  ", shopping_Total="
    			+ shoppingTotal + ", shopping_Date=" + shoppingDate +", member_Id=" + memberId + ", shopping_Status=" + shoppingStatus
    			+ ", shopping_Memo=" + shoppingMemo + "]";
    }
    
}