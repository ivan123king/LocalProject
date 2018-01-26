package com.lw.decodeAencode.classcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 算法大全
 * @author lenovo
 *
 */
public class AlgorithmCase {

	/**
	 * idea算法
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public void ideaCase() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		/*
		 *1.生成密钥 
		 */
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator kg = KeyGenerator.getInstance("IDEA");
		kg.init(128);
		SecretKey key = kg.generateKey();
		byte[] keyB = key.getEncoded();
		
		/*
		 * 2.加密
		 */
		Security.addProvider(new BouncyCastleProvider());
		Key k = new SecretKeySpec(keyB,"IDEA");
		//IDEA 算法  ，ECB 工作模式， ISO10126Padding 填充模式
		Cipher cipher = Cipher.getInstance("IDEA/ECB/ISO10126Padding");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		byte[] enData = cipher.doFinal("待加密数据".getBytes());
		
		/*
		 * 3.解密
		*/
		Security.addProvider(new BouncyCastleProvider());
		Key dk = new SecretKeySpec(keyB,"IDEA");
		Cipher dCipher = Cipher.getInstance("IDEA/ECB/ISO10126Padding");
		cipher.init(Cipher.DECRYPT_MODE, dk);
		byte[] deData = dCipher.doFinal(enData);
	}
	
	/**
	 * pbe算法：基于口令方式，而非密钥
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public void pbeCase() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		/*
		 * 1.生成盐
		 */
		SecureRandom random = new SecureRandom();
		byte[] salt = random.generateSeed(8);
		
		/*
		 * 2.加密
		 */
		String password = "口令";
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PEBWITHMD5andDES");
		SecretKey secretKey = factory.generateSecret(keySpec);
		
		//100 是迭代次数
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt,100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
		byte[] enData = cipher.doFinal("加密数据".getBytes());
		
		/*
		 * 3. 解密
		 */
		cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
		byte[] deData = cipher.doFinal(enData);
	}
	
	/**
	 * DH非对称加密
	 * 甲乙双方构建的私密密钥相同，DH算法使用同一个私密密钥完成加密，解密
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws IllegalStateException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * 
	 * 如果运行报错：java.security.InvalidKeyException: Illegal key size or default parameters
	 * Sun Java Cryptography Extension(JCE) Unlimited Strenght Jurisdiction Policy File 下载地址
		http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
	 * 上面下载的里面的两个jar包：local_policy.jar 和 US_export_policy.jar 替换掉原来安装目录C:\Program Files\Java\jdk1.8.0_111\jre\lib\security 下的两个jar包接可以了然后就重新运行程序，不会报错了，测试代码如下：
	 * 
	 * 原因：因为美国的出口限制，Sun通过权限文件（local_policy.jar、US_export_policy.jar）做了相应限制。
	 * 所以需要到官网上下载没有限制的local_policy.jar和US_export_policy.jar
	 * 
	 * DH算法说明：
	 * 	1. 甲方生成公钥私钥，将公钥发送给乙方
	 *  2. 乙方根据甲方的公钥生成公钥私钥，将公钥发送给甲方
	 *  3. 甲方根据乙方的公钥和甲方的私钥生成私密密钥
	 *  4. 乙方根据甲方的公钥和乙方的私钥生成私密密钥
	 *  5. 甲方用自己的私密密钥加密
	 *  6. 乙方用自己的私密密钥解密
	 *  
	 *  原因： 甲方私密密钥==乙方私密密钥，所以才能做到解密
	 * 
	 */
	public void dhCase() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalStateException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		/*
		 * 1.构建甲方公钥，私钥
		 */
		KeyPairGenerator fKeyPairGenerator = KeyPairGenerator.getInstance("DH");
		fKeyPairGenerator.initialize(512);//密钥长度，必须是64的倍数   512~2048
		KeyPair fKeyPair = fKeyPairGenerator.generateKeyPair();
		//甲方公钥
		DHPublicKey fpublicKey = (DHPublicKey) fKeyPair.getPublic();
		//甲方私钥
		DHPrivateKey fprivateKey = (DHPrivateKey) fKeyPair.getPrivate();
		
		
		/*
		 * 2.构建乙方公钥，私钥
		 */
		//解析甲方公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(fpublicKey.getEncoded());
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		//产生公钥，应该是和  fpublicKey一样的，只不过传输使用byte传递甲方公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		DHParameterSpec dhParamSpec = ((DHPublicKey)pubKey).getParams();
		//实例化密钥对生成器
		KeyPairGenerator sKeyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
		sKeyPairGenerator.initialize(dhParamSpec);
		KeyPair sKeyPair = sKeyPairGenerator.generateKeyPair();
		//乙方公钥
		DHPublicKey spublicKey = (DHPublicKey) sKeyPair.getPublic();
		//乙方私钥
		DHPrivateKey sprivateKey = (DHPrivateKey) sKeyPair.getPrivate();
		
		/*
		 * 3.生成甲方本地密钥，使用甲方私钥，乙方公钥构建
		 */
		//解析乙方公钥
		keyFactory = KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(spublicKey.getEncoded());
		pubKey = keyFactory.generatePublic(x509KeySpec);
		//解析甲方私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(fprivateKey.getEncoded());
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		//生成本地密钥
		SecretKey fSecretKey = keyAgree.generateSecret("AES");
		
		/*
		 * 4.生成乙方本地密钥，使用甲方公钥，乙方私钥
		 */
		//解析甲方公钥
		keyFactory = KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(fpublicKey.getEncoded());
		pubKey = keyFactory.generatePublic(x509KeySpec);
		//解析乙方私钥
		pkcs8KeySpec = new PKCS8EncodedKeySpec(sprivateKey.getEncoded());
		priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		//生成本地密钥
		SecretKey sSecretKey = keyAgree.generateSecret("AES");
		
		//fSecretKey和sSecretKey其实是一样的
		System.out.println(Base64.encodeBase64String(fSecretKey.getEncoded()));
		System.out.println(Base64.encodeBase64String(sSecretKey.getEncoded()));
		
		/*
		 * 5.甲方加密发送数据   使用甲方私密密钥加密
		 * qteiCN0nJdLe0IFymk4bSw==
		 * 3nz7OhvgNKcfgRb3uwJD+72Tc1i6gxEU7J4pUZWOf9s=
		 */
		SecretKey secretKey = new SecretKeySpec(fSecretKey.getEncoded(),"AES");
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] fdata = cipher.doFinal("待加密数据".getBytes());
		
		/*
		 * 6.乙方解密甲方发送到的加密数据   使用乙方私密密钥解密
		 */
		secretKey = new SecretKeySpec(sSecretKey.getEncoded(),"AES");
		cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] sdata = cipher.doFinal(fdata);
		System.out.println(new String(sdata));
	}
	
	/**
	 * 数字签名:私钥签名，公钥验证签名
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 */
	public void sign() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
		/*
		 * 1.生成公钥私钥密钥对
		 */
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(512);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		/*
		 * 2.甲方私钥签名
		 */
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		//下面就是用于签名的类
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(priKey);
		signature.update("用作签名的数据".getBytes());
		byte[] sign = signature.sign();//sign就是用来传递的签名
		
		/*
		 * 3. 乙方使用公钥验证签名
		 */
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
		keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(pubKey);
		signature.update("用作签名的数据".getBytes());
		boolean status = signature.verify(sign);
	}
	
	/**
	 * 数字证书加密解密
	 * 1.加载数字证书
	 * 2.获取数字证书中的公钥和私钥
	 * 3.使用密钥对加解密，或者签名
	 * 
	 * 生成密钥对  zlex.keystore文件
	 * keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg
SHA1withRSA -validity 365 -alias www.zlex.org -keystore zlex.keystore -dname "CN
=wwww.zlex.org,OU=zlex,O=zlex,L=BJ,ST=Bj,C=CN"

		导出数字证书：zlex.cer文件
		keytool -exportcert -alias www.zlex.org -keystore zlex
.keystore -file zlex.cer -rfc

	具体看有道云笔记：java基础/数字证书生成
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public void certificate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		String keyStorePath = "D:\\tools\\java生成数字证书\\zlex.keystore";
		String certificatePath = "D:\\tools\\java生成数字证书\\zlex.cer";
		String password = "123456";
		String alias = "www.zlex.org";
		
		/*
		 * 1.加载密钥库文件 zlex.keystore
		 */
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream is = new FileInputStream(keyStorePath);
		ks.load(is,password.toCharArray());
		is.close();
		
		/*
		 * 2.获取密钥库文件中公钥私钥
		 */
		//获取私钥需要密码
		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
		
		//获取公钥需要有证书中获得
		/*
		 * 3. 加载证书文件
		 * 当然也可以从keystore文件中获取证书    ks.getCertificate(alias);
		 */
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		is = new FileInputStream(certificatePath);
		Certificate certificate = certificateFactory.generateCertificate(is);
		is.close();
		
		/*
		 * 4.获取公钥
		 */
		PublicKey publicKey = certificate.getPublicKey();
		
		/**
		 * 有了公钥私钥，就完全可以仿照上面的方法做签名和加解密
		 */
		
	}
	
	public static void main(String[] args) {
		AlgorithmCase c = new AlgorithmCase();
		try {
			c.dhCase();
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| InvalidKeySpecException | InvalidAlgorithmParameterException
				| IllegalStateException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
