package com.coupon.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.coupon.bean.CouponBean;
import com.coupon.bean.CouponMemberBean;
import com.coupon.bean.TagId;
import com.coupon.dao.CouponDao;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.CouponDistributeDTO;
import com.coupon.dto.TagDTO;


public class CouponService {
	private CouponDao couponDao;
	
	public CouponService(Session session) {
		couponDao = new CouponDao(session);
	}
	
	//查詢所有couponWithTags
	public List<CouponBean> getAllCouponWithTagsAndReceived(){
		List<CouponBean> coupons = new ArrayList<>();
		coupons=couponDao.getAllCouponWithTagsAndReceived();
		return coupons;
	}
	
	//查詢單個couponWithTags 模糊查詢(search)
	public List<CouponBean> getAllCouponWithTagsAndReceived(String keyWord){
		List<CouponBean> coupons = new ArrayList<>();
		coupons=couponDao.getAllCouponWithTagsAndReceived(keyWord);
		return coupons;
	}
	
	//查詢單個couponWithTags ByID
	public CouponBean getCouponWithTags(String couponId) {
		CouponBean coupon=new CouponBean();
		int intCouponId = Integer.parseInt(couponId);
		coupon=couponDao.getCouponWithTags(intCouponId);
		return coupon;
	}
	
	//查詢所有Tags標籤(這裡有增加兩個非product跟togo的標籤)
	public Map<String, List<String>> getTagOptions() {
		Map<String, List<String>> tagOptions = new HashMap<>();
		List<String> productList=couponDao.getProductTypeTagsOption();
		productList.add("商城(全品項)");
		tagOptions.put("product", productList);
		List<String> togoList=couponDao.getTogoTypeTagsOption();
		togoList.add("訂餐(全品項)");
		tagOptions.put("togo", togoList);
		return tagOptions;
	}
	
	//新增coupon
	public int insertCoupon(String couponCode,String couponDescription,String couponStartDate,String couponEndDate,String maxCoupon,String perMaxCoupon,
			String couponStatus,String rulesDescription,String discountType,String discount,String minOrderDiscount,String  maxDiscount) {
		LocalDate localDateCouponStartDate=LocalDate.parse(couponStartDate);
		LocalDate localDateCouponEndDate=LocalDate.parse(couponEndDate);
		int intMaxCoupon=Integer.parseInt(maxCoupon);
		int intPerMaxCoupon=Integer.parseInt(perMaxCoupon);
		int intDiscount=Integer.parseInt(discount);
		int intMinOrderDiscount=Integer.parseInt(minOrderDiscount);
		int intMaxDiscount=Integer.parseInt(maxDiscount);
		//DAO
		int newCouponId=couponDao.insertCoupon(couponCode, couponDescription,localDateCouponStartDate,localDateCouponEndDate,intMaxCoupon,intPerMaxCoupon,couponStatus,
				rulesDescription,discountType,intDiscount,intMinOrderDiscount,intMaxDiscount);
		return newCouponId;
	}
	
	//新增tags service
	public void insertCouponTag(int newCouponId,String[] productTags,String[] togoTags) {
		//product
		if( productTags!=null) {
			System.out.println();
			for (String tag : productTags) {
				System.out.println(tag);
				TagId tagId = new TagId(newCouponId,tag);
				System.out.println(tagId);
				couponDao.insertCouponTags(tagId,"product");
			}			
		}		
		//togo
		if(togoTags != null) {
			for (String tag : togoTags) {
				TagId tagId = new TagId(newCouponId,tag);
				couponDao.insertCouponTags(tagId,"togo");
			}			
		}
	}
	
	//刪除coupon tags
	public void deleteCouponWithTags(String couponCode) {
		int intCouponId=Integer.parseInt(couponCode);
		couponDao.deleteCoupon(intCouponId);
		couponDao.deleteTags(intCouponId);		
	}
	
	//更新coupon tags
	public void updateCoupon(String couponId,String  couponCode,String  couponDescription,String  couponStartDate,String couponEndDate,String maxCoupon,
			String perMaxCoupon,String couponStatus,String rulesDescription,String discountType,String discount,String minOrderDiscount,String maxDiscount) {
		int intCouponId=Integer.parseInt(couponId);
		LocalDate localDateCouponStartDate=LocalDate.parse(couponStartDate);
		LocalDate localDateCouponEndDate=LocalDate.parse(couponEndDate);
		int intMaxCoupon=Integer.parseInt(maxCoupon);
		int intPerMaxCoupon=Integer.parseInt(perMaxCoupon);
		int intDiscount=Integer.parseInt(discount);
		int intMinOrderDiscount=Integer.parseInt(minOrderDiscount);
		int intMaxDiscount=Integer.parseInt(maxDiscount);
		couponDao.updateCoupon(intCouponId,couponCode,couponDescription,localDateCouponStartDate,localDateCouponEndDate,intMaxCoupon,intPerMaxCoupon,couponStatus,
				rulesDescription,discountType,intDiscount,intMinOrderDiscount,intMaxDiscount);
	}
	
	//刪除tags
	public void deleteCouponTag(String couponId,String[] productTags,String[] togoTags) {
		int intCouponId=Integer.parseInt(couponId);
		couponDao.deleteTags(intCouponId);
	}
	//convert DTOs
	public List<CouponDTO> convertCouponsToDTOs(List<CouponBean> couponBeans) {
	    return couponBeans.stream()
	            .map(couponBean -> {
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
	                        couponBean.getReceivedAmount()
	                );
	            })
	            .collect(Collectors.toList());
	}
	
	//convert DTO
	public CouponDTO convertCouponToDTO(CouponBean couponBean) {
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
	            couponBean.getReceivedAmount()
	    );
	}
	
	
	
	//distribute Option
//	public List<CouponDistributeBean> getDistributeOption(String memberIds){
//		//
//		String[] arrayMemberIds=memberIds.split(",");
//		int dietributeAmount=arrayMemberIds.length;
//		
//		
//		List<CouponDistributeBean> couponDistributes = new ArrayList<>();
//		List<CouponDistributeBean> NewCouponDistributes = new ArrayList<>();
//		
//		couponDistributes=couponDao.getDistributeOption();
//		for (CouponDistributeBean couponDistributeBean : couponDistributes) {
//			int maxAmount=couponDistributeBean.getMaxCoupon();
//			int receivedAmount=couponDistributeBean.getReceivedAmount();
//			int usageAmount=maxAmount-receivedAmount;
//			if(maxAmount==0) {
//				NewCouponDistributes.add(couponDistributeBean);
//			}else if(usageAmount >= dietributeAmount){
//				NewCouponDistributes.add(couponDistributeBean);
//			}
//		}
//		return NewCouponDistributes; 
		
//		List<CouponBean> couponBeans = couponDao.getAllCouponWithTagsAndReceived();
//	}
	
	//getOneCoupon
	public CouponBean getOneCoupon(String couponId){
		CouponBean coupon = couponDao.getOneCoupon(couponId);
		return coupon; 
		
	}
	
	//發放優惠券
//	public List<CouponMemberBean> distributeExcute(String memberIds,String couponId,String perMaxCoupon) {
//		List<CouponMemberBean> CouponMembers=new ArrayList<CouponMemberBean>();
//		int PerMaxAmount=Integer.parseInt(perMaxCoupon);
//		//memberIds="1,2,3,..."
//		String[] arrayMemberIds = memberIds.split(",");
//		for (String memberId : arrayMemberIds) {
//			CouponMemberBean couponMember = couponDao.sumMemberCoupon(memberId,couponId);
//			
//			if(couponMember==null) {           //null表示尚未領取過
//				couponMember=new CouponMemberBean();
//				couponMember.setMemberId(Integer.parseInt(memberId));
//				couponMember.setCouponId(Integer.parseInt(couponId));
//				couponMember.setReceivedAmount(0);
//			}
//			//
//			int memberAmount=couponMember.getReceivedAmount();
//			
//			if(PerMaxAmount==0) {              //無限制預設為0
//				couponMember.setDistributeStatus("成功");
//				couponDao.insertMemberCoupon(memberId, couponId,1);//參數3為status,0已使用,1可使用
//				CouponMembers.add(couponMember);
//				System.out.println("success");
//			}else if(memberAmount>=PerMaxAmount){
//				couponMember.setDistributeStatus("失敗");
//				couponMember.setDistributeFailReason("個人領取數量已達上限");
//				CouponMembers.add(couponMember);
//				System.out.println("fail");
//			}else {
//				couponMember.setDistributeStatus("成功");
//				couponDao.insertMemberCoupon(memberId, couponId,1);//參數3為status,0已使用,1可使用
//				CouponMembers.add(couponMember);
//				System.out.println("success");
//			}
//			
//
//		}
//		System.out.println(CouponMembers);
//		return CouponMembers;
//	}
	
	
	
}
