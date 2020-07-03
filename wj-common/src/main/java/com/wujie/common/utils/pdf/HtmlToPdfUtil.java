package com.wujie.common.utils.pdf;

import com.itextpdf.text.pdf.BaseFont;
import com.wujie.common.utils.OSUtil;
import com.wujie.common.utils.file.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.util.Map;

public class HtmlToPdfUtil {

	/**
	 * 通过生成静态Html页面导出pdf
	 * @param filePath 用于生成html的模板位置
	 * @param template 模板名称
	 * @param variables modelMap
	 * @param outputFile 输出路径
	 * @return
	 */
	public static File exportPdfFile(String filePath, String template, Map<String, Object> variables, String outputFile) {
		try {
			if (!FileUtils.existDirectory(outputFile))
				FileUtils.makeDirectory(outputFile);

			File retFile = new File(outputFile);
			// 运单一旦被修改或者被处理,其createTime一定被修改,outputFile名称也会改变,就一定不存在
			if (retFile.exists() && retFile.length() > 0) // 一定是未被修改或者被处理,且已经存在
				return retFile;// 直接返回即可

			OutputStream os = new FileOutputStream(retFile);
			
			//生成静态html页面
			String htmlStr = HtmlGenerator.generate(filePath, template, variables);  
	          
	        DocumentBuilder builder =  DocumentBuilderFactory.newInstance().newDocumentBuilder();   
	        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, null);
			
			//添加字体，用于中文显示
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if(OSUtil.isWindows()){
				// for win7
				fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont("/usr/share/fonts/chinese/TrueType/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			
			renderer.layout();
			renderer.createPDF(os);

			System.out.println("转换成功！");
			os.flush();
			os.close();
			return retFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过生成静态Html页面导出带签名的pdf
	 * @param filePath 用于生成html的模板位置
	 * @param template 模板名称
	 * @param variables modelMap
	 * @param outputFile 输出路径
	 * @return
	 */
	public static File exportSignPdfFile(String filePath, String template, Map<String, Object> variables, String outputFile) throws Exception {
		
		if (!FileUtils.existDirectory(outputFile))
			FileUtils.makeDirectory(outputFile);

		String signOutputFile = outputFile.replaceAll("\\.pdf", "_SIGN\\.pdf");
		File retSignFile = new File(signOutputFile);
		// 运单一旦被修改或者被处理,其createTime一定被修改,outputFile名称也会改变,就一定不存在
		if (retSignFile.exists() && retSignFile.length() > 0) // 一定是未被修改或者被处理,且已经存在
			return retSignFile;// 直接返回即可


		File retFile = new File(outputFile);
		if (!retFile.exists()) {
			OutputStream os = new FileOutputStream(retFile);
			
			//生成静态html页面
			String htmlStr = HtmlGenerator.generate(filePath, template, variables);  
	          
	        DocumentBuilder builder =  DocumentBuilderFactory.newInstance().newDocumentBuilder();   
	        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, null);
			
			//添加字体，用于中文显示
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if(OSUtil.isWindows()){
				// for win7
				fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont("/usr/share/fonts/chinese/TrueType/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			
			renderer.layout();
			renderer.createPDF(os);
			os.flush();
			os.close();
		}
		
		/** 生成带签名的PDF */
		Security.addProvider(new BouncyCastleProvider());
		PdfSignature pdfSign = new PdfSignature();
		pdfSign.sign(outputFile, outputFile.replaceAll("\\.pdf", "_SIGN\\.pdf"));
		System.out.println("pdf签名成功！");
		
		return retSignFile;
	
	}

	/**
	 * 通过请求URL获取html页面导出pdf
	 * @param urlStr 请求URL
	 * @param outputFile 输出路径
	 * @return
	 */
	public static File exportPdfFile(String urlStr, String outputFile) {
		try {
			if (!FileUtils.existDirectory(outputFile))
				FileUtils.makeDirectory(outputFile);

			File retFile = new File(outputFile);
			// 运单一旦被修改或者被处理,其createTime一定被修改,outputFile名称也会改变,就一定不存在
			if (retFile.exists() && retFile.length() > 0) // 一定是未被修改或者被处理,且已经存在
				return retFile;// 直接返回即可

			OutputStream os = new FileOutputStream(retFile);

			// String str = getHtmlFile(urlStr);
			// System.out.println(str);

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(urlStr);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if(OSUtil.isWindows()){
				// for win7
				fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont("/usr/share/fonts/chinese/TrueType/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.layout();

			renderer.createPDF(os);

			System.out.println("转换成功！");
			os.flush();
			os.close();
			return retFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取页面内容
	public static String getHtmlFile(String urlStr) {
		try {
			if (urlStr.indexOf("?") != -1) {
				urlStr = urlStr + "&locale=" + LocaleContextHolder.getLocale().toString();
			} else {
				urlStr = urlStr + "?locale=" + LocaleContextHolder.getLocale().toString();
			}

			URL url = new URL(urlStr);

			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();

			Tidy tidy = new Tidy();

			OutputStream os2 = new ByteArrayOutputStream();
			tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
			// tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
			tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
			tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
			tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
			tidy.parse(is, os2);

			is.close();

			// 解决乱码 --将转换后的输出流重新读取改变编码
			String temp;
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
					((ByteArrayOutputStream) os2).toByteArray()), "utf-8"));
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String urlStr = "http://www.eyd98.com/mobile/ship/show?id=70";
		String outputFile = "e:/ship70.pdf";

		HtmlToPdfUtil.exportPdfFile(urlStr, outputFile);
	}

}