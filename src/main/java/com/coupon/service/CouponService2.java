package com.coupon.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.bean.CouponBean;
import com.coupon.bean.TagBean;
import com.coupon.bean.TagId;
import com.coupon.dao.CouponDao2;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.CouponDistributeDTO;
import com.coupon.dto.TagDTO;


@Service
@Transactional
public class CouponService2 {
	
	@Autowired
	private CouponDao2 couponDao2;
	
	@Autowired
    private CouponService2 self;
	
	//convert distributeDTO
	public CouponDistributeDTO convertCouponDistributeDTO(CouponBean couponBean) {
		return new CouponDistributeDTO(
				couponBean.getCouponId(),
				couponBean.getCouponCode()+couponBean.getCouponDescription(),//selectOption
				couponBean.getReceivedAmount(),
				couponBean.getMaxCoupon()
		);
	}
	
	//convert distributeDTOs
	public List<CouponDistributeDTO> conCouponDistributeDTO(List<CouponBean> couponBeans){
		return couponBeans.stream()
				.map(couponBean -> convertCouponDistributeDTO(couponBean))
				.collect(Collectors.toList());
	}
	
	
	
	//convert couponDTO
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
	
	//convert couponDTOs
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
		self.addTags(couponBean,productTags,"product");
		self.addTags(couponBean,togoTags,"togo");
		couponDao2.insertCoupon(couponBean);
	}
	
	//delete
	public void deleteCouponById(String couponId) {
		int intCouponId = Integer.parseInt(couponId);
		couponDao2.deleteCoupon(intCouponId);
	}
	
	
	//update
	public void updateCoupon(CouponBean couponBean, String[] productTags, String[] togoTags) {
		System.out.println("touch");
		self.addTags(couponBean,productTags,"product");
		self.addTags(couponBean,togoTags,"togo");
		System.out.println("addtags done");
		couponDao2.updateCoupon(couponBean);
		System.out.println("dao DONE");
	}
	
	//search coupon
		public List<CouponDTO> searchCoupons(String keyWord){
			List<CouponBean> searchBeans = couponDao2.searchCoupons(keyWord);
			return convertCouponDTO(searchBeans);
		}
	
	//distrubute option
		public List<CouponDistributeDTO> getDistributeOption(String memberIds){
			String[] arrayMemberIds=memberIds.split(",");
			int distributeAmount=arrayMemberIds.length;
			
			List<CouponBean> allCoupon = couponDao2.getAllCoupon();
			List<CouponBean> couponOption = new ArrayList<CouponBean>();
			for (CouponBean couponBean : couponOption) {
				int maxAmount = couponBean.getMaxCoupon();
				int receivedAmount = couponBean.getMembers().size();
				int usageAmount = maxAmount-receivedAmount;
				
				//0表示無發放限制
				if(maxAmount==0) {    
					couponOption.add(couponBean);
				}else if(usageAmount>= distributeAmount){
					couponOption.add(couponBean);
				}
			}
			return conCouponDistributeDTO(couponOption);
			
		}
		
	//coupon add tags (before CRUD)	
	public void addTags(CouponBean coupon, String[] tags, String tagType) {
	    if (tags != null) {
	        for (String tag : tags) {
	            TagBean tagBean = new TagBean();
	            tagBean.setTagType(tagType);
	            
	            if (coupon.getCouponId() != null) {
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
