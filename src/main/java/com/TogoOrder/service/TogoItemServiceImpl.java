package com.TogoOrder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.dao.TogoItemDao;

public class TogoItemServiceImpl implements TogoItemService {
	
	@Autowired
	private TogoItemDao togoItemDao;
	
	@Override
	public TogoItemBean addTogoItem(TogoItemBean togoItem) {
		
		return togoItemDao.addTogoItem(togoItem);
	}

	@Override
	public boolean deleteTogoItemById(Integer togoId) {
		
		return togoItemDao.deleteTogoItemById(togoId);
	}

	@Override
	public boolean deleteTogoItemByTogoIdFoodId(Integer togoId, Integer foodId) {
		
		return togoItemDao.deleteTogoItemByTogoIdFoodId(togoId, foodId);
	}

	@Override
	public TogoItemBean updateTogoItemByTogoIdFoodId(TogoItemBean togoItem) {
		
		return togoItemDao.updateTogoItemByTogoIdFoodId(togoItem);
	}

	@Override
	public TogoItemBean getTogoItemByTogoIdFoodId(Integer togoId, Integer foodId) {
		
		return togoItemDao.getTogoItemByTogoIdFoodId(togoId, foodId);
	}

	@Override
	public List<TogoItemBean> getTogoItemByTogoId(Integer togoId) {
		
		return togoItemDao.getTogoItemByTogoId(togoId);
	}

	@Override
	public List<TogoItemBean> getAllTogoItem() {
		
		return togoItemDao.getAllTogoItem();
	}

}
