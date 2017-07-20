package com.eps.system.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

public class DocumentLoader extends SystemLoader {

	@Override
	public String load() throws IOException {
		
//		String docPath = this.getClass().getResource("").toString().substring(6).split("classes")[0] + "config/documentTemplate.xml";
		String docPath = this.getClass().getResource("").toString().substring(5).split("classes")[0] + "config/documentTemplate.xml";

		File file = new File(docPath);
		
		SAXBuilder builder = new SAXBuilder();
		InputStream in = new FileInputStream(file);
		Document document;
		Cache c = new Cache();
		Map<String,Map<String,String>> property = new HashMap<String, Map<String,String>>();
		try {
			document = builder.build(in);
		
			Element root = document.getRootElement();
			List<Element> list = root.getChildren();
			for(Element e:list) {
				List<Element> propertyList = e.getChildren("Property");
				Map<String,String> map = new HashMap<String, String>();
				for(Element element : propertyList){
					map.put(element.getAttributeValue("name"), element.getAttributeValue("value"));
				}
				property.put(e.getAttributeValue("templateno"), map);
				c.setValue(property);
			}
			c.setExpired(false);
			CacheManager.putCache("documentCache", c);
		} catch (JDOMException e1) {
			e1.printStackTrace();
			return "false";
		}
		
		return "true";
	}
}
