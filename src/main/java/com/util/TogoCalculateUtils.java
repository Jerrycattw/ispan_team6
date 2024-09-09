package com.util;

import java.util.List;

import com.TogoOrder.bean.TogoItemBean;
//import com.TogoOrder.dao.TogoItemDaoImpl;
import com.TogoOrder.dao.TogoItemDaoImpl;

public class TogoCalculateUtils {
	
	public static Integer sumOfTotalPrice(Integer togoId) {

		TogoItemDaoImpl togoItemDao = new TogoItemDaoImpl();
		List<TogoItemBean> togoItemList = togoItemDao.getTogoItemByTogoId(togoId);
		
		int newTotalPrice = 0;
		for(TogoItemBean item:togoItemList) {
			newTotalPrice = newTotalPrice+ item.getTogoItemPrice();	
//			System.out.println("訂單編號"+item.getTogoId());
//			System.out.println("訂單價格"+item.getTogoItemPrice());
//			System.out.println("數量"+item.getAmount());
//			System.out.println("訂單加總"+newTotalPrice);
//			System.out.println("==================");
		}
		System.out.println(togoId);
		System.out.println("成功更新"+newTotalPrice);
		
		return newTotalPrice;
	
	}
	
}
