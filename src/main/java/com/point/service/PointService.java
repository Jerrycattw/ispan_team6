package com.point.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.grammars.hql.HqlParser.CurrentDateFunctionContext;

import com.coupon.dao.CouponDao;
import com.point.bean.PointBean;
import com.point.bean.PointMemberBean;
import com.point.bean.PointSetBean;
import com.point.dao.PointDAO;
import com.point.dao.PointSetDAO;
import com.point.dto.PointMemberDTO;
import com.util.DateUtils;

public class PointService {
	private PointDAO pointDao;
	private PointSetService pointSetService;

	public PointService(Session session, PointSetService pointSetService) {
		this.pointDao = new PointDAO(session);
		this.pointSetService = pointSetService;
	}
	
	private static PointSetBean currentPointSet;
	
	
	// 新增點數紀錄
	public void insertOneRecord(int memberID, int pointChange, String createDateString, int transactionID,
			String transactionType) {
		Date createDate = DateUtils.GetDateFromString(createDateString);// creatDate轉型

		currentPointSet = pointSetService.getPointSet();// 取得點數設定
		String isExpiry = currentPointSet.getIsExpiry();// 判斷有無到期
		switch (isExpiry) {
		case ("isExpiry"):
			int yearInt = Integer.parseInt(createDateString.substring(0, 4)) + 1;// 到期日為隔年
			String year = String.valueOf(yearInt);
			String month = currentPointSet.getExpiryMonth();// 取得點數設定月
			String day = currentPointSet.getExpiryDay();// 取得點數設定日
			Date expiryDate = DateUtils.GetDateFromString(year + "-" + month + "-" + day);// yyyy-mm-dd
			PointBean pointBeanExpiry = new PointBean(memberID, pointChange, createDate, expiryDate, transactionID, transactionType);
//			PointDAO.InsertOneRecord(memberID, pointChange, createDate, expiryDate, transactionID, transactionType);
			pointDao.insertOneRecord(pointBeanExpiry);
			break;
		case ("noExpiry"):
			Date noExpiryDate = DateUtils.GetDateFromString("9999-12-31");// 無到期日時間無限大
			PointBean pointBeanNoExpiry = new PointBean(memberID, pointChange, createDate, noExpiryDate, transactionID, transactionType);
//			PointDAO.InsertOneRecord(memberID, pointChange, createDate, noExpiryDate, transactionID, transactionType);
			pointDao.insertOneRecord(pointBeanNoExpiry);
			break;
		}
	}

	// 批次新增點數紀錄
	public void insertBatchRecord(List<String> memberIDs, int pointChange, String createDateString, int transactionID,
			String transactionType) {
		System.out.println("SERVER TOUCH");
		for (String memberID : memberIDs) {
			int IntMemberID = Integer.parseInt(memberID);
			insertOneRecord(IntMemberID, pointChange, createDateString, transactionID, transactionType);
		}
		System.out.println("SERVER DONE");
	}

	// 搜尋點數
	public static List<PointMemberBean> search(String keyWord) {

//		String cleanKeyWord = keyWord.startsWith("-") ? keyWord.substring(1) : keyWord;
//		Boolean isName = keyWord.matches("^[A-Za-z\u4e00-\u9fa5\\s\\p{P}]+$");
//		Boolean isNumber = keyWord.matches("\\d+");
//		if (keyWord.startsWith("-")) {
//			return PointDAO.getPointMemberByID(cleanKeyWord);
//		} else if (isName) {
//			return PointDAO.getPointMemberByName(cleanKeyWord);
//		} else if (isNumber) {
//			return PointDAO.getPointMemberByNumber(cleanKeyWord);
//		}
		
		return null;
	}

	//OK// 打印訊息(批次新增)
	public String printMessage(List<String> memberIDs) {
		StringBuilder message = new StringBuilder();
		message.append("點數將贈送給").append(memberIDs.size()).append("位會員。").append(System.lineSeparator());// 換行
		message.append("會員編號為﹔");
		for (int i = 0; i < memberIDs.size(); i++) {
			message.append(memberIDs.get(i));
			if (i != memberIDs.size() - 1) {
				message.append("、");
			} else {
				message.append("。");
			}
		}
		return message.toString();
	}

	// 查詢(會員、點數餘額、電話) all
	public List<PointMemberDTO> getAllPointMember() {
		List<Object[]> results = pointDao.getAllPointMember();
		List<PointMemberDTO> PointMemberDTOs = new ArrayList<>();

		for (Object[] result :results) {
			PointMemberDTO dto = new PointMemberDTO();
			dto.setMemberId(Integer.parseInt(result[0].toString()));
			dto.setMemberName((String) result[1]);
			dto.setPhone((String) result[2]);
			dto.setTotalPointBalance((int) result[3]); // Assuming total_point_balance is of type Long
			dto.setExpiringPoints((int) result[4]); // Assuming expiring_points is of type Long
			dto.setExpiryDate((Date) result[5]); // Assuming get_expiry_date is of type Date

			PointMemberDTOs.add(dto);
		}
		
		return PointMemberDTOs;
	}
	
	//查詢(會員、點數餘額、電話) bymemberID (return bean)
	public PointMemberDTO  getPointMember(int memberId) {
		Object[] result = pointDao.getPointMember(memberId);

    	PointMemberDTO dto = new PointMemberDTO();
        dto.setMemberId(Integer.parseInt(result[0].toString()));
        dto.setMemberName((String) result[1]);
        dto.setPhone((String) result[2]);
        dto.setTotalPointBalance((int) result[3]); 
        dto.setExpiringPoints((int) result[4]);    
        dto.setExpiryDate((Date) result[5]);       
        return dto;
	}
	
	//查詢所有紀錄BY memberID
	public List<PointBean> getAllRecord(int memberID){
		 return pointDao.getAllRecord(memberID);
	}
	
	//查詢所有紀錄 
	public PointBean getOneRecord(int pointID) {
		return pointDao.getOneRecord(pointID); 
		
	}
	
	//修改
	public void updatePoint(int pointChange,Date createDate,Date expiryDate,int pointUsage,int transactionID,String transactionType,int pointID) {
		pointDao.updatePoint(pointChange, createDate, expiryDate, pointUsage, transactionID, transactionType, pointID);
	}
	
	//刪除
	public void deleteOneRecord(int pointID) {
		pointDao.deleteOneRecord(pointID);
	}
	
	//search
	public List<PointMemberDTO> searchPointMember(String keyWord){
		List<Object[]> results = pointDao.searchPointMember(keyWord);
		List<PointMemberDTO> PointMemberDTOs = new ArrayList<>();

		for (Object[] result :results) {
			PointMemberDTO dto = new PointMemberDTO();
			dto.setMemberId(Integer.parseInt(result[0].toString()));
			dto.setMemberName((String) result[1]);
			dto.setPhone((String) result[2]);
			dto.setTotalPointBalance((int) result[3]); 
			dto.setExpiringPoints((int) result[4]); 
			dto.setExpiryDate((Date) result[5]); 

			PointMemberDTOs.add(dto);
		}
		
		return PointMemberDTOs;
	}
}
