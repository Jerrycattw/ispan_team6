package com.shopping.service;

import java.util.List;

import org.hibernate.Session;

import com.shopping.bean.ItemBean;
import com.shopping.bean.ShoppingBean;
import com.shopping.dao.ItemDao;
import com.shopping.dao.ShoppingDao;

public class ItemService {

    private ItemDao itemDao;
    private ShoppingDao shoppingDao;

    public ItemService(Session session) {
        this.itemDao = new ItemDao(session);
        this.shoppingDao = new ShoppingDao(session);
    }

    public void addItem(ItemBean itemBean) {
        itemDao.addItem(itemBean);
    }

    public boolean deleteItem(int shoppingId, int productId) {
        return itemDao.deleteItem(shoppingId, productId);
    }

    public void updateItem(int shoppingItemQuantity, int productId, int shoppingId) {
        itemDao.updateItem(shoppingItemQuantity, productId, shoppingId);
    }

    public boolean deleteAllItem(int shoppingId) {
        return itemDao.deleteAllItem(shoppingId);
    }
    
    public Integer calculateTotalAmount(Integer shoppingId) {
        return itemDao.calculateTotalAmount(shoppingId);
    }
    
    public ItemBean searchItem(int shoppingId, int productId) {
        return itemDao.searchItem(shoppingId, productId);
    }

    public List<ItemBean> searchItemsByShoppingId(int shoppingId) {
        return itemDao.searchItemsByShoppingId(shoppingId);
    }

    public ShoppingBean searchByShoppingId(int shoppingId) {
        return shoppingDao.searchByShoppingId(shoppingId);
    }

    public void updateShoppingTotal(int shoppingId) {
        ShoppingBean shopping = shoppingDao.searchByShoppingId(shoppingId);
        shopping.setShoppingTotal(itemDao.calculateTotalAmount(shoppingId));
        shoppingDao.updateShopping(shopping);
    }
}
