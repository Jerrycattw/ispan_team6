package com.coupon.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.coupon.bean.CouponBean;
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
				couponBean.getMembers().size()
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
	public void insertCoupon(String couponCode,String couponDescription,String couponStartDate,String couponEndDate,String maxCoupon,String perMaxCoupon,
			String couponStatus,String rulesDescription,String discountType,String discount,String minOrderDiscount,String  maxDiscount, String[] productTags,String[] togoTags) {
		LocalDate localDateCouponStartDate=LocalDate.parse(couponStartDate);
		LocalDate localDateCouponEndDate=LocalDate.parse(couponEndDate);
		int intMaxCoupon=Integer.parseInt(maxCoupon);
		int intPerMaxCoupon=Integer.parseInt(perMaxCoupon);
		int intDiscount=Integer.parseInt(discount);
		int intMinOrderDiscount=Integer.parseInt(minOrderDiscount);
		int intMaxDiscount=Integer.parseInt(maxDiscount);
		
		couponDao2.insertCoupon(couponCode, couponDescription, localDateCouponStartDate, localDateCouponEndDate, intMaxCoupon, intPerMaxCoupon, couponStatus, rulesDescription, discountType, intDiscount, intMinOrderDiscount, intMaxDiscount, productTags, togoTags);
	}
	
	//delete
	public void deleteCouponById(String couponId) {
		int intCouponId = Integer.parseInt(couponId);
		couponDao2.deleteCoupon(intCouponId);
	}
	
	//update
	public void updateCoupon(String couponId,String  couponCode,String  couponDescription,String  couponStartDate,String couponEndDate,String maxCoupon,
			String perMaxCoupon,String couponStatus,String rulesDescription,String discountType,String discount,String minOrderDiscount,String maxDiscount, String[] productTags,String[] togoTags) {
		int intCouponId=Integer.parseInt(couponId);
		LocalDate localDateCouponStartDate=LocalDate.parse(couponStartDate);
		LocalDate localDateCouponEndDate=LocalDate.parse(couponEndDate);
		int intMaxCoupon=Integer.parseInt(maxCoupon);
		int intPerMaxCoupon=Integer.parseInt(perMaxCoupon);
		int intDiscount=Integer.parseInt(discount);
		int intMinOrderDiscount=Integer.parseInt(minOrderDiscount);
		int intMaxDiscount=Integer.parseInt(maxDiscount);
		
		couponDao2.updateCoupon(intCouponId, couponCode, couponDescription, localDateCouponStartDate, localDateCouponEndDate, intMaxCoupon, intPerMaxCoupon, couponStatus, rulesDescription, discountType, intDiscount, intMinOrderDiscount, intMaxDiscount, productTags, togoTags);
	}
	
}
