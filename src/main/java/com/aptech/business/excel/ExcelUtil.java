package com.aptech.business.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * jxls导出excel 
 */
public class ExcelUtil {
	
	/**
	 * 下载excel 
	 * @param request
	 * @param response
	 * @param templateFileName 模板名
	 * @param destFileName 导出文件名
	 * @param resultMap 包含数据
	 * @throws UnsupportedEncodingException
	 */
	public static void export(HttpServletRequest request,HttpServletResponse response,String templateFileName,String destFileName,Map<String,Object> resultMap) throws UnsupportedEncodingException{
	       // XLSTransformer transformer = new XLSTransformer();  
	        InputStream in=null;  
	        OutputStream out=null;  
	        //设置响应   
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	        response.setHeader("Content-Disposition","attachment;filename=" + new String(destFileName.getBytes("gb2312"), "ISO8859-1"));
	        try {
	        	templateFileName=request.getSession().getServletContext().getRealPath("/") + "reportTemplet/"+templateFileName;
	            in=new FileInputStream(templateFileName);  
	            XLSTransformer transformer = new XLSTransformer();
	            XSSFWorkbook  workbook=(XSSFWorkbook) transformer.transformXLS(in, resultMap);
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            //sheet.addMergedRegion(new CellRangeAddress(3, 2+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),0,0));  
	            //sheet.addMergedRegion(new CellRangeAddress(6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0), 6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),2,20));
	            if(resultMap.get("dataSize")!=null){
	            	@SuppressWarnings("unchecked")
					List<int[]> dataSize= (List<int[]>) resultMap.get("dataSize");
	            	for(int[] point:dataSize){
	    				//合并单无格
	    				sheet.addMergedRegion(new CellRangeAddress(point[0],point[1],point[2],point[3])); 
	    			}
	           }
	            if(resultMap.get("dataStyle")!=null){
	            	@SuppressWarnings("unchecked")
					List<String[]> dataStyle= (List<String[]>) resultMap.get("dataStyle");
	            	for(String [] point:dataStyle){
	            		try {
	            			//字体颜色
//	            			POIExcelUtil.writeDataToExcel(2, 5, sheet, "停电",  
//	            					Cell.CELL_TYPE_STRING, createStyles(workbook));
	            			POIExcelUtil.writeDataToExcel(Integer.parseInt(point[0]), 
	            					Integer.parseInt(point[1]), sheet, String.valueOf(point[2]),  
	            					Integer.parseInt(point[3]), createStyles(workbook));
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
	    			}
	           }
	            out=response.getOutputStream();  
	            workbook.write(out);  
	            out.flush();  
	        }catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (ParsePropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {  
	            if (in!=null){try {in.close();} catch (IOException e) {}}  
	            if (out!=null){try {out.close();} catch (IOException e) {}}  
	        }  
	    }  
	 /** 
     * Create a library of cell styles 
     */  
    private static CellStyle createStyles(Workbook wb) {  
    	 XSSFCellStyle style= (XSSFCellStyle)wb.createCellStyle();  
    	 style.setAlignment(CellStyle.ALIGN_LEFT);  
         Font formulaFont = wb.createFont();  
         formulaFont.setColor(Font.COLOR_RED); 
         style.setFont(formulaFont); 
         style.setBorderRight(CellStyle.BORDER_THIN);  
         style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderLeft(CellStyle.BORDER_THIN);  
         style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderTop(CellStyle.BORDER_THIN);  
         style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderBottom(CellStyle.BORDER_THIN);  
         style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
        return style;  
    }  
    
    /**
	 * 下载excel 
	 * @param request
	 * @param response
	 * @param templateFileName 模板名
	 * @param destFileName 导出文件名
	 * @param resultMap 包含数据
	 * @throws UnsupportedEncodingException
	 */
	public static void exportNew(HttpServletRequest request,HttpServletResponse response,String templateFileName,String destFileName,Map<String,Object> resultMap) throws UnsupportedEncodingException{
	       // XLSTransformer transformer = new XLSTransformer();  
	        InputStream in=null;  
	        OutputStream out=null;  
	        //设置响应   
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	        response.setHeader("Content-Disposition","attachment;filename=" + new String(destFileName.getBytes("gb2312"), "ISO8859-1"));
	        try {
	        	templateFileName=request.getSession().getServletContext().getRealPath("/") + "reportTemplet/"+templateFileName;
	            in=new FileInputStream(templateFileName);  
	            XLSTransformer transformer = new XLSTransformer();
	            XSSFWorkbook  workbook=(XSSFWorkbook) transformer.transformXLS(in, resultMap);
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            //sheet.addMergedRegion(new CellRangeAddress(3, 2+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),0,0));  
	            //sheet.addMergedRegion(new CellRangeAddress(6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0), 6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),2,20));
	            if(resultMap.get("dataSize")!=null){
	            	@SuppressWarnings("unchecked")
					List<int[]> dataSize= (List<int[]>) resultMap.get("dataSize");
	            	for(int[] point:dataSize){
	    				//合并单无格
	    				sheet.addMergedRegion(new CellRangeAddress(point[0],point[1],point[2],point[3])); 
	    			}
	           }
	            if(resultMap.get("dataStyle")!=null){
	            	@SuppressWarnings("unchecked")
					List<String[]> dataStyle= (List<String[]>) resultMap.get("dataStyle");
	            	for(String [] point:dataStyle){
	            		try {
	            			//字体颜色
//	            			POIExcelUtil.writeDataToExcel(2, 5, sheet, "停电",  
//	            					Cell.CELL_TYPE_STRING, createStyles(workbook));
	            			POIExcelUtil.writeDataToExcel(Integer.parseInt(point[0]), 
	            					Integer.parseInt(point[1]), sheet, String.valueOf(point[2]),  
	            					Integer.parseInt(point[3]), createStylesNew(workbook,resultMap.get("algin").toString()));
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
	    			}
	           }
	            out=response.getOutputStream();  
	            workbook.write(out);  
	            out.flush();  
	        }catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (ParsePropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {  
	            if (in!=null){try {in.close();} catch (IOException e) {}}  
	            if (out!=null){try {out.close();} catch (IOException e) {}}  
	        }  
	    }  
	 /** 
     * Create a library of cell styles 
     */  
    private static CellStyle createStylesNew(Workbook wb,String textalgin) {  
    	 XSSFCellStyle style= (XSSFCellStyle)wb.createCellStyle();
    	 if(StringUtils.equals(textalgin, "LEFT")){
    		 style.setAlignment(CellStyle.ALIGN_LEFT); 
    	 }else if(StringUtils.equals(textalgin, "RIGHT")){
    		 style.setAlignment(CellStyle.ALIGN_RIGHT);
    	 }else if(StringUtils.equals(textalgin, "CENTER")){
    		 style.setAlignment(CellStyle.ALIGN_CENTER);
    	 }
         Font formulaFont = wb.createFont();  
         formulaFont.setColor(Font.COLOR_RED); 
         style.setFont(formulaFont); 
         style.setBorderRight(CellStyle.BORDER_THIN);  
         style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderLeft(CellStyle.BORDER_THIN);  
         style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderTop(CellStyle.BORDER_THIN);  
         style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
         style.setBorderBottom(CellStyle.BORDER_THIN);  
         style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
        return style;  
    }  
  
}
