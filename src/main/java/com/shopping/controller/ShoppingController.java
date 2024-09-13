package com.shopping.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import com.members.bean.Member;
import com.shopping.bean.ItemBean;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.service.ShoppingService;
import com.shopping.service.ProductService;
import com.shopping.service.ItemService;
import java.io.IOException;
import java.util.List;

@WebServlet("/ShoppingController/*")
public class ShoppingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Session session = null;
	ItemService itemService = null;
	ProductService productService = null;
	ShoppingService shoppingService =null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		session = (Session)request.getAttribute("hibernateSession");
		itemService = new ItemService(session);
		productService = new ProductService(session);
		shoppingService = new ShoppingService(session);
		
		String action = request.getPathInfo().substring(1);
		System.out.println(action+"=========================================");

		switch (action) {
		case "ShowAddOrder":
			ShowAddOrder(request, response);
			break;
		case "ShowItemDetail":
			ShowItemDetail(request, response);
			break;
		case "AddOrder":
			AddOrder(request, response);
			break;
		case "AddShopping":
			AddShopping(request, response);
			break;
		case "DelOrder":
			DelOrder(request, response);
			break;
		case "UpdateShopping":
			UpdateShopping(request, response);
			break;
		case "UpdateDataShopping":
			UpdateDataShopping(request,response);
			break;
		case "SearchAllShopping":
			SearchAllShopping(request, response);
			break;
		}

	}
	
	protected void ShowAddOrder(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		List<Member> members = shoppingService.searchAllMembers();
	    List<ProductBean> products = shoppingService.searchAllProduct();
	    
//	    System.out.println("=========================================");
//	    System.out.println(members);
//	    System.out.println(products);
	    
	    request.setAttribute("members", members);
	    request.setAttribute("products", products);
	    
	    request.getRequestDispatcher("/Shopping/AddOrder.jsp").forward(request, response);
	    
	}
	
	 protected void ShowItemDetail(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
	        
	        String shoppingIdParam = request.getParameter("shoppingId");
	        Integer shoppingId = Integer.parseInt(shoppingIdParam);
	        
	        List<ItemBean> items = shoppingService.searchItemsByShoppingId(shoppingId); 
	        List<ProductBean> productList = shoppingService.searchAllProduct(); 
	        
	        Integer totalAmount = shoppingService.calculateTotalAmount(shoppingId); 
	        ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
	        
	        request.setAttribute("items", items);
	        request.setAttribute("productList", productList);
	        request.setAttribute("shopping",shopping);
	        
	        request.getRequestDispatcher("/Shopping/ItemDetail.jsp").forward(request, response);
	    }
	
	
	
	private void AddOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		int memberId = Integer.parseInt(request.getParameter("memberId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
		
		System.out.println(memberId);
		ShoppingBean shoppingBean = new ShoppingBean(memberId, 1);
		ShoppingBean order = shoppingService.addOrder(shoppingBean);
		
		
		request.setAttribute("shoppingId", order.getShoppingId());
		request.getRequestDispatcher("/ItemController/AddItem").forward(request, response);
	}

	private void AddShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");


		int shoppingTotal = Integer.parseInt(request.getParameter("shoppingTotal"));
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		String memberName = request.getParameter("memberName");
		int shoppingStatus = Integer.parseInt(request.getParameter("shoppingStatus"));
		String shoppingMemo = request.getParameter("shoppingMemo");

		ShoppingBean shoppingBean = new ShoppingBean(shoppingTotal, memberId, shoppingStatus, shoppingMemo,
				shoppingStatus);
		shoppingService.addOrder(shoppingBean);

		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
	}

	protected void DelOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		boolean deleteAllItem = itemService.deleteAllItem(shoppingId);
		System.out.println(deleteAllItem);
		
		shoppingService.deleteShopping(shoppingId);

		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
	}


	protected void UpdateShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
		
		request.setAttribute("shoppingBean", shopping);
		request.getRequestDispatcher("/Shopping/UpdateShopping.jsp").forward(request, response);

	}

	protected void UpdateDataShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		int shoppingTotal = shoppingService.calculateTotalAmount(shoppingId);
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		int shoppingStatus = Integer.parseInt(request.getParameter("shoppingStatus"));
		String shoppingMemo = request.getParameter("shoppingMemo");

		ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
		
		shopping.setShoppingTotal(shoppingTotal);
		shopping.setMemberId(memberId);
		shopping.setShoppingStatus(shoppingStatus);
		shopping.setShoppingMemo(shoppingMemo);

		shoppingService.updateShopping(shopping);
		
		response.sendRedirect(request.getContextPath() + "/ShoppingController/SearchAllShopping");
	}

	protected void SearchAllShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<ShoppingBean> shoppings = shoppingService.searchAllShopping();
		request.setAttribute("shoppings", shoppings);
		request.getRequestDispatcher("/Shopping/SearchAllShopping.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
