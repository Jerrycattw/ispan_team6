package com.TogoOrder.dao;

import java.util.List;

import com.TogoOrder.bean.TogoBean;

public interface TogoDao {
	//新增
	TogoBean addTogo(TogoBean togo);
	//刪除
	boolean deleteTogoById(Integer togoId);
	boolean deleteTogoByStatus(Integer togoStatus);
	//更新
	TogoBean updateTogoById(TogoBean togo);
	TogoBean updateTogoStatusById(Integer togoId, Integer togoStatus);
	TogoBean updateTotalPrice(Integer togoId, Integer totalPrice);
	//查詢單筆
	TogoBean getTogoById(Integer togoId);
	//查詢多筆
	List<TogoBean> getAllTogo();
	List<TogoBean> getTogoByPhone(String tgPhone);
	
	
}
