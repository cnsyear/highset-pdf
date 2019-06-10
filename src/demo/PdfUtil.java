package demo;

import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

/**
 * 本地文件转pdf
 * 
 * @author guokaige
 *
 */
public class PdfUtil {
	//protected final static Logger logger = LoggerFactory.getLogger(PdfUtil.class);
	private int topValue;
	private int leftValue;
	private int rightValue;
	private int bottomValue;
	private int userSpaceWidth;

	public PdfUtil(int topValue, int leftValue, int rightValue, int bottomValue, int userSpaceWidth) {
		super();
		this.topValue = topValue;
		this.leftValue = leftValue;
		this.rightValue = rightValue;
		this.bottomValue = bottomValue;
		this.userSpaceWidth = userSpaceWidth;
	}
	

	/**
	 * 创建pdf文件
	 * 
	 * @author Jiabin.Zhao
	 * @date 创建时间：2017年2月22日 上午11:55:29
	 * @version 1.0
	 * @parameter
	 * @since
	 * @return
	 * @param htmlDocument
	 *            html代码文档
	 * @param outputPath
	 *            pdf文件输出路径
	 * @param fontPath
	 *            字体路径
	 * @throws InvalidParameterException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void createPDFFile(String htmlDocument, String outputPath, String fontsPath)
			throws InvalidParameterException, MalformedURLException, IOException {
		PD4ML pd4ml = new PD4ML();
		pd4ml.setHtmlWidth(userSpaceWidth); // set frame width of
		pd4ml.setPageSize(PD4Constants.A4);
		pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
		pd4ml.addStyle("BODY {margin: 0}", true);
		pd4ml.useTTF(fontsPath, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		pd4ml.render(new StringReader(htmlDocument), baos);
		baos.close();
		// logger.info("resulting PDF size: " + baos.size() + " bytes");
		File output = new File(outputPath);
		// 创建父级目录
		if (!output.getParentFile().exists()) {
			output.getParentFile().mkdirs();
		}
		java.io.FileOutputStream fos = new java.io.FileOutputStream(output);
		fos.write(baos.toByteArray());
		fos.close();
		// logger.info(outputPath + "\ndone.");
		System.out.println(outputPath + "\ndone.");
	}

	/**
	 * 转码
	 * 
	 * @Title: title
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @version V2.0
	 * @author guokaige
	 * @Date 2016-12-7 下午2:54:30
	 */
	public final static String readFile(String path, String encoding) throws IOException {

		File f = new File(path);
		FileInputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (Exception e) {

			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		byte buffer[] = new byte[2048];

		int read;
		do {
			read = is.read(buffer, 0, buffer.length);
			if (read > 0) {
				fos.write(buffer, 0, read);
			}
		} while (read > -1);

		fos.close();
		bis.close();
		is.close();

		return fos.toString(encoding);
	}

	/**
	 * 转码
	 * 
	 * @Title: title
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @version V2.0
	 * @author guokaige
	 * @Date 2016-12-7 下午2:54:30
	 */
	public final static String readFile(InputStream is, String encoding) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		byte buffer[] = new byte[2048];

		int read;
		do {
			read = is.read(buffer, 0, buffer.length);
			if (read > 0) {
				fos.write(buffer, 0, read);
			}
		} while (read > -1);

		fos.close();
		bis.close();
		is.close();

		return fos.toString(encoding);
	}

	/**
	 * 替换占位符
	 * 
	 * @author Jiabin.Zhao
	 * @date 创建时间：2017年2月24日 下午8:44:49
	 * @version 1.0
	 * @parameter
	 * @since
	 * @return
	 * @param content
	 * @param map
	 * @return
	 */
	public static String replace(String content, Map<String, String> map) {
		if (map == null || map.values().size() == 0)
			return content;
		Collection<String> cl = map.keySet();
		for (String key : cl) {
			String temp = "${" + key + "}";
			if (map.get(key) != null) {
				content = content.replace(temp, map.get(key).toString());
			} else {
				content = content.replace(temp, " &nbsp; ");
			}
		}
		return content;
	}

}