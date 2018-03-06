package com.lw.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * 进行加密的安全Socket
 * @author lenovo
 *
 */
public class SecureOrderTaker {
	
	public final static int PORT = 7000;
	public final static String algorithm = "SSL";
	
	public static void main(String[] args) {
		try{
			SSLContext context = SSLContext.getInstance(algorithm);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			
			char[] password = System.console().readPassword();
			ks.load(new FileInputStream("XXX.keys"),password);
			kmf.init(ks,password);
			context.init(kmf.getKeyManagers(), null, null);
			
			//擦出口令
			Arrays.fill(password, '0');
			
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(PORT);
			
			//增加匿名（未认证）密码组
			String[] supported = server.getSupportedCipherSuites();
			String[] anonCipherSuitesSupported = new String[supported.length];
			int numAnonCipherSuitesSupported = 0;
			for(int i=0;i<supported.length;i++){
				if(supported[i].indexOf("_anon_")>0){
					anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
				}
			}
			String[] oldEnabled = server.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length+numAnonCipherSuitesSupported];
			System.arraycopy(oldEnabled, 0, newEnabled, 0, newEnabled.length);
			System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);
			
			server.setEnabledCipherSuites(newEnabled);
			
			//所有设置完成，可以通信,此时Socket是安全的
			while(true){
				try(Socket connection = server.accept()){
					InputStream is = connection.getInputStream();
					int c;
					while((c=is.read())!=-1){
						System.out.write(c);
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}catch(IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex){
			ex.printStackTrace();
		}
	}
}
