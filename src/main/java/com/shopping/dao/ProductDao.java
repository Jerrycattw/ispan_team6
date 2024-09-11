package com.shopping.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.shopping.bean.ProductBean;

public class ProductDao {

	private Session session;

	public ProductDao(Session session) {
		this.session = session;
	}

	// 新增
	public ProductBean addProduct(ProductBean bean) {
		if (bean != null) {
			session.persist(bean);
			return bean;
		}
		return null;
	}

	// 刪除
	public boolean deleteProduct(Integer productId) {
		ProductBean resultBean = session.get(ProductBean.class, productId);
		if (resultBean != null) {
			session.remove(resultBean);
			return true;
		}
		return false;
	}

	// 更新
	public ProductBean updateProduct(ProductBean productBean) {
		ProductBean isExist = session.get(ProductBean.class, productBean.getProductId());
			if(isExist!=null) {
				session.merge(productBean);
				return productBean;
		}
		return null;
	}

	// 查詢全部
	public List<ProductBean> searchAllProduct() {
		Query<ProductBean> query = session.createQuery("from ProductBean", ProductBean.class);
		return query.list();
	}

	// 依ID查詢
	public ProductBean searchByProductId(Integer productId) {
		return session.get(ProductBean.class, productId);
	}
	
	// 依品名查詢
		public ProductBean searchProductName(String productName) {
			return session.get(ProductBean.class, productName);
		}

	
	// 依總類查詢
	public ProductBean SearchByProductType(Integer productType) {
		return session.get(ProductBean.class, productType);
	}

	// 根據產品名稱查詢
    public ProductBean searchByProductName(String productName) {
        Query<ProductBean> query = session.createQuery("from ProductBean where productName = :productName", ProductBean.class);
        query.setParameter("productName", productName);
        return query.uniqueResult();
    }

    // 根據產品類型查詢
    public List<ProductBean> searchByProductType(Integer productType) {
        Query<ProductBean> query = session.createQuery("from ProductBean where productType = :productType", ProductBean.class);
        query.setParameter("productType", productType);
        return query.list();
    }
    
    // 查詢產品價格
    public Integer searchProductPriceById(Integer productId) {
        String hql = "SELECT p.productPrice FROM ProductBean p WHERE p.productId = :productId";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("productId", productId);
        Integer productPrice = query.uniqueResult();
        return productPrice;
    }
}