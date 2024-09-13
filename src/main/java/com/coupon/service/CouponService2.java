package com.coupon.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.coupon.bean.CouponBean;
import com.coupon.bean.TagBean;
import com.coupon.bean.TagId;
import com.coupon.dao.CouponDao2;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.TagDTO;

public class CouponService2 {
	private CouponDao2 couponDao2;

	public CouponService2(Session session) {
		couponDao2 = new CouponDao2(session);
	}

	//convert DTO
	public CouponDTO convertCouponDTO(CouponBean couponBean) {
		List<TagDTO> tagDTOs = couponBean.getTags().stream()
				.map(tagBean -> new TagDTO(tagBean.getTagId().getTagName(), tagBean.getTagType()))
				.collect(Collectors.toList());

		return new CouponDTO(
				couponBean.getCouponId(),
				couponBean.getCouponCode(),
				couponBean.getCouponDescription(),
				couponBean.getCouponStartDate(),
				couponBean.getCouponEndDate(),
				couponBean.getMaxCoupon(),
				couponBean.getPerMaxCoupon(),
				couponBean.getCouponStatus(),
				couponBean.getRulesDescription(),
				couponBean.getDiscountType(),
				couponBean.getDiscount(),
				couponBean.getMinOrderDiscount(),
				couponBean.getMaxDiscount(),
				tagDTOs,
				couponBean.getMembers().size()//receivedAmount
		);
	}
	
	//convert DTOs
	public List<CouponDTO> convertCouponDTO(List<CouponBean> couponBeans) {
	    return couponBeans.stream()
	            .map(couponBean -> convertCouponDTO(couponBean))
	            .collect(Collectors.toList());
	}
	
	//getAll Coupon
	public List<CouponDTO> getAllCoupon() {
		List<CouponBean> coupons = couponDao2.getAllCoupon();
		return convertCouponDTO(coupons);
	}
	
	//getById Coupon
	public CouponDTO getCouponById(String couponId) {
		CouponBean coupon = couponDao2.getCouponById(couponId);
		return convertCouponDTO(coupon);
	}
	
	
	//getAll tagsOption(這裡有增加非資料庫的標籤"全品項")
	public Map<String, List<String>> getTagOptions(){
		
		Map<String, List<String>> tagOptions = new HashMap<>();
		
		//product
		List<String> productList = couponDao2.getProductTypeTagsOption();
		productList.add("商城(全品項)");
		tagOptions.put("product", productList);
		
		//togo
		List<String> togoList = couponDao2.getTogoTypeTagsOption();
		togoList.add("訂餐(全品項)");
		tagOptions.put("togo", togoList);
		
		return tagOptions;
		
	}
	
	//insert Coupon
	public void insertCoupon(CouponBean couponBean,String[] productTags, String[] togoTags) {
		addTags(couponBean,productTags,"product");
		addTags(couponBean,togoTags,"togo");	
		couponDao2.insertCoupon(couponBean);
	}
	
	//delete
	public void deleteCouponById(String couponId) {
		int intCouponId = Integer.parseInt(couponId);
		couponDao2.deleteCoupon(intCouponId);
	}
	
	
	//update
		public void updateCoupon(CouponBean couponBean, String[] productTags, String[] togoTags) {	
			addTags(couponBean,productTags,"product");
			addTags(couponBean,togoTags,"togo");
			couponDao2.updateCoupon(couponBean);
		}
		
	//coupon add tags (before CRUD)	
	private void addTags(CouponBean coupon, String[] tags, String tagType) {
	    if (tags != null) {
	        for (String tag : tags) {
	            TagBean tagBean = new TagBean();
	            tagBean.setTagType(tagType);
	            
	            if (coupon.getCouponId() > 0) {
	            	tagBean.setTagId(new TagId(coupon.getCouponId(),tag));//修改透過coupon的ID
	            }else {
	            	tagBean.setTagId(new TagId(tag));//新增的COUPON沒有ID，透過關聯coupon identity新增
	            }
	            
	            tagBean.setCoupon(coupon);
	            coupon.getTags().add(tagBean);
	        }
	    }
	}
}
