package com.TogoOrder.dao;

import java.util.List;

import com.TogoOrder.bean.MenuBean;

public interface MenuDao {
	//新增
	MenuBean addFood(MenuBean food);
	//刪除
	boolean deleteFoodById(Integer foodId);
	boolean deleteFoodByName(String foodName);
	boolean deleteFoodByStatus(Integer foodStatus);
	//更新
	MenuBean updateFoodStatusById(Integer foodId, Integer foodStatus);
	MenuBean updateFoodStatusByName(String foodName, Integer foodStatus);
	MenuBean updateFoodById(MenuBean food);
	MenuBean updateFoodByName(MenuBean food);
	//查詢單筆
	MenuBean getFoodById(Integer foodId);	
	//查詢多筆
	List<MenuBean> getAllFoods();
	List<MenuBean> getFoodByName(String foodName);
}
