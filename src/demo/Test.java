package demo;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
 

public class Test {
	public static void main(String[] args) throws Exception {
		
		String fontPath = Test.class.getResource("/").getPath()+ "fonts/";
		
		InputStream is =Test.class.getClassLoader().getResourceAsStream("1.html");  
		String htmlDocument = PdfUtil.readFile(is,"utf-8");
		
		Map<String,String> param = new HashMap<>();
		param.put("name","zhaojie");
		param.put("age","23");
		htmlDocument =PdfUtil.replace(htmlDocument, param);
		
		PdfUtil util = new PdfUtil(15, 15, 15, 15, 650);
		//ÁÙÊ±Â·¾¶	
		String tempPath = "d://pdf/";
		File tempDir = new File(tempPath);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		String filePath = tempPath+UUID.randomUUID().toString()+".pdf";
		util.createPDFFile(htmlDocument, filePath,fontPath);
	}
}