package com.rent.controller;

import java.io.File;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rent.bean.Tableware;
import com.rent.bean.TablewareStock;
import com.rent.service.TablewareService;
import com.rent.service.TablewareStockService;
import com.reserve.bean.Restaurant;
import com.reserve.service.RestaurantService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
@Controller
@RequestMapping("/Tableware/*")
public class TablewareController{
	
	@Autowired
	TablewareService tablewareService;
	@Autowired
	TablewareStockService tablewareStockService;
	@Autowired
	RestaurantService restaurantService;
	
	@GetMapping("getOption")
	public String getOption(Model model) {
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		model.addAttribute("restaurantNames", restaurantNames);
		return "tableware/InsertTableware";
	}
	
	@PostMapping("insert")
	protected String insert(
			@RequestParam("tableware_name") String tablewareName, 
			@RequestParam("tableware_deposit") Integer tablewareDeposit, 
			@RequestParam("tableware_image") MultipartFile timg,
			@RequestParam("tableware_description") String tablewareDescription,
			@RequestParam Map<String, String> allParams,
			Model model) throws IOException {
		
		Tableware tableware = new Tableware();
		tableware.setTablewareName(tablewareName);
		tableware.setTablewareDeposit(tablewareDeposit);
		tableware.setTablewareDescription(tablewareDescription);
		tableware.setTablewareStatus(1);
		
		
		String uploadPath = "C:/upload/tablewareIMG";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); // 确保递归创建目录
		}
		if (!timg.isEmpty()) {
			String fileName = timg.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = tablewareName + "_" + System.currentTimeMillis() + extension;
			File filePart = new File(uploadPath + File.separator + newFileName);
			timg.transferTo(filePart);
			tableware.setTablewareImage("/EEIT187-6/tablewareIMG/" + newFileName);
		}
		tablewareService.insert(tableware);
		
		int tablewareId = tableware.getTablewareId();
		
		List<String> restaurantNameParams = new ArrayList<>();
		List<String> stockParams = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : allParams.entrySet()) {
			if (entry.getKey().startsWith("restaurantName")) {
				restaurantNameParams.add(entry.getValue());
			} else if (entry.getKey().startsWith("stock")) {
				stockParams.add(entry.getValue());
			}
		}
		
		List<TablewareStock> tablewareStocks = new ArrayList<>();
		for (int i = 0; i < restaurantNameParams.size(); i++) {
			String restaurantNameParam = restaurantNameParams.get(i);
			String stockParam = stockParams.get(i);
			System.out.println(restaurantNameParam);
			if (restaurantNameParam != null && !restaurantNameParam.isEmpty() && stockParam != null && !stockParam.isEmpty()) {
				String restaurantId = restaurantService.getRestaurantId(restaurantNameParam);
				Integer stock = Integer.parseInt(stockParam);
				
				TablewareStock tablewareStock = new TablewareStock(tablewareId, restaurantId, stock);
				tablewareStocks.add(tablewareStock);
			}
		}
		// 批量插入库存记录
		for (TablewareStock tablewareStock : tablewareStocks) {
			tablewareStockService.insert(tablewareStock.getTablewareId(), tablewareStock.getRestaurantId(), tablewareStock.getStock());
		}
		
		return "redirect:/Tableware/getAll";
	}
	
	
//	@PostMapping("insert")
//	protected String insert(
//			@RequestParam("tableware_name") String tablewareName, 
//			@RequestParam("tableware_deposit") Integer tablewareDeposit, 
//			@RequestParam("tableware_image") MultipartFile timg,
//			@RequestParam("tableware_description") String tablewareDescription,
//			@RequestParam Map<String, String> allParams,
//			Model model) throws IOException {
//		
//		Tableware tableware = new Tableware();
//		tableware.setTablewareName(tablewareName);
//		tableware.setTablewareDeposit(tablewareDeposit);
//		tableware.setTablewareDescription(tablewareDescription);
//		tableware.setTablewareStatus(1);
//		
//		
//		String uploadPath = "C:/upload/tablewareIMG";
//	    File uploadDir = new File(uploadPath);
//	    if (!uploadDir.exists()) {
//	        uploadDir.mkdirs(); // 确保递归创建目录
//	    }
//	    if (!timg.isEmpty()) {
//	    	String fileName = timg.getOriginalFilename();
//            String extension = fileName.substring(fileName.lastIndexOf("."));
//            String newFileName = tablewareName + "_" + System.currentTimeMillis() + extension;
//            File filePart = new File(uploadPath + File.separator + newFileName);
//            timg.transferTo(filePart);
//            tableware.setTablewareImage("/EEIT187-6/tablewareIMG/" + newFileName);
//	    }
//	    tablewareService.insert(tableware);
//		int tablewareId = tableware.getTablewareId();
//		List<String> restaurantIdParams = new ArrayList<>();
//	    List<String> stockParams = new ArrayList<>();
//	    for (Map.Entry<String, String> entry : allParams.entrySet()) {
//	        if (entry.getKey().startsWith("restaurantId")) {
//	            restaurantIdParams.add(entry.getValue());
//	        } else if (entry.getKey().startsWith("stock")) {
//	            stockParams.add(entry.getValue());
//	        }
//	    }
//		
//        List<TablewareStock> tablewareStocks = new ArrayList<>();
//        for (int i = 0; i < restaurantIdParams.size(); i++) {
//            String restaurantId = restaurantIdParams.get(i);
//            String stockParam = stockParams.get(i);
//            if (restaurantId != null && !restaurantId.isEmpty() && stockParam != null && !stockParam.isEmpty()) {
//                Integer stock = Integer.parseInt(stockParam);
//                TablewareStock tablewareStock = new TablewareStock(tablewareId, restaurantId, stock);
//                tablewareStocks.add(tablewareStock);
//            }
//        }
//        // 批量插入库存记录
//        for (TablewareStock tablewareStock : tablewareStocks) {
//            tablewareStockService.insert(tablewareStock.getTablewareId(), tablewareStock.getRestaurantId(), tablewareStock.getStock());
//        }
//        
//		return "redirect:/Tableware/getAll";
//	}

	@GetMapping("getAll")
	protected String getAll(Model model) {
		List<Tableware> tablewares = tablewareService.getAll();
		model.addAttribute("tablewares", tablewares);
		return "tableware/GetAllTableware";
	}
	
	@GetMapping("get")
	protected String getById(@RequestParam("tableware_id") Integer tablewareId, Model model) {
		Tableware tableware = tablewareService.getById(tablewareId);
		model.addAttribute("tableware", tableware);
		return "tableware/UpdateTableware";
	}

	@PostMapping("update")
	protected String update(
			@RequestParam("tableware_id") Integer tablewareId, 
			@RequestParam("tableware_name") String tablewareName, 
			@RequestParam("tableware_deposit") Integer tablewareDeposit, 
			@RequestParam("tableware_image") MultipartFile timg,
			@RequestParam("tableware_description") String tablewareDescription,
			@RequestParam("tableware_status") Integer tablewareStatus,
			Model model) throws IOException {
		Tableware tableware = tablewareService.getById(tablewareId);
		tableware.setTablewareName(tablewareName);
		tableware.setTablewareDeposit(tablewareDeposit);
		tableware.setTablewareDescription(tablewareDescription);
		tableware.setTablewareStatus(tablewareStatus);
		
        String uploadPath = "C:/upload/tablewareIMG";
	    File fileSaveDir = new File(uploadPath);
	    if (!fileSaveDir.exists()) {
	        fileSaveDir.mkdirs();
	    }

	    if (!timg.isEmpty()) {
	        String fileName = timg.getOriginalFilename();
	        String extension = fileName.substring(fileName.lastIndexOf("."));
	        String newFileName = tablewareName + "_" + System.currentTimeMillis() + extension;

	        // 將檔案寫入指定路徑
	        File fileToSave = new File(uploadPath + File.separator + newFileName);
	        timg.transferTo(fileToSave);

	        tableware.setTablewareImage("/EEIT187-6/tablewareIMG/" + newFileName);
	    } else {
	        // 如果沒有上傳新的圖片，保留現有圖片
	        if (timg != null) {
	        	tableware.setTablewareImage(tableware.getTablewareImage());
	        }
	    }
	    tablewareService.update(tableware);
	    return "redirect:/Tableware/getAll";
	}

	@GetMapping("updateStatus")
	protected String updateStatus(@RequestParam("tableware_id") Integer tablewareId, Model model) throws IOException {
		Tableware tableware = tablewareService.updateStatus(tablewareId);
		model.addAttribute("tableware", tableware);
		return "redirect:/Tableware/getAll";
	}

	@GetMapping("search")
	protected String search(@RequestParam("keyword") String keyword, Model model) throws IOException {
		List<Tableware> tablewares = tablewareService.search(keyword);
		model.addAttribute("tablewares", tablewares);
		return "tableware/GetAllTableware";
	}
}
