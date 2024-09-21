package com.rent.controller;

import java.io.File;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
@Controller
@RequestMapping("/Tableware/*")
public class TablewareController{
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	TablewareService tablewareService;
	@Autowired
	TablewareStockService tablewareStockService;
	
	@SuppressWarnings("unused")
	@PostMapping("insert")
	protected String insert(
			@RequestParam("tableware_name") String tablewareName, 
			@RequestParam("tableware_deposit") Integer tablewareDeposit, 
			@RequestParam("tableware_image") MultipartFile timg,
			@RequestParam("tableware_description") String tablewareDescription,
			@RequestParam("restaurantId") String restaurantIdParam,
			@RequestParam("stock") String stockParam,
			Model model) throws IOException {
		
		String uploadPath = servletContext.getRealPath("/tableware/tablewareImage");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		String fileName = Paths.get(timg.getOriginalFilename()).getFileName().toString();
		File filePart = new File(uploadPath + File.separator + fileName);
		timg.transferTo(filePart);
		String tablewareImage = "/EEIT187-6/tableware/tablewareImage/" + fileName;

		Tableware tableware = tablewareService.insert(tablewareName, tablewareDeposit, tablewareImage,tablewareDescription);
		
		int tablewareId = tableware.getTablewareId();
		
        List<TablewareStock> tablewareStocks = new ArrayList<>();
        int index = 0;
        while (true) {
            restaurantIdParam += index;
            stockParam += index;
            if (restaurantIdParam == null) {
                break; 
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
		return "redirect:/Tableware/getAll";
	}

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
			Model model)
			throws IOException {
		Tableware tableware = new Tableware();
	    String tablewareImage = null;
	    if (timg != null && timg.getSize() > 0) {
	        // 用户上传了新图片
	        String fileName = Paths.get(timg.getOriginalFilename()).getFileName().toString();
	        tablewareImage = "/EEIT187-6/tableware/tablewareImage/" + fileName;
	        try {
	            // 获取上传路径
	            String uploadPath = servletContext.getRealPath("/tableware/tablewareImage");
	            File uploadDir = new File(uploadPath);
	            if (!uploadDir.exists()) {
	                uploadDir.mkdir(); // 如果目录不存在，则创建
	            }
	            // 将文件写入服务器
	            File filePart = new File(uploadPath + File.separator + fileName);
	    		timg.transferTo(filePart);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        // 没有上传新图片，使用原始图片路径
	    	Tableware existingTableware = tablewareService.getById(tablewareId);
	        if (existingTableware != null) {
	        	tableware.setTablewareImage(existingTableware.getTablewareImage());
	        }
	    }
	    tablewareService.update(tablewareId, tablewareName, tablewareDeposit, tablewareImage,
	    		tablewareDescription, tablewareStatus);
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
