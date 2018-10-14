package com.lw.house.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;





import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;





import com.google.common.collect.Lists;
import com.google.common.io.Files;

@Service
public class FileService {

	//@Value将application.properties中的file.path注入到此filePath变量中来
	@Value("${file.path}")
	private String filePath;
	
	/**
	 * 保存图片到本地，返回图片的相对路径
	 * @param files
	 * @return
	 */
	public List<String> getImgPath(List<MultipartFile> files){
		List<String> paths = Lists.newArrayList();
		files.forEach((file)->{
			File localFile = null;
			if(!file.isEmpty()){
				try{
					localFile = saveToLocal(file,filePath);
					//获取相对路径
					String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
					paths.add(path);
				}catch(Exception e){
					throw new IllegalArgumentException(e);
				}
			}
		});
		return paths;
	}
	
	/**
	 * 将服务器上传的文件保存到本地
	 * @param file
	 * @param filePath2
	 * @return
	 * @throws IOException 
	 */
	private File saveToLocal(MultipartFile file,String filePath2) throws IOException{
		System.out.println("optionalfileName:"+file.getOriginalFilename());
		File newFile = new File(filePath+File.separator+Instant.now().getEpochSecond()+File.separator+file.getOriginalFilename());
		if(!newFile.exists()){
			newFile.getParentFile().mkdirs();
			newFile.createNewFile();
		}
		//将上传的文件写入到新建的文件中
		Files.write(file.getBytes(), newFile);
		return newFile;
	}
	
}
