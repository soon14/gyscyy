package com.aptech.business.overhaul.overhaulFile.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.overhaul.overhaulFile.domain.OverhaulFileEntity;
import com.aptech.business.overhaul.overhaulFile.exception.OverhaulFileException;
import com.aptech.business.overhaul.overhaulFile.exception.OverhaulFileExceptionType;
import com.aptech.business.overhaul.overhaulFile.service.OverhaulFileService;
import com.aptech.common.system.config.domain.SysConfigEntity;
import com.aptech.common.system.config.service.SysConfigService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.PathUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修文件包配置控制器
 *
 * @author 
 * @created 2017-08-04 14:04:07
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulFile")
public class OverhaulFileController extends BaseController<OverhaulFileEntity> {
	
	@Autowired
	private OverhaulFileService overhaulFileService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<OverhaulFileEntity> getService() {
		return overhaulFileService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//上传人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
        SysUserEntity userEntity = RequestContext.get().getUser();
        List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
		List<UserUnitRelEntity> list = userUnitRelService.findByCondition(conditions, null);
		conditions.clear();
		if(list!=null){
			Long[] funcids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				funcids[i] = list.get(i).getUnitId();
			}
			conditions.add(new Condition("unitId",FieldTypeEnum.LONG,MatchTypeEnum.IN,funcids));
		}else{
			conditions.add(new Condition("unitId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getUnitId()));

		}
        List<SysUserEntity> allUsers = sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("uploadUsers", JsonUtil.toJson(requestUserVO.getOptions()));	
		
        model.put("isUpload", overhaulFileService.checkUpload());	

        return this.createModelAndView("overhaul/overhaulFile/overhaulFileList", model);
	}
	
	/**
	 *	跳转到上传页面
	 */
	@RequestMapping("/uploadFile")
	public ModelAndView uploadFile(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String fileSize = "50";
		String fileType = "";
		List<SysConfigEntity> list = sysConfigService.findAll();
		if(list!=null && !list.isEmpty()){
			 fileSize = list.get(0).getFileMax();
			 fileType = list.get(0).getWhiteList();
		}
		model.put("fileSize", fileSize);
		model.put("fileType", fileType);
		return this.createModelAndView("overhaul/overhaulFile/uploadFile", model);
	}
	
	/**
	 *	保存上传文件
	 */
	@RequestMapping("/saveFile")
	public @ResponseBody
	ResultObj saveFile(@RequestBody OverhaulFileEntity t, HttpServletRequest request) {
		return overhaulFileService.add(t);
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/bulkDelete", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		return overhaulFileService.deleteBulk(ids);
	}
	@RequestMapping(value = "/checkUpload", method = RequestMethod.POST)
	public @ResponseBody ResultObj checkUpload() {
		ResultObj resultObj =  new ResultObj();
		Boolean flag = overhaulFileService.checkUpload();
		resultObj.setResult(""+flag);
		return resultObj;
	}
	
	/**
	 * 批量下载
	 */
	@RequestMapping(value = "/bulkDowload/{ids}", method = RequestMethod.GET)
	public  void bulkDowload( @PathVariable String ids, HttpServletRequest request,HttpServletResponse response) {
		String[] idsArray = ids.substring(1, ids.length()-1).split(",");
		long[] idsArr= new long[idsArray.length];
		for(int i=0;i<idsArray.length;i++){
			idsArr[i] = Long.valueOf(idsArray[i]);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("id", FieldTypeEnum.LONG, MatchTypeEnum.IN,idsArr ));

		List<OverhaulFileEntity> list = overhaulFileService.findByCondition(conditions, null);
		String url = request.getSession().getServletContext().getRealPath("/");
		try{
			DateFormatUtil df = DateFormatUtil.getInstance("yyyyMMddHHmmss");
			String zipfile = url+"/upload/"+df.format(new Date())+".zip";
			if (!(new File(zipfile)).canWrite()) {
				FileUtils.forceMkdir(new File(new File(zipfile).getParent()));
			}
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			out.setEncoding("utf-8");
			File temp = null;
			for (int i = 0; i < list.size(); i++) {
				if (url.endsWith(File.separator)) {
					temp = new File(url + list.get(i).getAttchmentId());
				} else {
					temp = new File(url + File.separator + list.get(i).getAttchmentId());
				}

				if (temp.isFile()) {
					ZipEntry entry = new ZipEntry(list.get(i).getFileName());
					out.putNextEntry(entry);
					FileInputStream in = new FileInputStream(temp);
					int length = 0;
					byte[] b = new byte[1024];
					while ((length = in.read(b, 0, 1024)) != -1) {
						out.write(b, 0, length);
					}
					in.close();
				}
			}
			out.close();
			downloadFile(response, zipfile, "检修文件包.zip");

		}catch(Exception e){
			throw new OverhaulFileException(OverhaulFileExceptionType.FILE_TYPE.getErrorCode(),OverhaulFileExceptionType.FILE_TYPE.getErrorMsg());
		}
	}
	
	
	@RequestMapping(value = "/checkFile/{id}", method = RequestMethod.GET)
	public @ResponseBody
	ResultObj checkFile(@PathVariable Long id, HttpServletRequest request) {
		OverhaulFileEntity entity = overhaulFileService.findById(id);
		String fileName = PathUtil.getRealPath()+ entity.getAttchmentId();
		File file = new File(fileName);
		if (!file.exists()) {
			throw new OverhaulFileException(OverhaulFileExceptionType.FILE_TYPE.getErrorCode(),OverhaulFileExceptionType.FILE_TYPE.getErrorMsg());
		}
		return new ResultObj();
	}
	/**
	 * 文件下载
	 */
	@RequestMapping(value = "/downloadFile/{id}", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,@PathVariable Long id) {
		OverhaulFileEntity entity = overhaulFileService.findById(id);
		String fileName = PathUtil.getRealPath()+ entity.getAttchmentId();
		InputStream bis = null;
		BufferedOutputStream out= null;
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
			String filename = entity.getFileName();
			filename = URLEncoder.encode(filename, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ filename);
			response.setContentType("multipart/form-data");
			out = new BufferedOutputStream(response.getOutputStream());
			int len = 0;
			while ((len = bis.read()) != -1) {
				out.write(len);
				out.flush();
			}	
			bis.close();
			out.close();
		} catch (Exception e) {
			throw new OverhaulFileException(OverhaulFileExceptionType.FILE_TYPE.getErrorCode(),OverhaulFileExceptionType.FILE_TYPE.getErrorMsg());
		}finally{
            if (out!=null ){try {out.close();} catch (IOException e) {}}  
		}
	}
	
	private void downloadFile(HttpServletResponse response, String zipTempPath, String zipName){
		File serverFile=new File(zipTempPath);
		if(serverFile.exists()){
			try {
				response.setHeader("Content-Disposition", "attachment; filename="+new String(zipName.getBytes("UTF-8"),"iso-8859-1"));
				response.setContentType("application/x-msdowmload");
				OutputStream OutPutStream=response.getOutputStream();
				FileInputStream fileInputStream=new FileInputStream(serverFile);
				byte bytes[]=new byte[1024];
				int len=0;
				while((len=fileInputStream.read(bytes))!=-1){
					OutPutStream.write(bytes,0,len);
				}
				OutPutStream.close();
				fileInputStream.close();
			} catch (Exception ue) {
				throw new OverhaulFileException(OverhaulFileExceptionType.FILE_TYPE.getErrorCode(),OverhaulFileExceptionType.FILE_TYPE.getErrorMsg());
			}finally{
				File zipFile = new File(zipTempPath);
				zipFile.delete();
			}
		}else{
			throw new OverhaulFileException(OverhaulFileExceptionType.FILE_TYPE.getErrorCode(),OverhaulFileExceptionType.FILE_TYPE.getErrorMsg());
		}
	}
	
	@RequestMapping("/upload")
	public @ResponseBody Map<String, String> upload(HttpServletRequest request, MultipartFile file) throws IOException {
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
		Long attachmentSize = new Long(100);
		long fileSize = 0;
		
		fileSize = attachmentSize*1024*1024;
		
		if (file.getSize() > fileSize) {
			resultMap.put("result", "error");
			resultMap.put("errorMsg", "文件超出最大值"+attachmentSize.toString()+"M...");
			return resultMap;
		}
		String fileName = file.getOriginalFilename();
		String[] tempNames = fileName.split("\\.");
		String extendName = (tempNames)[tempNames.length-1];
		//增加过滤
		if(tempNames.length>2){
			resultMap.put("result", "error");
			resultMap.put("errorMsg", "上传文件类型不正确");
			return resultMap;	
		}
		if(fileName.contains("\0")){
			resultMap.put("result", "error");
			resultMap.put("errorMsg", "上传文件类型不正确");
			return resultMap;	
		}
		//
		List<SysConfigEntity> list = sysConfigService.findAll();
		if(list!=null && !list.isEmpty()){
			String blackfileType = list.get(0).getBlackList();
			if(blackfileType.contains(extendName.toLowerCase())){				
					resultMap.put("result", "error");
					resultMap.put("errorMsg", "上传文件类型不正确");
					return resultMap;			
			}
			
		}
		String filePath = this.getFileName(request, type, fileName);
		File newFile = new File(realPath, filePath);
		file.transferTo(newFile);
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
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		StringBuilder fileName = new StringBuilder();
		StringBuilder fileDir = new StringBuilder();

		Date now = new Date();

		fileName.append(fileFormat.format(now)).append("_").append(title);
		//fileName.append(fileFormat.format(now));

		fileDir.append("/upload").append("/").append(type).append("/").append(dateFormat.format(now));

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
}