package com.TogoOrder.dao;

import java.util.List;

import com.TogoOrder.bean.TogoItemBean;

public interface TogoItemDao {
	//新增
	TogoItemBean addTogoItem(TogoItemBean togoItem);
	//刪除
	boolean deleteTogoItemById(Integer togoId);
	boolean deleteTogoItemByTogoIdFoodId(Integer togoId, Integer foodId);
	//更新
	TogoItemBean updateTogoItemByTogoIdFoodId(TogoItemBean togoItem);
	//查詢單筆
	TogoItemBean getTogoItemByTogoIdFoodId(Integer togoId, Integer foodId);
	//查詢多筆
	List<TogoItemBean> getTogoItemByTogoId(Integer togoId);
	List<TogoItemBean> getAllTogoItem();
}
