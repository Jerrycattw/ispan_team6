package com.rent.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.rent.bean.Tableware;
import com.rent.bean.TablewareStock;
import com.rent.service.TablewareService;
import com.rent.service.TablewareStockService;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/tablewareController/*")
@SuppressWarnings("unchecked")
public class TablewareController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo().substring(1);
		System.out.println(action);
		switch (action) {
		case "insert":
			insert(request, response);
			break;
		case "search":
			search(request, response);
			break;
		case "getAll":
			getAll(request, response);
			break;
		case "get":
			getById(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "updateStatus":
			updateStatus(request, response);
			break;
		}
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareService tablewareService = new TablewareService(session);
		TablewareStockService tablewareStockService = new TablewareStockService(session);
		
		String tablewareName = request.getParameter("tableware_name");
		int tablewareDeposit = Integer.parseInt(request.getParameter("tableware_deposit"));
		Part filePart = request.getPart("tableware_image");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // 处理文件名
		String tablewareImage = "/EEIT187-6/tableware/tablewareImage/" + fileName;
		String tablewareDescription = request.getParameter("tableware_description");
		String uploadPath = getServletContext().getRealPath("/tableware/tablewareImage");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir(); // 如果目录不存在，则创建
		}
		filePart.write(uploadPath + File.separator + fileName);

		Tableware tableware = tablewareService.insert(tablewareName, tablewareDeposit, tablewareImage,tablewareDescription);
		session.flush();
		
		int tablewareId = tableware.getTablewareId();
        List<TablewareStock> tablewareStocks = new ArrayList<>();
        int index = 0;
        while (true) {
            String restaurantIdParam = request.getParameter("restaurantId" + index);
            String stockParam = request.getParameter("stock" + index);

            if (restaurantIdParam == null) {
                break; // 如果没有更多的restaurantIdParam，退出循环
            }

            Integer restaurantId = Integer.parseInt(restaurantIdParam);
            Integer stock = Integer.parseInt(stockParam);

            TablewareStock tablewareStock = new TablewareStock(tablewareId, restaurantId, stock);
            tablewareStocks.add(tablewareStock);

            index++; // 处理下一个TablewareStock
        }

        // 批量插入库存记录
        for (TablewareStock tablewareStock : tablewareStocks) {
            tablewareStockService.insert(tablewareStock.getTablewareId(), tablewareStock.getRestaurantId(), tablewareStock.getStock());
        }
		
		request.setAttribute("tableware", tableware);
		request.getRequestDispatcher("/tablewareController/getAll").forward(request, response);
	}

	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareService tablewareService = new TablewareService(session);
		List<Tableware> tablewares = tablewareService.getAll();
		request.setAttribute("tablewares", tablewares);
		request.getRequestDispatcher("/tableware/getAll.jsp").forward(request, response);
	}
	
	protected void getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareService tablewareService = new TablewareService(session);
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		Tableware tableware = tablewareService.getById(tablewareId);
		request.setAttribute("tableware", tableware);
		request.getRequestDispatcher("/tableware/update.jsp").forward(request, response);
	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareService tablewareService = new TablewareService(session);

		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		String tablewareName = request.getParameter("tableware_name");
		int tablewareDeposit = Integer.parseInt(request.getParameter("tableware_deposit"));
		String tablewareDescription = request.getParameter("tableware_description");
		int tablewareStatus = Integer.parseInt(request.getParameter("tableware_status"));

		Part filePart = request.getPart("tableware_image");
	    String tablewareImage;
	    if (filePart != null && filePart.getSize() > 0) {
	        // 用户上传了新图片
	        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	        tablewareImage = "/EEIT187-6/tableware/tablewareImage/" + fileName;
	        try {
	            // 获取上传路径
	            String uploadPath = getServletContext().getRealPath("/tableware/tablewareImage");
	            File uploadDir = new File(uploadPath);
	            if (!uploadDir.exists()) {
	                uploadDir.mkdir(); // 如果目录不存在，则创建
	            }
	            // 将文件写入服务器
	            filePart.write(uploadPath + File.separator + fileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        // 没有上传新图片，使用原始图片路径
	        tablewareImage = request.getParameter("original_tableware_image");
	    }
	    Tableware tableware = tablewareService.update(tablewareId, tablewareName, tablewareDeposit, tablewareImage,
	    		tablewareDescription, tablewareStatus);
	    response.sendRedirect(request.getContextPath() + "/tablewareController/getAll");
	}

	protected void updateStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareService tablewareService = new TablewareService(session);
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		try {
			Tableware tableware = tablewareService.updateStatus(tablewareId);
			request.setAttribute("tableware", tableware);
			request.getRequestDispatcher("/tablewareController/getAll").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        TablewareService tablewareService = new TablewareService(session);
        try {
            String keyword = request.getParameter("keyword");
            List<Tableware> tablewares = tablewareService.search(keyword);
            request.setAttribute("tablewares", tablewares);
            request.getRequestDispatcher("/tableware/getAll.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
