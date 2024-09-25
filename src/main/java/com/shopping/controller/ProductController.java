package com.shopping.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ProductDTO;
import com.shopping.bean.ProductType;
import com.shopping.service.ItemService;
import com.shopping.service.ProductService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.shopping.service.ShoppingService;

@RequestMapping("/ProductController/*")
@Transactional
@Controller
public class ProductController {

	@Autowired
	ItemService itemService;
	@Autowired
	ShoppingService shoppingService;
	@Autowired
	ProductService productService;

	@PostMapping("addProduct")
	public String addProduct(@RequestParam(name = "productName") String productName,
			@RequestParam(name = "productTypeId") Integer productTypeId,
			@RequestParam(name = "productPrice") Integer productPrice,
			@RequestParam(name = "productStock") Integer productStock,
			@RequestParam(name = "productStatus") Integer productStatus,
			@RequestParam(name = "productDescription") String productDescription,
			@RequestParam("productPicture") MultipartFile file, Model m) throws IOException {

//	        String filename = file.getOriginalFilename();
		System.out.println(productName);
		
		String uploadDir = "C:/upload/ProductImg";
		
		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}
		
		String productPicture2="";
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
//			String extension = fileName.substring(fileName.lastIndexOf("."));
//			String newFileName = file.getOriginalFilename();
//			
            // 將檔案寫入指定路徑
            File fileToSave = new File(uploadDir + File.separator + fileName);
			file.transferTo(fileToSave);
			
			productPicture2 = "/EEIT187-6/ProductImg/" + fileName;
		}
		
//		file.transferTo(new File(uploadDir, filename));

		ProductType productType = productService.searchByProductType(productTypeId);
		ProductBean productBean = new ProductBean(productName, productPrice, productPicture2, productStock,
				productStatus, productDescription);
		productBean.setProductType(productType);
		productService.addProduct(productBean);
		
	
		
		
		return "redirect:/ProductController/searchAllProduct"; // 重定向到搜索所有產品
	}

	@PostMapping("delProduct")
	public String delProduct(@RequestParam(name="productId") Integer productId, Model m) {
		productService.deleteProduct(productId);
		return "redirect:/ProductController/searchAllProduct";

	}

	@PostMapping("updateProduct")
	public String updateProduct(@RequestParam(name="productId") Integer productId, Model m) {
		ProductBean product = productService.searchByProductId(productId);
		m.addAttribute("product", product);
		return "Product/UpdateProduct";
	}

	@PostMapping("updateDataProduct")
	public String updateDataProduct(HttpServletRequest request, @RequestParam(name = "productName") String productName,
			@RequestParam(name = "productTypeId") Integer productTypeId,
			@RequestParam(name = "productPrice") Integer productPrice,
			@RequestParam(name = "productStock") Integer productStock,
			@RequestParam(name = "productStatus") Integer productStatus,
			@RequestParam(name = "productDescription") String productDescription,
			@RequestParam(name = "productId") Integer productId,
			@RequestParam(name = "productPicture") MultipartFile file) throws IOException {

		ProductBean product = productService.searchByProductId(productId);
		ProductType productType = productService.searchByProductType(productTypeId);

		String productPicture;
		String filename = file.getOriginalFilename();

		if (filename == null || filename.isEmpty()) {
			productPicture = product.getProductPicture();
		} else {
			String uploadDir = "C:/upload/ProductImg";
			File uploadDirFile = new File(uploadDir);
			if (!uploadDirFile.exists()) {
				uploadDirFile.mkdirs();
			}
			file.transferTo(new File(uploadDir, filename));
			productPicture = "/EEIT187-6/ProductImg/" + filename;
		}

		product.setProductName(productName);
		product.setProductPicture(productPicture);
		product.setProductPrice(productPrice);
		product.setProductStatus(productStatus);
		product.setProductStock(productStock);
		product.setProductType(productType);
		product.setProductDescription(productDescription);

		productService.updateProduct(product);
		return "redirect:/ProductController/searchAllProduct";
	}

	@GetMapping("searchAllProduct")
	public String searchAllProduct(Model m) {

		System.out.println("searchAllProduct.Con");
		List<ProductDTO> products = productService.searchAllProduct();
		for (ProductDTO product : products) {
			System.out.println(product);
		}
		m.addAttribute("products", products);
		return "Product/SearchAllProduct";
	}

}
