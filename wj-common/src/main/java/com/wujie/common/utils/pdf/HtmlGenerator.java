package com.wujie.common.utils.pdf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HtmlGenerator {
	
	/**
	 * 使用Thymeleaf API渲染模板生成静态页面
	 * @param filePath
	 * @param template
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	public static String generate(String filePath, String template, Map<String, Object> variables) throws Exception {
		//构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(filePath);//模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");//模板文件后缀
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode("HTML");
        resolver.setCacheTTLMs(Long.valueOf(3600000L));
        resolver.setCacheable(false);
		
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //构造上下文(Model)
        Context context = new Context();
        context.setVariables(variables);

        //渲染模板
        //FileWriter write = new FileWriter("result.html");

		// 输出流
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
        templateEngine.process(template, context, writer);

		stringWriter.flush();
		stringWriter.close();
		writer.flush();
		writer.close();

		// 输出html
		String htmlStr = stringWriter.toString();
		return htmlStr;
	}

	public static void main(String args[]) throws Exception {
		String Str = generate("d:\\", "test", new HashMap<String, Object>());
		System.out.println(Str);
	}
}