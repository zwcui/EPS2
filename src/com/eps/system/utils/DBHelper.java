package com.eps.system.utils;

import java.io.IOException;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.TableMaxsn;
import com.eps.dao.service.SystemService;

public class DBHelper {

	public static DBHelper dbHelper;
	
	public static DBHelper getInstance(){
		if(dbHelper == null){
			dbHelper = new DBHelper();
		}
		return dbHelper;
	}
	
	public synchronized static String generateKey(String tableName) throws IOException{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");
		
		tableName = tableName.toLowerCase();
		
		TableMaxsn maxBean = systemService.getMaxNumFromTable(tableName);
		
		String maxSerialNo = "";
		String sysDate = DateHelper.getCurrentDate();
		sysDate = sysDate.replace("/", "");
		
		if(maxBean == null){
			maxBean = new TableMaxsn();
			int number = systemService.getMaxNum(tableName);
			number++;
			maxSerialNo = getSerialNo(sysDate, String.valueOf(number), 16);
			maxBean.setCurrentDate(sysDate);
			maxBean.setTotalCount(String.valueOf(number));
			maxBean.setMaxSerialNo(String.valueOf(maxSerialNo));
			maxBean.setTableName(tableName);
			systemService.insertMaxNum(maxBean);
		}else{
			String currentDate = maxBean.getCurrentDate(); 
			if(!currentDate.equals(sysDate)){
				maxSerialNo = getSerialNo(sysDate, "1", 16);
				maxBean.setCurrentDate(sysDate);
				maxBean.setTotalCount("1");
				maxBean.setMaxSerialNo(String.valueOf(maxSerialNo));
				maxBean.setTableName(tableName);
				systemService.updateMaxNumFromTable(maxBean);
			}else{
				int totalCount = Integer.parseInt(maxBean.getTotalCount());
				totalCount++;
				maxSerialNo = getSerialNo(sysDate, totalCount+"", 16);
				maxBean.setCurrentDate(sysDate);
				maxBean.setTotalCount(String.valueOf(totalCount));
				maxBean.setMaxSerialNo(String.valueOf(maxSerialNo));
				maxBean.setTableName(tableName);
				systemService.updateMaxNumFromTable(maxBean);
			}
		}
		
		return maxSerialNo;
		
	}
	
	private static String getSerialNo(String currentDate,String totalCount,int length){
		
		while(totalCount.length() < length-currentDate.length()){
			totalCount = "0" + totalCount;
		}
		
		return currentDate + totalCount;
	}
	
//	public synchronized static String generateKey(String tableName) throws IOException{
//		String serialNo = "";
//		
//		SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSessionFactory();
//		SqlSession session = sqlSessionFactory.openSession();
//		String statement = "com.eps.mappers.SystemMapper.getMaxNum";//Ó³ÉäsqlµÄ±êÊ¶×Ö·û´®
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("tableName", tableName);
//	    int number = Integer.parseInt(String.valueOf(session.selectOne(statement, map))) + 1;
//	    String num = number + "";
//	    session.close();
//	    while(num.length() < count-8){
//	    	num = "0" + num;
//	    }
//	    
//	    Date date = new Date();
//	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//	    String dateStr = format.format(date);
//	    
//	    serialNo = dateStr + num;
//		
//		return serialNo;
//	}
	
}
