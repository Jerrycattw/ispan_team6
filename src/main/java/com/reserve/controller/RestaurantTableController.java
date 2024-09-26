package com.reserve.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reserve.bean.Restaurant;
import com.reserve.bean.RestaurantTable;
import com.reserve.bean.RestaurantTableId;
import com.reserve.bean.TableDTO;
import com.reserve.bean.TableType;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;


@Controller
@RequestMapping("/Table/*")
@Transactional
public class RestaurantTableController {

	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;

	@GetMapping("add1")
	public String addTable(@RequestParam("restaurantName") String restaurantName, Model model) {
		model.addAttribute("restaurantName", restaurantName);
		List<TableType> tableTypes = tableTypeService.selectAll();
		model.addAttribute("allTypes", tableTypes);
		return "reserve/AddTable";
	}

	@PostMapping("add2")
	public String addTable2(@RequestParam("restaurantName") String restaurantName,
							@RequestParam("tableTypeId") String tableTypeId,
							@RequestParam("tableTypeNumber") Integer tableTypeNumber, Model model) {

		String restaurantId = restaurantService.getRestaurantId(restaurantName);
		TableType tableType = tableTypeService.selectById(tableTypeId);
		String tableTypeName = tableTypeService.getTableTypeName(tableTypeId);
		tableType.setTableTypeName(tableTypeName);
		System.out.println(tableTypeName);
		model.addAttribute("tableTypeName", tableTypeName);
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = new RestaurantTable(restaurantTableId, tableTypeNumber);
		restaurantTableService.insert(restaurantTable);
		return "redirect:/Table/getAll?restaurantId=" + restaurantId ;
	
	}


	@GetMapping("del")
	public String delTable(@RequestParam("restaurantId") String restaurantId,
						   @RequestParam("tableTypeId") String tableTypeId) {
		
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		restaurantTableService.delete(restaurantTableId);

		return "redirect:/Table/getAll?restaurantId=" + restaurantId;
	}

	@GetMapping("getAll")
	public String getAllTable(@RequestParam(value = "restaurantId",required = false) String restaurantId,
							  @RequestParam(value = "restaurantName",required = false) String restaurantName, Model model) {
		
		
		
		if (restaurantId == null && restaurantName != null) {
			restaurantId = restaurantService.getRestaurantId(restaurantName);
		}
		if (restaurantId != null && restaurantName == null) {
			restaurantName = restaurantService.getRestaurantName(restaurantId);
		}

		List<RestaurantTable> tableTypes = restaurantTableService.selectAll(restaurantId);
		List<TableDTO> tableDTOs = new ArrayList<>();

		for (RestaurantTable restaurantTable : tableTypes) {
			tableDTOs.add(new TableDTO(restaurantTable));
		}

		model.addAttribute("name", restaurantName);
		model.addAttribute("tableTypes", tableDTOs);

		return "reserve/GetAllTables";
	}

	@GetMapping("set1")
	public String setTable1(@RequestParam("restaurantId") String restaurantId,
							@RequestParam("tableTypeId") String tableTypeId, Model model) {
		
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = restaurantTableService.selectById(restaurantTableId);
		Restaurant restaurant = restaurantService.selectById(restaurantId);
		TableType tableType = tableTypeService.selectById(tableTypeId);
		
		model.addAttribute("table", restaurantTable);

		return "reserve/SetTableType";
	}


	@PostMapping("set2")
	public String setTable2(@RequestParam("restaurantId") String restaurantId,
			@RequestParam("tableTypeId") String tableTypeId, @RequestParam("tableTypeNumber") Integer tableTypeNumber) {

		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = restaurantTableService.selectById(restaurantTableId);
		restaurantTable.setTableTypeNumber(tableTypeNumber);
		restaurantTableService.update(restaurantTable);

		return "redirect:/Table/getAll?restaurantId=" + restaurantId ;
	}


}
