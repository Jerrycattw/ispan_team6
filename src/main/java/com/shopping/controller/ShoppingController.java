package com.shopping.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.members.bean.Member;
import com.shopping.bean.ItemBean;
import com.shopping.bean.ProductBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.dao.ItemDao;
import com.shopping.dao.ProductDao;
import com.shopping.dao.ShoppingDao;
import com.shopping.service.ShoppingService;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingService;
import com.util.HibernateUtil;
import java.io.IOException;
import java.util.List;

@WebServlet("/ShoppingController/*")
public class ShoppingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Session session = null;
	ItemDao itemDao = null;
	ProductDao productDao = null;
	ShoppingDao shoppingDao = null;
	ShoppingService shoppingService =null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		session = (Session)request.getAttribute("hibernateSession");
		itemDao = new ItemDao(session);
		productDao = new ProductDao(session);
		shoppingDao = new ShoppingDao(session);
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
//		case "DelShopping":
//			DelShopping(request, response);
//			break;
		case "UpdateShopping":
			UpdateShopping(request, response);
			break;
		case "UpdateDataShopping":
			UpdateDataShopping(request,response);
			break;
		case "SearchAllShopping":
			SearchAllShopping(request, response);
			break;
//		case "SearchByCategory":
//			SearchByCategory(request,response);
//			break;
		}

	}
	
	protected void ShowAddOrder(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    SessionFactory factory = HibernateUtil.getSessionFactory();
	    Session session = factory.getCurrentSession();
	    
	    ShoppingService shoppingService = new ShoppingService(session);
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
		 
		 
		 	SessionFactory factory = HibernateUtil.getSessionFactory();
	        Session session = factory.getCurrentSession();
	        
	        
	        String shoppingIdParam = request.getParameter("shoppingId");
	        Integer shoppingId = Integer.parseInt(shoppingIdParam);
	        
	        ItemDao itemDao = new ItemDao(session);
	        ProductDao productDao = new ProductDao(session);
	        
//	        List<ItemBean> items = itemDao.searchItemsByShoppingId(shoppingId);
//	        List<ProductBean> productList = productDao.searchAllProduct();
	        
	        List<ItemBean> items = shoppingService.searchItemsByShoppingId(shoppingId); 
	        List<ProductBean> productList = shoppingService.searchAllProduct(); 
	        
	        Integer totalAmount = shoppingService.calculateTotalAmount(shoppingId); 
	        ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
	        
//	        Integer totalAmount = itemDao.calculateTotalAmount(shoppingId);
//	        ShoppingDao shoppingDao = new ShoppingDao(session);
//	        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
	        
	        request.setAttribute("items", items);
	        request.setAttribute("productList", productList);
	        request.setAttribute("shopping",shopping);
	        
	        request.getRequestDispatcher("/Shopping/ItemDetail.jsp").forward(request, response);
	    }
	
	
	
	private void AddOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		ItemDao itemDao = new ItemDao(session);
		ShoppingDao shoppingDao = new ShoppingDao(session);

		int memberId = Integer.parseInt(request.getParameter("memberId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int shoppingItemQuantity = Integer.parseInt(request.getParameter("shoppingItemQuantity"));
//		ShoppingDao shoppingDao = new ShoppingDao();
//		int shoppingId = shoppingDao.getMemberId(memberId);
		
		System.out.println(memberId);
		ShoppingBean shoppingBean = new ShoppingBean(memberId, 1);
		ShoppingBean order = shoppingDao.addOrder(shoppingBean);
		
//		System.out.println(order);
//		
//		System.out.println(productId);
//		System.out.println(shoppingItemQuantity);
		
//		ItemBean itemBean = new ItemBean(order.getShoppingId(), productId, shoppingItemQuantity);
//		itemDao.addItem(itemBean);
		
//		ShoppingService shoppingService = new ShoppingService(session);
//		ProductService productService = new ProductService(session);
//		
//		
//		Member member = shoppingService.searchMemberById(memberId);  
//	    ProductBean product = productService.searchByProductId(productId);
//	    
//	    ShoppingBean shoppingBean = new ShoppingBean();
	    
	    
	    
//        ShoppingBean shopping = shoppingDao.searchByShoppingId(order.getShoppingId());
//        shopping.setShoppingTotal(itemDao.calculateTotalAmount(order.getShoppingId()));
//        shoppingDao.updateShopping(shopping);
	    
//        shoppingBean.setShoppingTotal(product.getProductPrice() * shoppingItemQuantity);  
//        shoppingBean.setShoppingStatus(1);
//        shoppingBean.setShoppingMemo("");
	    
//		shoppingService.addOrder(itemBean);
		
		request.setAttribute("shoppingId", order.getShoppingId());
		
//		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
		request.getRequestDispatcher("/ItemController/AddItem").forward(request, response);
	}

	private void AddShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();

		int shoppingTotal = Integer.parseInt(request.getParameter("shoppingTotal"));
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		String memberName = request.getParameter("memberName");
		int shoppingStatus = Integer.parseInt(request.getParameter("shoppingStatus"));
		String shoppingMemo = request.getParameter("shoppingMemo");

		ShoppingBean shoppingBean = new ShoppingBean(shoppingTotal, memberId, shoppingStatus, shoppingMemo,
				shoppingStatus);
		ShoppingService shoppingtService = new ShoppingService(session);
		shoppingtService.addOrder(shoppingBean);

		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
	}

	protected void DelOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		ItemDao itemDao = new ItemDao(session);
		boolean deleteAllItem = itemDao.deleteAllItem(shoppingId);
		System.out.println(deleteAllItem);
		
//		ShoppingDao shoppingDao = new ShoppingDao(session);
//		shoppingDao.deleteShopping(shoppingId);
		
		shoppingService.deleteShopping(shoppingId);
//		ItemBean itemBean = new ItemBean();
//		ShoppingService shoppingtService = new ShoppingService(session);
//		shoppingtService.deleteO(shoppingId);

		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
	}

//	protected void DelShopping(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//		Session session = factory.getCurrentSession();
//
//		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
//
//		ShoppingBean shoppingBean = new ShoppingBean(shoppingId);
//		ShoppingService shoppingService = new ShoppingService(session);
//		shoppingService.deleteShopping(shoppingId);
//
//		request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
//	}

	protected void UpdateShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
		
		
//		ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
		ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
		
		request.setAttribute("shoppingBean", shopping);
		request.getRequestDispatcher("/Shopping/UpdateShopping.jsp").forward(request, response);

	}

	protected void UpdateDataShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
//		ShoppingDao shoppingDao = new ShoppingDao(session);
//		ItemDao itemDao = new ItemDao(session);

		int shoppingId = Integer.parseInt(request.getParameter("shoppingId"));
//		int shoppingTotal = itemDao.calculateTotalAmount(shoppingId);
		int shoppingTotal = shoppingService.calculateTotalAmount(shoppingId);
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		int shoppingStatus = Integer.parseInt(request.getParameter("shoppingStatus"));
		String shoppingMemo = request.getParameter("shoppingMemo");


		ShoppingBean shopping = shoppingService.searchByShoppingId(shoppingId);
//		ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
		
		
		
		shopping.setShoppingTotal(shoppingTotal);
		shopping.setMemberId(memberId);
		shopping.setShoppingStatus(shoppingStatus);
		shopping.setShoppingMemo(shoppingMemo);
		
		ShoppingService shoppingService = new ShoppingService(session);
		shoppingService.updateShopping(shopping);
		
//		ShoppingBean S = new ShoppingBean(shoppingTotal,memberId, shoppingStatus,shoppingMemo,shoppingId);
//		shoppingDao.updateShopping(shoppingTotal,memberId, shoppingStatus,shoppingMemo,shoppingId);

//	    request.getRequestDispatcher("/ShoppingController/SearchAllShopping").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/ShoppingController/SearchAllShopping");
	}

	protected void SearchAllShopping(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		ShoppingService shoppingService = new ShoppingService(session);
		List<ShoppingBean> shoppings = shoppingService.searchAllShopping();
		request.setAttribute("shoppings", shoppings);
		request.getRequestDispatcher("/Shopping/SearchAllShopping.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
