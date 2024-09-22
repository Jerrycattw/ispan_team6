package com.TogoOrder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TogoOrder.bean.MenuBean;
import com.TogoOrder.dao.MenuDaoImpl;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDaoImpl menuDao;
	
	@Override
	public MenuBean addFood(MenuBean food) {
		
		return menuDao.addFood(food);
	}

	@Override
	public boolean deleteFoodById(Integer foodId) {
		if (menuDao.checkFoodReferences(foodId)) {
			return false;
		}
		return menuDao.deleteFoodById(foodId);
	}

	@Override
	public boolean deleteFoodByName(String foodName) {
		
		return menuDao.deleteFoodByName(foodName);
	}

	@Override
	public boolean deleteFoodByStatus(Integer foodStatus) {
		
		return menuDao.deleteFoodByStatus(foodStatus);
	}

	@Override
	public MenuBean updateFoodStatusById(Integer foodId, Integer foodStatus) {
		
		return menuDao.updateFoodStatusById(foodId, foodStatus);
	}

	@Override
	public MenuBean updateFoodStatusByName(String foodName, Integer foodStatus) {
		
		return menuDao.updateFoodStatusByName(foodName, foodStatus);
	}

	@Override
	public MenuBean updateFoodById(MenuBean food) {
		
		return menuDao.updateFoodById(food);
	}

	@Override
	public MenuBean updateFoodByName(MenuBean food) {
		
		return menuDao.updateFoodByName(food);
	}

	@Override
	public MenuBean getFoodById(Integer foodId) {
		
		return menuDao.getFoodById(foodId);
	}

	@Override
	public List<MenuBean> getAllFoods() {
		
		return menuDao.getAllFoods();
	}

	@Override
	public List<MenuBean> getFoodByName(String foodName) {
		
		return menuDao.getFoodByName(foodName);
	}
	
	

}
