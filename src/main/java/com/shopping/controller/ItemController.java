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

@WebServlet("/ItemController/*")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	Session session = null;
	ItemService itemService = null;
	ProductService productService = null;
	ShoppingService shoppingService =null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		session = (Session)request.getAttribute("hibernateSession");
		productService = new ProductService(session);
		shoppingService = new ShoppingService(session);
		shoppingService = new ShoppingService(session);
		itemService = new ItemService(session);
		
		// 獲取URL中的操作名稱
		String action = request.getPathInfo().substring(1);
		System.out.println(action);

		switch (action) {
		case "AddItem":
			AddItem(request, response);
			break;
		case "GetProductInfo":
			GetProductInfo(request, response);
			break;
		case "DelItem":
			DelItem(request,response);
			break;
		case "UpdateItem":
			UpdateItem(request,response);
			break;
		case "ShowUpdateItem":
			ShowUpdateItem(request, response);
			break;
		}
	}
	
	
	 protected void ShowUpdateItem(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
		 
	        String shoppingId= request.getParameter("shoppingId");
	        String productId = request.getParameter("productId");
	        
	        System.out.println(shoppingId);
	        System.out.println(productId);
	        
	        ItemBean item = itemService.searchItem(Integer.parseInt(shoppingId), Integer.parseInt(productId));
	        
	        System.out.println(item);
	        
	        request.setAttribute("item", item);
	        request.getRequestDispatcher("/Shopping/UpdateItem.jsp").forward(request, response);
	    }


	private void AddItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Integer shoppingId = null;
		
		if(request.getParameter("shoppingId")!=null) {
			shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		} else {
			shoppingId = (Integer) request.getAttribute("shoppingId");
		}
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
		
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
        
        
        response.sendRedirect(request.getContextPath() + "/ShoppingController/ShowItemDetail?shoppingId=" + shoppingId);
	
	}

	protected void GetProductInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String productName = request.getParameter("name");
		String productAmount = request.getParameter("amount");
		System.out.println(productAmount);
		ProductBean product = productService.searchProductName(productName);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String jsonResponse = gson.toJson(product);
		response.getWriter().write(jsonResponse);
	}


	protected void DelItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		System.out.println(shoppingId);
		System.out.println(productId);

		boolean success =itemService.deleteItem(shoppingId, productId);
		
		itemService.updateShoppingTotal(shoppingId);
		System.out.println(success);
		
		request.getRequestDispatcher("/ShoppingController/ShowItemDetail").forward(request, response);
	}

	
	protected void UpdateItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		itemService.updateItem(shoppingItemQuantity, productId, shoppingId);
		itemService.updateShoppingTotal(shoppingId);
		
        request.getRequestDispatcher("/ShoppingController/ShowItemDetail").forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
