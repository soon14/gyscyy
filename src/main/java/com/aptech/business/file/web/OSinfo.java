package com.aptech.business.file.web;

import com.aptech.business.component.dictionary.EPlatform;

/**
 * @Description:   
 * @author         wangcc
 * @version        V1.0  
 * @Date           2017年12月18日 上午10:21:05 
 */
public class OSinfo {
	 private static String OS = System.getProperty("os.name").toLowerCase();  
     
	    private static OSinfo _instance = new OSinfo();  
	      
	    private EPlatform platform;  
	      
	    private OSinfo(){}  
	      
	    public static boolean isLinux(){  
	        return OS.indexOf("linux")>=0;  
	    }  
	      
	      
	    public static boolean isWindows(){  
	        return OS.indexOf("windows")>=0;  
	    }  
	      
	    /** 
	     * 获取操作系统名字 
	     * @return 操作系统名 
	     */  
	    public static EPlatform getOSname(){  
	        if (isLinux()) {  
	            _instance.platform = EPlatform.Linux;  
	        }else if (isWindows()) {  
	            _instance.platform = EPlatform.Windows;  
	        } 
	        return _instance.platform;  
	    }  
	    /** 
	     * @param args 
	     */  
	    public static void main(String[] args) {  
	        System.out.println(OSinfo.getOSname());  
	    }  

}
