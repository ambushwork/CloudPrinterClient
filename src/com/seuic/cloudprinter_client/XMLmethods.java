package com.seuic.cloudprinter_client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.os.Environment;

public class XMLmethods {
	private Document menu;
	private static Element e_menu;
	public static final String relativePath_Upload = "/baidu/Upload_Menu.xml";
	public  Element initXML() {
        try {
    		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+relativePath_Upload);
    		if(file.exists())
    			file.delete();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.menu = builder.newDocument();
            
            e_menu = this.menu.createElement("menu");
    		this.menu.appendChild(e_menu);
    		return e_menu;
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
            return null;
        }
	}
	public  Element createXML(String s_name,String s_price,String s_count)
	{
		
		Element e_dish = this.menu.createElement("dish");
		//.menu.appendChild(e_dish);
		Element e_name = this.menu.createElement("name");
		e_name.appendChild(this.menu.createTextNode(s_name));
		e_dish.appendChild(e_name);
		Element e_price = this.menu.createElement("price");
		e_price.appendChild(this.menu.createTextNode(s_price));
		e_dish.appendChild(e_price);
		Element e_count = this.menu.createElement("count");
		e_count.appendChild(this.menu.createTextNode(s_count));
		e_dish.appendChild(e_count);
		//this.menu.appendChild(e_dish);
		return e_dish;
	}
	public  void makeXML(){
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(menu);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+relativePath_Upload));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
		
	}
	public void SaveDish(String name,String price,String count)
	{
		e_menu.appendChild(createXML(name,price,count));
		makeXML();	
	}
        
}
