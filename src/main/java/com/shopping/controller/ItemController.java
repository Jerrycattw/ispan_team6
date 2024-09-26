package com.shopping.controller;
import jakarta.servlet.ServletException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.shopping.bean.ItemBean;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.service.ItemService;
import com.shopping.service.ShoppingService;
import com.shopping.service.ProductService;
import java.io.IOException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/ItemController/*")
@Transactional
@Controller
public class ItemController {
	
	
	@Autowired
	ItemService itemService;
	@Autowired
	ProductService productService;
	@Autowired
	ShoppingService shoppingService;
	
	
	
	 
	 @GetMapping("addItem")
	 public String addItem(@RequestParam(name="shoppingId",required = false) Integer shoppingId,
	                       @RequestParam(name="productId") Integer productId,
	                       @RequestParam(name="shoppingItemQuantity") Integer shoppingItemQuantity,
	                       Model m) {


	     if (shoppingId == null) {
	         shoppingId = (Integer) m.asMap().get("shoppingId");
	         						//從 Model 中獲取屬性值過 model.asMap().get("shoppingId") 來檢查是否在其他地方設置過該值
	     }

	     ProductBean productBean = productService.searchByProductId(productId);
	     int productPrice = productBean.getProductPrice();
	     int itemPrice = shoppingItemQuantity * productPrice;

	     ItemBean itemBean = new ItemBean(shoppingId, productId, shoppingItemQuantity);
	     itemBean.setShoppingItemPrice(itemPrice);
	     itemService.addItem(itemBean);

	     ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
	     shopping.setShoppingTotal(itemService.calculateTotalAmount(shoppingId));
	     shoppingService.updateShopping(shopping);
	     shoppingService.addTotal(shoppingItemQuantity, productId, shoppingId);

	     return "redirect:/ShoppingController/showItemDetail?shoppingId=" + shoppingId;
	 }

	 @PostMapping("addItemD")
	 public String addItemD(@RequestParam(name="shoppingId",required = false) Integer shoppingId,
	                       @RequestParam(name="productId") Integer productId,
	                       @RequestParam(name="shoppingItemQuantity") Integer shoppingItemQuantity,
	                       Model m) {


	     if (shoppingId == null) {
	         shoppingId = (Integer) m.asMap().get("shoppingId");
	         						//從 Model 中獲取屬性值過 model.asMap().get("shoppingId") 來檢查是否在其他地方設置過該值
	     }

	     ProductBean productBean = productService.searchByProductId(productId);
	     int productPrice = productBean.getProductPrice();
	     int itemPrice = shoppingItemQuantity * productPrice;

	     ItemBean itemBean = new ItemBean(shoppingId, productId, shoppingItemQuantity);
	     itemBean.setShoppingItemPrice(itemPrice);
	     itemService.addItem(itemBean);

	     ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
	     shopping.setShoppingTotal(itemService.calculateTotalAmount(shoppingId));
	     shoppingService.updateShopping(shopping);
	     shoppingService.addTotal(shoppingItemQuantity, productId, shoppingId);

	     return "redirect:/ShoppingController/showItemDetail?shoppingId=" + shoppingId;
	 }


//	protected void GetProductInfo(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//
//		String productName = request.getParameter("name");
//		String productAmount = request.getParameter("amount");
//		System.out.println(productAmount);
//		ProductBean product = productService.searchProductName(productName);
//
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//		Gson gson = new Gson();
//		String jsonResponse = gson.toJson(product);
//		response.getWriter().write(jsonResponse);
//	}

	
	@PostMapping("/delItem")
    public String delItem(@RequestParam(name="shoppingId") Integer shoppingId,
                          @RequestParam(name="productId") Integer productId,
                          Model m) {
        
        System.out.println(shoppingId);
        System.out.println(productId);
        
        boolean success = itemService.deleteItem(shoppingId, productId);
        itemService.updateShoppingTotal(shoppingId);
        
        System.out.println("Deletion success: " + success);
        
        m.addAttribute("success", success); 
        return "redirect:/ShoppingController/showItemDetail?shoppingId=" + shoppingId;
    }



	@PostMapping("showUpdateItem")
	public String showUpdateItem(@RequestParam(name="shoppingId") String shoppingId,
	                              @RequestParam(name="productId") String productId,
	                              Model m) {

	    System.out.println(shoppingId);
	    System.out.println(productId);
	    
	    ItemBean item = itemService.searchItem(Integer.parseInt(shoppingId), Integer.parseInt(productId));
	    System.out.println(item);
	    
	    //重新載入關連到product
	    ProductBean productBean = productService.searchByProductId(Integer.valueOf(productId));
	    
	    m.addAttribute("item", item);
	    
	    
	    return "Shopping/UpdateItem"; 
	}
	 
	

	@PostMapping("updateItem")
	public String updateItem(@RequestParam(name="shoppingItemQuantity") Integer shoppingItemQuantity,
	                         @RequestParam(name="productId") Integer productId,
	                         @RequestParam(name="shoppingId") Integer shoppingId) {

	    itemService.updateItem(shoppingItemQuantity, productId, shoppingId);
	    itemService.updateShoppingTotal(shoppingId);
	    
	    return "redirect:/ShoppingController/showItemDetail?shoppingId=" + shoppingId;
	}

	

}
