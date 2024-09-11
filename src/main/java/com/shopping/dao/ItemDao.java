package com.shopping.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.shopping.bean.ItemBean;
import com.shopping.bean.ItemId;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.bean.ItemBean;

public class ItemDao {

	private Session session;

	public ItemDao(Session session) {
		this.session = session;
	}

	// 新增訂單
//	public ItemBean addOrder(ItemBean bean) {
//		if (bean != null) {
//			session.persist(bean);
//			return bean;
//		}
//		return null;
//	}

	// 新增訂單明細
	public ItemBean addItem(ItemBean itembean) {
		if (itembean != null) {
			session.persist(itembean);
			return itembean;
		}
		return null;
	}

	// 刪除單筆
	public boolean deleteItem(Integer shoppingId, Integer productId) {
		ItemId itemId = new ItemId(shoppingId, productId);
		ItemBean resultBean = session.get(ItemBean.class, itemId);
		if (resultBean != null) {
			session.remove(resultBean);
			return true;
		}
		return false;
	}
	
	
	// 刪除全部
	public boolean deleteAllItem(Integer shoppingId) {
	    Query<ItemBean> query = session.createQuery("from ItemBean where shoppingId = :shoppingId", ItemBean.class);
	    query.setParameter("shoppingId", shoppingId);
	    List<ItemBean> list = query.list();
	    
	    if(list != null && !list.isEmpty()) {
	        for(ItemBean itemBean : list) {
	            session.remove(itemBean);
	        }
	        return true;
	    }
	    return false;
	}

	

	// 更新
	public ShoppingBean updateProduct(ShoppingBean shoppingBean) {
		ShoppingBean isExist = session.get(ShoppingBean.class, shoppingBean.getShoppingId());
		if (isExist != null) {
			session.merge(shoppingBean);
			return shoppingBean;
		}
		return null;
	}

	// 更新產品項目
	public ItemBean updateItem(Integer shoppingItemQuantity, Integer productId, Integer shoppingId) {
		ItemBean item = session
				.createQuery("FROM ItemBean WHERE shoppingId = :shoppingId AND productId = :productId", ItemBean.class)
				.setParameter("shoppingId", shoppingId).setParameter("productId", productId).uniqueResult();
		ProductDao productDao = new ProductDao(session);
		Integer productPrice = productDao.searchProductPriceById(productId);
		Integer itemPrice = shoppingItemQuantity * productPrice;
		item.setShoppingItemQuantity(shoppingItemQuantity);
		item.setShoppingItemPrice(itemPrice);
		return item;
	}

	// 計算訂單總金額
	public Integer calculateTotalAmount(Integer shoppingId) {
		String hql = "SELECT SUM(i.shoppingItemPrice * i.shoppingItemQuantity) FROM ItemBean i WHERE i.shoppingId = :shoppingId";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("shoppingId", shoppingId);
		Long totalAmountLong = query.uniqueResult();
		return (totalAmountLong != null) ? totalAmountLong.intValue() : 0;
	}

	// 查詢訂單明細
	public List<ItemBean> searchItemsByShoppingId(Integer shoppingId) {
		String hql = "FROM ItemBean i WHERE i.shoppingId = :shoppingId";
		Query<ItemBean> query = session.createQuery(hql, ItemBean.class);
		query.setParameter("shoppingId", shoppingId);
		return query.getResultList();
	}
	
	// 查詢單筆訂單明細
	public ItemBean searchItem(Integer shoppingId, Integer productId) {
		
		ItemId itemId = new ItemId(shoppingId, productId);
		ItemBean resultBean = session.get(ItemBean.class, itemId);
		if (resultBean != null) {
			return resultBean;
		}
		return null;
		
	}
	
	

}
