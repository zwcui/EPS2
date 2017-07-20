package com.eps.system.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLReader {
	public String readGlobalXML() throws JDOMException, IOException{
		
//		String globalPath = this.getClass().getResource("").toString().substring(6).split("classes")[0] + "config/global.xml";
		String globalPath = this.getClass().getResource("").toString().substring(5).split("classes")[0] + "config/global.xml";

		System.out.println(globalPath);
		File file = new File(globalPath);
		
		SAXBuilder builder = new SAXBuilder();
		InputStream in = new FileInputStream(file);
		Document document = builder.build(in);
		Element root = document.getRootElement();
		List<Element> list = root.getChildren();
		for(Element e:list) {
			Cache c = new Cache();
			System.out.println(e.getName());
			System.out.println(e.getText());
			c.setKey(e.getName());
			c.setValue(e.getText());
			c.setExpired(false);
			CacheManager.putCache(e.getName(), c);
		}
		
		System.out.println("--------"+CacheManager.getAppHome());
		
		return "true";
	}
	
	public static void main(String[] args) throws JDOMException, IOException{
		new XMLReader().readGlobalXML();
		
		
	}
}
