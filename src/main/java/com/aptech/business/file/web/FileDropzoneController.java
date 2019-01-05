package com.aptech.business.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aptech.business.component.dictionary.EPlatform;
import com.aptech.framework.util.DateUtil;
import com.aptech.framework.util.PathUtil;

/** @ClassName:     FileDropzoneController.java 
 * @author         changl
 * @version        V1.0  
 * @Date           2017年6月22日 下午4:16:59 
 */
@Controller
@RequestMapping("/fileDropzone")
public class FileDropzoneController {

	@RequestMapping("/upload")
	public @ResponseBody Map<String, String> upload(HttpServletRequest request,  MultipartFile file) throws IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		String realPath = PathUtil.getRealPath();
		String type = request.getParameter("type");

		if (file == null) {
			try {
				throw new Exception("上传失败：文件为空");
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("result", "error");
				resultMap.put("errorMsg", "上传失败：文件为空");
				return resultMap;
			}
		}
		
		String fileName = file.getOriginalFilename();

		String filePath = this.getFileName(request, type, fileName);
		File newFile = new File(realPath, filePath);
		file.transferTo(newFile);
//		backupFileInLocal(realPath+filePath,filePath,type);

		resultMap.put("result", "success");
		resultMap.put("url", filePath);
		resultMap.put("name", fileName);
		resultMap.put("file_size", String.valueOf(file.getSize()) + " bytes");
		resultMap.put("format", file.getContentType());
		return resultMap;
	}

	// 返回文件的相对路径
	private String getFileName(HttpServletRequest request, String type, String title) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat secondFormat = new SimpleDateFormat("HHmmss");

		StringBuilder fileName = new StringBuilder();
		StringBuilder fileDir = new StringBuilder();

		Date now = new Date();
		fileName.append(title);
		StringBuffer sb = new StringBuffer();
		fileDir.append("/upload").append("/").append(type).append("/").append(dateFormat.format(now)).append("/").append(secondFormat.format(now).toString());

		String filePath = fileDir.toString();

		String basePath = this.getClass().getClassLoader().getResource("").getPath();
		int index = basePath.indexOf("WEB-INF");
		String realPath = basePath.substring(0, index);

		realPath = realPath + filePath;
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return filePath + "/" + fileName;
	}
	
	 private  void backupFileInLocal(String sourFilePath, String targetFilePath,String type) { 
	        String inputname = sourFilePath;  
	        String outputname = "";  
	        if(StringUtils.equals(OSinfo.getOSname().getName(), EPlatform.Windows.getName())){
	        	outputname = "E:/upload/"+type; 
	        }else{
	        	outputname = "/home/administrator/upload/"+type;
	        }
	        FileInputStream input = null;;  
	        FileOutputStream output = null;
	        try{  
		        input  = new FileInputStream(inputname);
		        File file  = new File(outputname);
		        if(!file.isDirectory()){
		        	file.mkdirs();
		        }
		        output = new FileOutputStream("E:"+targetFilePath);  
		        // 1K的数据缓冲
		        byte[] bs = new byte[1024];
	            // 读取到的数据长度
	            int len;
	            // 开始读取
	            while ((len = input.read(bs)) != -1) {
	            	output.write(bs, 0, len);
	            }
	        }catch(IOException e){  
	            e.printStackTrace();  
	        }finally{
	        	 // 完毕，关闭所有链接
	            try {
	            	output.close();
	            	input.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }  
}
