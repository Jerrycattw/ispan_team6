package com.shopping.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.bean.ProductBean;
import com.shopping.bean.ProductDTO;
import com.shopping.bean.ProductType;

@Repository
@Transactional
public class ProductDao {


	@Autowired
	private SessionFactory sessionFactory;
	

	// 新增
	public ProductBean addProduct(ProductBean bean) {
		Session session = sessionFactory.getCurrentSession();
		if (bean != null) {
			session.persist(bean);
			return bean;
		}
		return null;
	}
	
	// 新增
	public ProductDTO addProduct(ProductDTO DTO) {
		Session session = sessionFactory.getCurrentSession();
		if (DTO != null) {
			session.persist(DTO);
			return DTO;
		}
		return null;
	}


	// 刪除
	public boolean deleteProduct(Integer productId) {
		Session session = sessionFactory.getCurrentSession();
		ProductBean resultBean = session.get(ProductBean.class, productId);
		if (resultBean != null) {
			session.remove(resultBean);
			return true;
		}
		return false;
	}

	// 更新
	public ProductBean updateProduct(ProductBean productBean) {
		Session session = sessionFactory.getCurrentSession();
		ProductBean isExist = session.get(ProductBean.class, productBean.getProductId());
			if(isExist!=null) {
				session.merge(productBean);
				return productBean;
		}
		return null;
	}
	
	
	 // 依ID查詢單筆訂位訂單
//	 public Reserve selectById(String reserveId) {
//	  Session session = sessionFactory.getCurrentSession();
//	     String hql = "SELECT r FROM Reserve r JOIN FETCH r.member JOIN FETCH r.restaurant WHERE r.id = :reserveId";
//	     Query<Reserve> query = session.createQuery(hql, Reserve.class);
//	     query.setParameter("reserveId", reserveId);
//	     return query.uniqueResult();
//	 }
	
	
	// 查詢全部
	public List<ProductBean> searchAllProduct() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<ProductBean> query = session.createQuery("from ProductBean pb JOIN FETCH pb.productType", ProductBean.class);
		return query.list();
	}
	

	// 依ID查詢
	public ProductBean searchByProductId(Integer productId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ProductBean.class, productId);
	}
	
	// 依品名查詢
		public ProductBean searchProductName(String productName) {
			Session session = sessionFactory.getCurrentSession();
			return session.get(ProductBean.class, productName);
		}

	
	// 依總類查詢
	public ProductBean SearchByProductType(Integer productType) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ProductBean.class, productType);
	}

	// 根據產品名稱查詢
    public ProductBean searchByProductName(String productName) {
    	Session session = sessionFactory.getCurrentSession();
        Query<ProductBean> query = session.createQuery("from ProductBean where productName = :productName", ProductBean.class);
        query.setParameter("productName", productName);
        return query.uniqueResult();
    }

    // 根據產品類型查詢
//    public List<ProductBean> searchByProductType(Integer productType) {
//    	Session session = sessionFactory.getCurrentSession();
//        Query<ProductBean> query = session.createQuery("from ProductBean where productType = :productType", ProductBean.class);
//        query.setParameter("productType", productType);
//        return query.list();
//    }
    
 // 根據產品類型查詢單一產品
    public ProductType searchByProductType(Integer productTypeId) { 
        Session session = sessionFactory.getCurrentSession();
        Query<ProductType> query = session.createQuery("from ProductType where productTypeId = :productTypeId", ProductType.class);
        query.setParameter("productTypeId", productTypeId);
        return query.uniqueResult(); 
    }
    
    // 查詢產品價格
    public Integer searchProductPriceById(Integer productId) {
    	Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p.productPrice FROM ProductBean p WHERE p.productId = :productId";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("productId", productId);
        Integer productPrice = query.uniqueResult();
        return productPrice;
    }
}
