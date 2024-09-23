package com.TogoOrder.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.service.TogoItemService;

@Service
public class TogoCalculateUtils {
	
	@Autowired
	private static TogoItemService togoItemService;
	
	public static Integer sumOfTotalPrice(Integer togoId) {
		
		
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.getCurrentSession();
//		  
//		TogoItemServiceImpl togoItemService = new TogoItemServiceImpl(session);
		List<TogoItemBean> togoItemList = togoItemService.getTogoItemByTogoId(togoId);
		
		int newTotalPrice = 0;
		for(TogoItemBean item:togoItemList) {
			newTotalPrice = newTotalPrice+ item.getTogoItemPrice();	
		}
		System.out.println(togoId);
		System.out.println("成功更新"+newTotalPrice);
		
		return newTotalPrice;
	
	}
	
}
