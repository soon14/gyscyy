package com.aptech.business.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * jxls导出excel 
 */
public class ExcelUtilAll {
	
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
	        	templateFileName=request.getSession().getServletContext().getRealPath("/") + "\\reportTemplet\\"+templateFileName;
	            in=new FileInputStream(templateFileName);  
	            XLSTransformer transformer = new XLSTransformer();
	            XSSFWorkbook  workbook=(XSSFWorkbook) transformer.transformXLS(in, resultMap);
	            //XSSFSheet sheet = workbook.getSheetAt(0);
	            //sheet.addMergedRegion(new CellRangeAddress(3, 2+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),0,0));  
	            //sheet.addMergedRegion(new CellRangeAddress(6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0), 6+Optional.fromNullable((int)resultMap.get("dataSize")).or(0),2,20));
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
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }
        for(int a=0;a<3;a++){
        	// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName+"("+a+")");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

            //声明列对象
            HSSFCell cell = null;

            //创建标题
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            for(int i=0;i<values.length;i++){
                row = sheet.createRow(i + 1);
                for(int j=0;j<values[i].length;j++){
                    //将内容按顺序赋给对应的列对象
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }
            
            
        }
        
        return wb;
    }
}
