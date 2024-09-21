package com.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.bean.ProductBean;
import com.shopping.bean.ProductDTO;
import com.shopping.bean.ProductType;
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
	
	public ProductDTO addProduct(ProductDTO DTO) {
		return pDao.addProduct(DTO);
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
	
	public ProductType searchByProductType(Integer productTypeId) { 
	    return pDao.searchByProductType(productTypeId);
	}
	
	
//	public List<ProductBean> searchAllProduct() {
//		return pDao.searchAllProduct();
//	}
	
	public List<ProductDTO> searchAllProduct() {
		
		
	    List<ProductBean> productBeans = pDao.searchAllProduct();
	    
	    List<ProductDTO> productDTOs = new ArrayList<>();

	    for (ProductBean productBean : productBeans) {
	    	
	        ProductDTO productDTO = new ProductDTO(productBean);
	        
//	        productDTO.setProductId(productBean.getProductId());
//	        productDTO.setProductName(productBean.getProductName());
//	        productDTO.setProductPrice(productBean.getProductPrice());
//	        productDTO.setProductPicture(productBean.getProductPicture());
//	        productDTO.setProductStock(productBean.getProductStock());
//	        productDTO.setProductStatus(productBean.getProductStatus());
//	        productDTO.setProductDescription(productBean.getProductDescription());
//	        
////	        ProductType productType = productBean.getProductType();
////	        productType.getProductId();
//	        
//	        productDTO.setProductTypeId(productBean.getProductType().getProductTypeId());
//	        productDTO.setProductTypeName(productBean.getProductType().getProductTypeName());

	        productDTOs.add(productDTO);
	    }

	    return productDTOs;
	}
	
	
	
	public Integer calculateTotalAmount(Integer shoppingId) {
	    return iDao.calculateTotalAmount(shoppingId);
	}
	
}
