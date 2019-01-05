package com.aptech.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aptech.framework.exception.ServerException;
import com.aptech.framework.exception.request.BadRequestException;
import com.aptech.framework.exception.request.RequestException;
import com.aptech.framework.log.Log;
import com.aptech.framework.util.FileUtil;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.PathUtil;

/**
 * 
 * {该处请说明该class的含义和作用}
 *
 * @author zhangjx
 * @created 2017年8月19日 上午11:25:13 
 * @lastModified 
 * @history
 *
 */
public class ExcelExportUtil {
	
	private static Log log = Log.getInstance(ExcelExportUtil.class);
	
	/**
	 * 版本
	 */
	public final static String VERSION = "V1.0";

	/**
	 * 配置文件目录的位置。所有的配置文件应设在这里。
	 */
	private String excelName;
	
	private final String BASE_PATH = PathUtil.getRealPath() + "/reportTemplet" + File.separator ;

	/**
	 * 构造方法
	 * 
	 * @param excelName
	 *            excel文件路径
	 * @throws RequestException
	 *             自定义异常
	 */
	public ExcelExportUtil(final String excelName) throws RequestException {
		this.excelName = BASE_PATH + excelName;
	}
	
	public String generateExcel(Map<String, Object> resultMap) throws BadRequestException, IOException{		
		InputStream is = null;
		XSSFWorkbook  workbook = null;
		try {
			is = new FileInputStream(this.excelName);
			XLSTransformer transformer = new XLSTransformer();
			workbook =(XSSFWorkbook) transformer.transformXLS(is, resultMap);
		} catch (Exception e) {
			throw new BadRequestException("读取文件[" + excelName + "]失败。", "-10001",  e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (final IOException e) {
				log.error(e.getMessage());
			}
		}
		String tempFileName = PathUtil.getRealPath() + "/reportTemplet" + File.separator
				+ IdUtil.creatUUID() + ".xls";
		File file = new File(tempFileName);
		file.createNewFile();
		OutputStream out = null;
		try {
			out = new FileOutputStream(tempFileName);
			workbook.write(out);
		} catch (FileNotFoundException e) {
			throw new ServerException("临时文件[" + tempFileName + "]没有生成。",  e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				log.error(e.getMessage());
			}
		}
		return tempFileName;
	}
	
	
	public String makeExcel(Map<String, Object> resultMap,List<int[]> list) throws BadRequestException, IOException{		
		InputStream is = null;
		XSSFWorkbook  workbook = null;
		try {
			is = new FileInputStream(this.excelName);
			XLSTransformer transformer = new XLSTransformer();
			workbook =(XSSFWorkbook) transformer.transformXLS(is, resultMap);
			XSSFSheet sheet =  workbook.getSheetAt(0);
			for(int[] point:list){
				//合并单无格
				sheet.addMergedRegion(new CellRangeAddress(point[0],point[1],point[2],point[3])); 
			}
		} catch (Exception e) {
			throw new BadRequestException("读取文件[" + excelName + "]失败。", "-10001",  e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (final IOException e) {
				log.error(e.getMessage());
			}
		}
		String tempFileName = PathUtil.getRealPath() + "/upload/excel" + File.separator
				+ IdUtil.creatUUID() + ".xls";
		File file = new File(tempFileName);
		file.createNewFile();
		OutputStream out = null;
		try {
			out = new FileOutputStream(tempFileName);
			workbook.write(out);
		} catch (FileNotFoundException e) {
			throw new ServerException("临时文件[" + tempFileName + "]没有生成。",  e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				log.error(e.getMessage());
			}
		}
		return tempFileName;
	}


	/**
	 * 获取无后缀名的文件名
	 * 
	 * @Title: getExcelNameUnuffix
	 * @Description:
	 * @return
	 */
	public String getExcelNameUnuffix() {
		return FileUtil.getFileNameUnuffix(this.excelName);
	}

	/**
	 * 获取无后缀名的文件名
	 * 
	 * @Title: getExcelNameUnuffix
	 * @Description:
	 * @return
	 */
	public String getExcelName() {
		return FileUtil.getFileName(this.excelName);
	}


}
