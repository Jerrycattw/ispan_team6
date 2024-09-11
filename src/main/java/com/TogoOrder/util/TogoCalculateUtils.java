package com.TogoOrder.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.service.TogoItemServiceImpl;
import com.util.HibernateUtil;

public class TogoCalculateUtils {
	
	public static Integer sumOfTotalPrice(Integer togoId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		  
		TogoItemServiceImpl togoItemService = new TogoItemServiceImpl(session);
		List<TogoItemBean> togoItemList = togoItemService.getTogoItemByTogoId(togoId);
		
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
