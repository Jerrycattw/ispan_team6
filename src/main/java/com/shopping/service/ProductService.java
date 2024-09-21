package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.bean.ProductBean;
import com.shopping.dao.ItemDao;
import com.shopping.dao.ProductDao;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private ProductDao pDao;
	private ItemDao iDao;
	
//	public ProductService(Session session) {
//		pDao = new ProductDao(session);
//		iDao = new ItemDao(session); 
//		
//	}
	
	public ProductBean addProduct(ProductBean bean) {
		return pDao.addProduct(bean);
	}
	
	public boolean deleteProduct(Integer productId) {
		return pDao.deleteProduct(productId);
	}
	
	public ProductBean updateProduct(ProductBean bean) {
		return pDao.updateProduct(bean);
	}
	
	public ProductBean searchProductName(String productName) {
        return pDao.searchProductName(productName);
    }
	
	public ProductBean searchByProductId(Integer productId) {
		return pDao.searchByProductId(productId);
	}
	
	public List<ProductBean> searchAllProduct() {
		return pDao.searchAllProduct();
	}
	
	public Integer calculateTotalAmount(Integer shoppingId) {
	    return iDao.calculateTotalAmount(shoppingId);
	}
	
}
