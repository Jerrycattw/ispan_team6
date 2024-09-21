/*package com.shopping.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ProductType;
import com.shopping.service.ItemService;
import com.shopping.service.ProductService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.service.ShoppingService;

@Controller
//@WebServlet("/ProductController/*")
@RequestMapping("/ProductController/*")
@Transactional
@MultipartConfig
//@MultipartConfig(location="C:/Users/user/Program/EEIT187-6/src/main/webapp/Product/ProductImg")
public class PC{
//	private static final long serialVersionUID = 1L;

//	Session session = null;
//	ItemService itemService = null;
//	ShoppingService shoppingService = null;
//	ProductService productService =null;
	
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {

//		session = (Session)request.getAttribute("hibernateSession");
//		itemService = new ItemService(session);
//		productService = new ProductService(session);
//		shoppingService = new ShoppingService(session);
//		productService = new ProductService(session);
		
		
		// 獲取URL中的操作名稱
//		String action = request.getPathInfo().substring(1);
//		System.out.println(action);
		
//		System.out.println(request.getParameter("product_name"));
//		System.out.println(request.getParameter("product_price"));
//		System.out.println(request.getParameter("product_stock"));
//		System.out.println(request.getParameter("product_status"));
//		System.out.println(request.getParameter("product_description"));
//		System.out.println(request.getParameter("product_type_id"));
//		System.out.println(request.getParameter("product_type_name"));
//		System.out.println(request.getParameter("product_picture"));
		
		
		
//		switch (action) {
//		case "HomePage":
//			HomePage(request, response);
//			break;
//		case "AddProduct": 
//			AddProduct(request, response);
//			break;
//		case "DelProduct":
//			DelProduct(request,response);
//			break;
//		case "UpdateProduct":
//			UpdateProduct(request,response);
//			break;
//		case "UpdateDataProduct":
//			UpdateDataProduct(request,response);
//			break;
//		case "SearchAllProduct":
//			SearchAllProduct(request, response);
//			break;
//		}
//	}
			
	
	protected void HomePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");

		if("AddProduct".equals(action)) {
			response.sendRedirect("/Product/AddProduct.html");
		}else if ("SearchAllProduct".equals(action)) {
			response.sendRedirect("/ProductController/SearchAllProduct");
		}else if ("SearchByCategory".equals(action)) {
			response.sendRedirect("/Product/SearchByCategory.html");
		}
		
	}
	
	
	private void AddProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String productName = request.getParameter("productName");
		
		int productTypeId = Integer.parseInt(request.getParameter("productTypeId"));
//		System.out.println(productTypeId);
		int productPrice = Integer.parseInt(request.getParameter("productPrice"));
		int productStock = Integer.parseInt(request.getParameter("productStock"));
		int productStatus = Integer.parseInt(request.getParameter("productStatus"));
		String productDescription = request.getParameter("productDescription");
		
		Part part = request.getPart("productPicture");
		String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		String uploadDir = getServletContext().getRealPath("/Product/ProductImg");
		File uploadDirFile = new File(uploadDir);
		    if (!uploadDirFile.exists()) {
		        uploadDirFile.mkdirs();
		    }
		    part.write(uploadDir + File.separator + filename);
		
		String productPicture = "/EEIT187-6/Product/ProductImg/" + filename;
		
		
		ProductType productType = session.get(ProductType.class, productTypeId);
		ProductBean productBean = new ProductBean(productName, productPrice, productPicture, productStock, productStatus, productDescription);
		productBean.setProductType(productType);
		productService.addProduct(productBean);
		request.getRequestDispatcher("/ProductController/SearchAllProduct").forward(request, response);
	}
	
	private String getFileName(Part part) {
		String header = part.getHeader("Content-Disposition");
		int slashIdx = header.lastIndexOf("\\");
		String filename;
		if (slashIdx != -1)
			filename = header.substring(slashIdx + 1, header.length()-1);			
		else {
			int idx = header.indexOf("filename");
			filename = header.substring(idx + 10, header.length()-1);
		}
		return filename;
	}
	
	
	
	protected void DelProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int productId = Integer.parseInt(request.getParameter("productId"));

		productService.deleteProduct(productId);
		
		request.getRequestDispatcher("/ProductController/SearchAllProduct").forward(request, response);
	}
	
	
	protected void UpdateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int productId = Integer.parseInt(request.getParameter("productId"));
		
		ProductBean product = productService.searchByProductId(productId);
		
		request.setAttribute("product",product );
		request.getRequestDispatcher("/Product/UpdateProduct.jsp").forward(request, response);
		

	}


	
	protected void UpdateDataProduct(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    String productName = request.getParameter("productName");
	    Integer productPrice = Integer.parseInt(request.getParameter("productPrice"));
	    Integer productStock = Integer.parseInt(request.getParameter("productStock"));
	    Integer productStatus = Integer.parseInt(request.getParameter("productStatus"));
	    String productDescription = request.getParameter("productDescription");
	    Integer productTypeId = Integer.parseInt(request.getParameter("productTypeId"));
	    Integer productId = Integer.parseInt(request.getParameter("productId"));
	    ProductBean product = productService.searchByProductId(productId);
	    ProductType productType = session.get(ProductType.class, productTypeId);
	    
	    ProductBean currentProduct = productService.searchByProductId(productId); 
	    String currentPicture = currentProduct.getProductPicture(); 

	    Part part = request.getPart("productPicture");
	    String filename = getFileName(part);
	    String productPicture = null;

	    if (filename == null || filename.isEmpty()) {
	        productPicture = currentPicture;
	    } else {
	        String uploadPath = getServletContext().getRealPath("/Product/ProductImg");
	        File fileSaveDir = new File(uploadPath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	        }
	        String filePath = Paths.get(uploadPath, filename).toString();
	        part.write(filePath);
	        productPicture = "/EEIT187-6/Product/ProductImg/" + filename;
	    }
	    product.setProductName(productName);
	    product.setProductPicture(productPicture);
	    product.setProductPrice(productPrice);
	    product.setProductStatus(productStatus);
	    product.setProductStock(productStock);
	    product.setProductType(productType); 
	    product.setProductDescription(productDescription);
	    
		productService.updateProduct(product);
	    request.getRequestDispatcher("/ProductController/SearchAllProduct").forward(request, response);
	}

	
	
	
	protected void SearchAllProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<ProductBean> products = productService.searchAllProduct();
		for (ProductBean product : products) {
			System.out.println(product);
		}
		request.setAttribute("products", products);
		 System.out.println("Sea");
		request.getRequestDispatcher("/Product/SearchAllProduct.jsp").forward(request, response);
	}

	protected void SearchByProductId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productTypeName = request.getParameter("product_type_name");
		ProductBean productBean = session.get(ProductBean.class, 3);
		System.out.println(productBean.getProductName());
		
		request.getRequestDispatcher("/Product/SearchByCategory.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	
}
*/