package com.TogoOrder.service;

import java.util.List;

import com.TogoOrder.bean.TogoBean;
import com.TogoOrder.dao.TogoDaoImpl;

public class TogoServiceImpl implements TogoService {
	private TogoDaoImpl togoDao;
	
	public TogoServiceImpl() {
		togoDao = new TogoDaoImpl();
	}
	
	@Override
	public TogoBean addTogo(TogoBean togo) {
		
		return togoDao.addTogo(togo);
	}

	@Override
	public boolean deleteTogoById(Integer togoId) {
		
		return togoDao.deleteTogoById(togoId);
	}

	@Override
	public boolean deleteTogoByStatus(Integer togoStatus) {
		
		return togoDao.deleteTogoByStatus(togoStatus);
	}

	@Override
	public TogoBean updateTogoById(TogoBean togo) {
		
		return togoDao.updateTogoById(togo);
	}

	@Override
	public TogoBean updateTogoStatusById(Integer togoId, Integer togoStatus) {
		
		return togoDao.updateTogoStatusById(togoId, togoStatus);
	}

	@Override
	public TogoBean updateTotalPrice(Integer togoId, Integer totalPrice) {
		
		return togoDao.updateTotalPrice(togoId, totalPrice);
	}

	@Override
	public TogoBean getTogoById(Integer togoId) {
		
		return togoDao.getTogoById(togoId);
	}

	@Override
	public List<TogoBean> getAllTogo() {
		
		return togoDao.getAllTogo();
	}

	@Override
	public List<TogoBean> getTogoByPhone(String tgPhone) {
		
		return togoDao.getTogoByPhone(tgPhone);
	}

}
