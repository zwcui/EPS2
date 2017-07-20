package com.eps.system.cache;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.jdom.JDOMException;

import com.eps.system.load.CodeLoader;
import com.eps.system.load.DocumentLoader;
import com.eps.system.load.OrgLoader;
import com.eps.system.load.UserLoader;

public class LoadServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {

		XMLReader reader = new XMLReader();
		UserLoader userLoader = new UserLoader();
		OrgLoader orgLoader = new OrgLoader();
		CodeLoader codeLoader = new CodeLoader();
		DocumentLoader documentLoader = new DocumentLoader();
		try {
			String globalLoadStatus = reader.readGlobalXML();
			if("true".equals(globalLoadStatus)){
				System.out.println("载入global成功");
			}else{
				System.out.println("载入global失败");
			}
			
			String userLoadStatus = userLoader.load();
			if("true".equals(userLoadStatus)){
				System.out.println("载入user成功");
			}else{
				System.out.println("载入user失败");
			}
			
			String orgLoadStatus = orgLoader.load();
			if("true".equals(orgLoadStatus)){
				System.out.println("载入org成功");
			}else{
				System.out.println("载入org失败");
			}
			
			String codeLoadStatus = codeLoader.load();
			if("true".equals(codeLoadStatus)){
				System.out.println("载入code成功");
			}else{
				System.out.println("载入code失败");
			}
			
			String documentLoadStatus = documentLoader.load();
			if("true".equals(documentLoadStatus)){
				System.out.println("载入document成功");
			}else{
				System.out.println("载入document失败");
			}
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}	
		
	}

}
