package com.wujie.common.utils.pdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class PdfSignature {

	private static final Logger logger = LoggerFactory.getLogger(PdfSignature.class);
	
	//DATOS DEL TSA DE PRUEBA
	private final String  TSA_URL ="http://ca.signfiles.com/TSAServer.aspx";
	private final String  TSA_USUARIO ="";
	private final String  TSA_PASSWORD ="";
	
	//KEYSTORE JKS
	private final String JKS_PASSWORD = "123456";
	//F:\\test_icessl_key\\keystore.jks
	private final String JKS_PATH = "keystore.jks";
	
	public PdfSignature(){
		
	}

	public void sign(String origen, String destino) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			String pass = JKS_PASSWORD; //PASSWORD DE LA KEYSTORE
			
			//读取KeyStore文件
			InputStream file = ClassUtils.class.getClassLoader().getResourceAsStream(JKS_PATH); //EL ARCHIVO QUE GENERAMOS TIENE QUE ESTAR EN EL DISCO C
			//InputStream file = this.getClass().getResourceAsStream(JKS_PATH);
			//java.io.FileInputStream file = new java.io.FileInputStream(JKS_PATH);
			
			//从指定的输入流中加载此 KeyStore
			keyStore.load(file, pass.toCharArray());
			PrivateKey pk = (PrivateKey) keyStore.getKey("mtorre4580", pass.toCharArray());//USUARIO Y PASSWORD DE LA KEYSTORE
			Certificate[] chain = keyStore.getCertificateChain("mtorre4580"); //OBTENGO EL CERTIFICADO
			
			PdfReader reader = new PdfReader(origen);
			FileOutputStream os = new FileOutputStream(destino);
			
			//创建签章工具PdfStamper ，最后一个boolean参数是否允许被追加签名
			PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');			
			// 获取数字签章属性对象
			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			appearance.setReason("Qingchuanyi Order Signature");
			appearance.setLocation("CHINA");
			//设置签名的签名域名称，图片大小受表单域大小影响（过小导致压缩）
			appearance.setVisibleSignature(new Rectangle(20, 0, 300, 150), 1, "signature");
			TSAClientBouncyCastle tsc = new TSAClientBouncyCastle(TSA_URL, TSA_USUARIO, TSA_PASSWORD); //SE AGREGA TSA
			// 摘要算法
			ExternalDigest digest = new BouncyCastleDigest();
			// 签名算法
			ExternalSignature signature = new PrivateKeySignature(pk, "SHA1", "BC");
			// 调用itext签名方法完成pdf签章
			MakeSignature.signDetached(appearance, digest, signature, chain, null, null, tsc, 0, CryptoStandard.CMS);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al firmar el pdf");
		}
	}
}
