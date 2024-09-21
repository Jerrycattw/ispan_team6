package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.members.bean.Member;
import com.shopping.bean.ItemBean;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.dao.ItemDao;
import com.shopping.dao.ProductDao;
import com.shopping.dao.ShoppingDao;

@Service
@Transactional
public class ShoppingService {
	
	@Autowired
	private ShoppingDao sDao;
	private ItemDao iDao;
	private ProductDao pDao;
	
	
//	public ShoppingService(Session session) {
//		sDao = new ShoppingDao(session);
//		iDao = new ItemDao(session);
//		pDao = new ProductDao(session);
//	}
	
	
	public ShoppingBean addOrder(ShoppingBean bean) {
		return sDao.addOrder(bean);
	}
	
	 public void addTotal(Integer shoppingItemQuantity, Integer productId, Integer shoppingId) {
	        sDao.addTotal(shoppingItemQuantity, productId, shoppingId);
	    }
	
	public boolean deleteShopping(Integer ShoppingId) {
		return sDao.deleteShopping(ShoppingId);
	}
	
	public boolean deleteOrder(Integer ShoppingId) {
		return iDao.deleteItem(ShoppingId, ShoppingId);
	}
	
	public ShoppingBean updateShopping(ShoppingBean shoppingbean) {
		return sDao.updateShopping(shoppingbean);
	}
	
	public ShoppingBean updateDataShopping(ShoppingBean shoppingbean) {
		return sDao.updateDataShopping(shoppingbean);
	}
	
	public ShoppingBean searchByShoppingId(Integer ShoppingId) {
		return sDao.searchByShoppingId(ShoppingId);
	}
	
	public Member searchMemberById(Integer memberId) {
        return sDao.searchMemberById(memberId);
    }
	
	
	public List<Member> searchAllMembers() {
        return sDao.searchAllMembers();  
    }

    public List<ProductBean> searchAllProduct() {
        return pDao.searchAllProduct(); 
    }
	
	public List<ShoppingBean> searchAllShopping() {
		return sDao.searchAllShopping();
	}
	
	public List<ItemBean> searchItemsByShoppingId(Integer shoppingId) { 
        return iDao.searchItemsByShoppingId(shoppingId);
    }

    public Integer calculateTotalAmount(Integer shoppingId) { 
        return iDao.calculateTotalAmount(shoppingId);
    }
}
