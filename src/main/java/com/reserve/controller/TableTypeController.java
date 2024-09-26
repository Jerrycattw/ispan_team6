package com.reserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reserve.bean.TableType;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;



@Controller
@RequestMapping("/TableType/*")
public class TableTypeController {
	
	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;
	
    @PostMapping("/add")
    public String addTableType(@RequestParam("tableTypeId") String tableTypeId,
                               @RequestParam("tableTypeName") String tableTypeName) {
        TableType tableType = new TableType(tableTypeId, tableTypeName);
        tableTypeService.insert(tableType);
        return "redirect:/TableType/getAllType";
    }

    @GetMapping("/del")
    public String delTableType(@RequestParam("tableTypeId") String tableTypeId) {
        tableTypeService.delete(tableTypeId);
        return "redirect:/TableType/getAllType";
    }

    @GetMapping("/getAllType")
    public String getAllTableType(Model model) {
        List<TableType> tableTypes = tableTypeService.selectAll();
        model.addAttribute("tableTypes", tableTypes);
        return "reserve/GetAllTableTypes";
    }
    
    
	
	
}
