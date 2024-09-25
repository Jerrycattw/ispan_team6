package com.shopping.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.members.bean.Member;
import com.shopping.bean.ItemBean;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ProductDTO;
import com.shopping.bean.ShoppingBean;
import com.shopping.service.ShoppingService;
import com.shopping.service.ProductService;
import com.shopping.service.ItemService;
import java.io.IOException;
import java.util.List;

@RequestMapping("/ShoppingController/*")
@Transactional
@Controller
public class ShoppingController  {

	@Autowired
	ItemService itemService;
	@Autowired
	ProductService productService;
	@Autowired
	ShoppingService shoppingService;
	
	
	
	@GetMapping("showAddOrder")
	protected String showAddOrder(Model m) {

		List<Member> members = shoppingService.searchAllMembers();
//	    List<ProductBean> products = shoppingService.searchAllProduct();
		List<ProductDTO> products = productService.searchAllProduct();
	    
	    System.out.println("showAddOrder.con");
	    
	   m.addAttribute("members", members);
	   m.addAttribute("products", products);
	   return "Shopping/AddOrder";
	    
	}
	
	@GetMapping("showItemDetail")
    public String showItemDetail(@RequestParam(name="shoppingId") Integer shoppingId, Model m) {

		List<ItemBean> items = shoppingService.searchItemsByShoppingId(shoppingId);
		List<ProductDTO> productList = productService.searchAllProduct();
//        List<ProductBean> productList = shoppingService.searchAllProduct();
        Integer totalAmount = shoppingService.calculateTotalAmount(shoppingId);
        ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
        
        m.addAttribute("items", items);
        m.addAttribute("productList", productList);
        m.addAttribute("shopping", shopping);
        m.addAttribute("totalAmount", totalAmount);

        return "Shopping/ItemDetail"; 
    }
	
	 
	 @PostMapping("addOrder")
	 public String addOrder(@RequestParam(name="memberId") Integer memberId,
	                        @RequestParam(name="productId") Integer productId,
	                        @RequestParam(name="shoppingItemQuantity") Integer shoppingItemQuantity,
	                        Model m) {

			
			System.out.println(memberId);
			ShoppingBean shoppingBean = new ShoppingBean(memberId, 1);
			ShoppingBean order = shoppingService.addOrder(shoppingBean);
			
		    return "redirect:/ItemController/addItem?shoppingId=" + order.getShoppingId() + 
		            "&productId=" + productId + 
		            "&shoppingItemQuantity=" + shoppingItemQuantity;
		}
	

	 @PostMapping("/delOrder")
	    public String delOrder(@RequestParam(name="shoppingId") Integer shoppingId, Model model) {
	        boolean deleteAllItem = itemService.deleteAllItem(shoppingId);
	        System.out.println(deleteAllItem);

	        shoppingService.deleteShopping(shoppingId);

	        return "redirect:/ShoppingController/searchAllShopping"; 
	    }
	


	@PostMapping("updateShopping")
    public String updateShopping(@RequestParam(name="shoppingId") int shoppingId, Model m) {
        ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
        m.addAttribute("shoppingBean", shopping);
        return "Shopping/UpdateShopping"; 
    }

	
	@PostMapping("updateDataShopping")
	 public String updateDataShopping(@RequestParam(name="shoppingId") int shoppingId,
	                                      @RequestParam(name="memberId") int memberId,
	                                      @RequestParam(name="shoppingStatus") int shoppingStatus,
	                                      @RequestParam(name="shoppingMemo") String shoppingMemo) {
	        int shoppingTotal = shoppingService.calculateTotalAmount(shoppingId);
	        ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);

	        shopping.setShoppingTotal(shoppingTotal);
	        shopping.setMemberId(memberId);
	        shopping.setShoppingStatus(shoppingStatus);
	        shopping.setShoppingMemo(shoppingMemo);

	        shoppingService.updateShopping(shopping);
	        return "redirect:/ShoppingController/searchAllShopping"; 
	    }
	

	@GetMapping("searchAllShopping")
	public String searchAllShopping(Model m) {
		
		System.out.println("searchAllShopping.con");
		List<ShoppingBean> shoppings = shoppingService.searchAllShopping();
		m.addAttribute("shoppings", shoppings);
		return "Shopping/SearchAllShopping";
	}
	
}
