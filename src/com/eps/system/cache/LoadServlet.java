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
				System.out.println("����global�ɹ�");
			}else{
				System.out.println("����globalʧ��");
			}
			
			String userLoadStatus = userLoader.load();
			if("true".equals(userLoadStatus)){
				System.out.println("����user�ɹ�");
			}else{
				System.out.println("����userʧ��");
			}
			
			String orgLoadStatus = orgLoader.load();
			if("true".equals(orgLoadStatus)){
				System.out.println("����org�ɹ�");
			}else{
				System.out.println("����orgʧ��");
			}
			
			String codeLoadStatus = codeLoader.load();
			if("true".equals(codeLoadStatus)){
				System.out.println("����code�ɹ�");
			}else{
				System.out.println("����codeʧ��");
			}
			
			String documentLoadStatus = documentLoader.load();
			if("true".equals(documentLoadStatus)){
				System.out.println("����document�ɹ�");
			}else{
				System.out.println("����documentʧ��");
			}
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}	
		
	}

}
