package com.TogoOrder.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.service.TogoItemService;
import com.TogoOrder.service.TogoItemServiceImpl;
import com.util.HibernateUtil;

@Component
public class TogoCalculateUtils {

    private static ApplicationContext context;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        TogoCalculateUtils.context = applicationContext;
    }

    // 靜態方法
    public static Integer sumOfTotalPrice(Integer togoId) {
        // 從 ApplicationContext 獲取 Bean
        TogoItemService togoItemService = context.getBean(TogoItemService.class);
        
        List<TogoItemBean> togoItemList = togoItemService.getTogoItemByTogoId(togoId);
        int newTotalPrice = 0;
        for (TogoItemBean item : togoItemList) {
            newTotalPrice = newTotalPrice+ item.getTogoItemPrice();	
        }
        System.out.println(togoId);
        System.out.println("成功更新" + newTotalPrice);
        return newTotalPrice;
    }
}
