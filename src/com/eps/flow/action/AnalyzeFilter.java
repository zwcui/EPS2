package com.eps.flow.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.FlowModel;
import com.eps.dao.FlowDAO;
import com.eps.dao.service.FlowService;

public class AnalyzeFilter {

	public String getNextPhaseNoFromFilter(WebApplicationContext webApplicationContext, String flowNo,String phaseNo,String phaseAction){
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("flowNo", flowNo);
		paramMap.put("phaseNo", phaseNo);
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		FlowModel flowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		String res = "";
		if(flowModel != null){
			
			String filter = flowModel.getFilter();
			String script = flowModel.getScript();
			
			//${}表示expression  #{}表示class
			//IF(${} = '','',IF(${} = '','',''))
			//IF('1'='1','A','B')
			//IF('1'='1','A',IF('1'='2','B','C'))
			if(filter != null && !"".equals(filter)){
				
				//TODO:目前只对phaseaction的表达式判断
				if(filter.contains("${phaseAction}")) filter = filter.replace("${phaseAction}", "'"+phaseAction+"'");
				res = AnalyzeIF(filter).split("@")[1];
				//TODO:添加对类返回值的映射
			}
		}
		return res;
	}
	
	public String AnalyzeIF(String ifExpression){
		String res = "";
		
		String frameStr = ifExpression.substring(3, ifExpression.length()-1);
		String param = frameStr.split(",")[0];
		String result1 = frameStr.split(",")[1];
		String result2 = frameStr.substring(frameStr.indexOf(",", frameStr.indexOf(result1))+1);
		String param1 = param.split("'")[1];
		String calc = param.split("'")[2];
		String param2 = param.split("'")[3];
		if("=".equals(calc)){
			if(param1.equals(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}else if(">=".equals(calc)){
			if(Double.parseDouble(param1) >= Double.parseDouble(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}else if("<=".equals(calc)){
			if(Double.parseDouble(param1) <= Double.parseDouble(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}else if(">".equals(calc)){
			if(Double.parseDouble(param1) > Double.parseDouble(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}else if("<".equals(calc)){
			if(Double.parseDouble(param1) < Double.parseDouble(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}else if("<>".equals(calc)){
			if(!param1.equals(param2)){
				if(!result1.contains("IF")){
					res = "true@"+result1;
				}else{
					res = AnalyzeIF(result1);
				}
			}else{
				if(!result2.contains("IF")){
					res = "true@"+result2;
				}else{
					res = AnalyzeIF(result2);
				}
			}
		}
		
		return res;
	}
	
	
	public static void main(String[] args){
		
		String filter = "IF('2'='1','A',IF('2'='2','B','C'))";
		new AnalyzeFilter().AnalyzeIF(filter);
		
	}
	
	
	
	
}
