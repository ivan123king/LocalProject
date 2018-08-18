package com.lw.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class DianMing {

	private static final ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
	private static final Dispatch sapo = sap.getObject();
	private static Variant defalutVoice = sap.getProperty("Voice");
	
	private static void setDefaultPlaySetting(){
		// 音量 0-100
        sap.setProperty("Volume", new Variant(100));
        // 语音朗读速度 -10 到 +10
        sap.setProperty("Rate", new Variant(-2));
        Dispatch dispdefaultVoice = defalutVoice.toDispatch();
        Variant allVoices = Dispatch.call(sapo, "GetVoices");
        Dispatch dispVoices = allVoices.toDispatch();

        Dispatch setvoice = Dispatch.call(dispVoices, "Item", new Variant(1)).toDispatch();
        ActiveXComponent voiceActivex = new ActiveXComponent(dispdefaultVoice);
        ActiveXComponent setvoiceActivex = new ActiveXComponent(setvoice);

        Variant item = Dispatch.call(setvoiceActivex, "GetDescription");
	}
	
	private static boolean callName(int timeout,String...names){
		for(String name:names){
			// 执行朗读
			try {
				Dispatch.call(sapo, "Speak", new Variant(name));
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean play(int timeout,String...names){
	    try {
	    	setDefaultPlaySetting();
	    	return callName(timeout,names);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        sapo.safeRelease();
	        sap.safeRelease();
	    }
	    return false;
	}
	
	/**
	 * 从文件读取姓名
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFromFile(String path) throws IOException{
		List<String> namesList = new ArrayList<String>();
		if(path!=null&&!"".equals(path)){
			File file = new File(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
			String name = null;
			name = br.readLine();
			while(name!=null){
//				name = new String(name.getBytes("iso-8859-1"),"UTF-8");
				namesList.add(name);
				name = br.readLine();
			}
		}
		return namesList;
	}
}
