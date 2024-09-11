package com.TogoOrder.service;

import java.util.List;

import org.hibernate.Session;

import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.dao.TogoItemDaoImpl;

public class TogoItemServiceImpl implements TogoItemService {
	private TogoItemDaoImpl togoItemDao;
	
	public TogoItemServiceImpl(Session session) {
		togoItemDao = new TogoItemDaoImpl(session);
	}
	
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
