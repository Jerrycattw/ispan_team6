package com.shopping.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.shopping.bean.ItemBean;
import com.shopping.bean.ItemId;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.dao.ItemDao;
import com.shopping.dao.ProductDao;
import com.shopping.dao.ShoppingDao;
import com.shopping.service.ItemService;
import com.shopping.service.ProductService;
import com.util.HibernateUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.tags.shaded.org.apache.bcel.generic.NEW;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@WebServlet("/ItemController/*")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	Session session = null;
	ItemDao itemDao = null;
	ProductDao productDao = null;
	ShoppingDao shoppingDao = null;
	ItemService itemService =null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		session = (Session)request.getAttribute("hibernateSession");
		itemDao = new ItemDao(session);
		productDao = new ProductDao(session);
		shoppingDao = new ShoppingDao(session);
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
//		case "AddOrder": 
//			AddOrder(request, response);
//			break;
//		case "SelectProduct":
//			SelectProduct(request,response);
//			break;
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
	        
	        ItemBean item = itemDao.searchItem(Integer.parseInt(shoppingId), Integer.parseInt(productId));
	        
//	        Integer shoppingId = Integer.parseInt(shoppingIdParam);
//	        
//	        
//	        List<ItemBean> items = itemDao.searchItemsByShoppingId(shoppingId);
//	        List<ProductBean> productList = productDao.searchAllProduct();
//	        
//	        Integer totalAmount = itemDao.calculateTotalAmount(shoppingId);
//	        ShoppingDao shoppingDao = new ShoppingDao(session);
//	        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
	        
	        System.out.println(item);
	        
	        request.setAttribute("item", item);
//	        request.setAttribute("productList", productList);
//	        request.setAttribute("shopping",shopping);
	        
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
		
		ProductBean productBean = productDao.searchByProductId(productId);
		
		int productPrice = productBean.getProductPrice();
        int itemPrice = shoppingItemQuantity * productPrice;
        
        ItemBean itemBean = new ItemBean(shoppingId, productId, shoppingItemQuantity);
        itemBean.setShoppingItemPrice(itemPrice);
//        itemDao.addItem(itemBean);
        
        itemService.addItem(itemBean);
        
        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
        shopping.setShoppingTotal(itemDao.calculateTotalAmount(shoppingId));
        shoppingDao.updateShopping(shopping);
        
        shoppingDao.addTotal(shoppingItemQuantity, productId, shoppingId);
        
        
        response.sendRedirect(request.getContextPath() + "/ShoppingController/ShowItemDetail?shoppingId=" + shoppingId);
	
	}
//
//	private void AddOrder(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		request.setCharacterEncoding("UTF-8");
//		
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//		Session session = factory.getCurrentSession();
//		
//		int memberId = Integer.parseInt(request.getParameter("memberId"));
//		int productId = Integer.parseInt(request.getParameter("productId"));
//		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
//		ShoppingDao shoppingDao = new ShoppingDao(session);
//		ShoppingBean shoppingId = shoppingDao.searchMemberById(memberId);
//		ItemDao itemDao = new ItemDao(session);
//		itemDao.addItem(shoppingId,productId,shoppingItemQuantity);
//		
////		shoppingDao.addShopping(shoppingTotal,memberId, memberName,shoppingStatus,shoppingMemo);
//		
//		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
//	}

	protected void GetProductInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String productName = request.getParameter("name");
		String productAmount = request.getParameter("amount");
		System.out.println(productAmount);
		ProductBean product = productDao.searchProductName(productName);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String jsonResponse = gson.toJson(product);
		response.getWriter().write(jsonResponse);
	}

//	protected void AddItemToOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//        Session session = factory.getCurrentSession();
//		
//        String requestData = request.getReader().lines().collect(Collectors.joining());
//        JsonObject jsonObject = JsonParser.parseString(requestData).getAsJsonObject();
//
//        Integer shoppingId = jsonObject.get("shoppingId").getAsInt();
//        Integer productId = jsonObject.get("productId").getAsInt();
//        Integer shoppingItemQuantity = jsonObject.get("shoppingItemQuantity").getAsInt();
//
//        ItemDao itemDao = new ItemDao(session);
//        ItemBean itemBean = new ItemBean(shoppingId,productId,shoppingItemQuantity);
//       itemDao.addItem(itemBean);
//
//        JsonObject responseJson = new JsonObject();
//        response.setContentType("application/json");
//        response.getWriter().write(responseJson.toString());
//    }

//	protected void SelectProduct(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ProductDao productDao = new ProductDao();
//        List<ProductBean> products = productDao.searchAllProduct();
//        
//        request.setAttribute("products", products);
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/Shopping/ItemDetail.jsp");
//        dispatcher.forward(request, response);
//    }

	protected void DelItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		System.out.println(shoppingId);
		System.out.println(productId);

//		ShoppingDao shoppingDao = new ShoppingDao(session);
//		shoppingDao.deleteItemTotal(shoppingItemQuantity, productId,shoppingId);
		boolean success =itemDao.deleteItem(shoppingId, productId);
		
		itemService.updateShoppingTotal(shoppingId);
		
//        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
//        shopping.setShoppingTotal(itemDao.calculateTotalAmount(shoppingId));
//        shoppingDao.updateShopping(shopping);
		
//		boolean success =itemDao.deleteItem(20, 10001);
		
//		ItemId itemId = new ItemId(10001,20);
//		ItemBean itemBean = session.get(ItemBean.class, itemId);
//		System.out.println(itemBean);
//		session.remove(itemBean);
		
		System.out.println(success);
		
//		response.sendRedirect(request.getContextPath() + "/ShoppingController/ShowItemDetail?shoppingId=" + shoppingId);
		request.getRequestDispatcher("/ShoppingController/ShowItemDetail").forward(request, response);
	}

	protected void UpdateItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		itemService.updateItem(shoppingItemQuantity, productId, shoppingId);
		itemService.updateShoppingTotal(shoppingId);
		
//		itemDao.updateItem(shoppingItemQuantity, productId,shoppingId);
//		
//        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
//        shopping.setShoppingTotal(itemDao.calculateTotalAmount(shoppingId));
//        shoppingDao.updateShopping(shopping);
		
        request.getRequestDispatcher("/ShoppingController/ShowItemDetail").forward(request, response);
//		response.sendRedirect(request.getContextPath() + "/Shopping/ItemDetail.jsp?shoppingId=" + shoppingId);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
