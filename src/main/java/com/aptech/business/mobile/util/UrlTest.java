package com.aptech.business.mobile.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/** @ClassName:     UrlTest.java 
 * @author         changl
 * @version        V1.0  
 * @Date           2017年10月26日 下午1:28:50 
 */

public class UrlTest {
	
	  public static void main(String[] args) throws UnsupportedEncodingException {
		  //获取TokenId
		  String accessCode="admin##123456##设备标识码";
		  accessCode=MobileUtil.StringEncryptDes(accessCode);
		  accessCode= URLEncoder.encode(accessCode,"utf-8");
		  String result =get("http://127.0.0.1:8080/gyscyy/mobile/token/getToken"
		  ,"accessCode="+accessCode);
//		  String result =post("http://127.0.0.1:8080/gyscyy/mobile/workTicket/getElectricOneInfo"
//				  ,"accessCode="+accessCode);
		  System.out.println(MobileUtil.decodeString(result));
		  JSONObject object=JSONObject.fromObject(MobileUtil.decodeString(result));
		  String tokenId=object.getString("token");
		  tokenId=MobileUtil.StringEncryptDes(tokenId);
		  tokenId= URLEncoder.encode(tokenId,"utf-8");
//		  result= URLEncoder.encode(result,"utf-8");
//		  String result6 =post("http://127.0.0.1:8080/gyscyy/mobile/workTicket/getQfDetail"
//				  ,"json="+result);
		  
//		  String tokenId="0j1EnT3BtPRgysqkK19s86FgDlDi3IEnpssj4pFahK4h3ToGdNyxA4NxYUdbERBB";
//		  //加密
//		  String result2 =post("http://127.0.0.1:8080/gyscyy/mobile/login/login"
//				  ,"token="+tokenId);
//		  System.out.println(MobileUtil.decodeString(result2));
//		  JSONObject object=JSONObject.fromObject(MobileUtil.decodeString(result));
//		  String tokenId=object.getString("token");
//		  tokenId=MobileUtil.StringEncryptDes(tokenId);
//		  tokenId= URLEncoder.encode(tokenId,"utf-8");
//		  System.out.println(tokenId);
//		  //登入
//		  String result2 =post("http://127.0.0.1:8080/gyscyy/mobile/login/login"
//				  ,"token="+tokenId);
//		  System.out.println(MobileUtil.decodeString(result2));
//		  //修改密码 
//		  String result2 =post("http://127.0.0.1:8080/gyscyy/mobile/login/login"
//				  ,"token="+tokenId);
//		  System.out.println(MobileUtil.decodeString(result2));
//////		  //修改密码 
//		  String oldPsw="123456";
//		  String newPsw="123456";
//		  oldPsw=MobileUtil.StringEncryptDes(oldPsw);
//		  oldPsw= URLEncoder.encode(oldPsw,"utf-8");
//		  newPsw=MobileUtil.StringEncryptDes(newPsw);
//		  newPsw= URLEncoder.encode(newPsw,"utf-8");
//		  String result3 =post("http://127.0.0.1:8080/gyscyy/mobile/login/pswChanged"
//				  ,"token="+tokenId+"&oldPsw="+oldPsw+"&newPsw="+newPsw);
//		  System.out.println(MobileUtil.decodeString(result3));
//		  //获取代办列表
		  String pageSize="1";
		  pageSize= URLEncoder.encode(MobileUtil.StringEncryptDes(pageSize),"utf-8");
		  String currentPage="1";
		  currentPage= URLEncoder.encode(MobileUtil.StringEncryptDes(currentPage),"utf-8");
		  String dbrw =post("http://127.0.0.1:8080/gyscyy/mobile/todoTask/getWorkList"
				  ,"token="+tokenId+"&pageSize="+pageSize+"&currentPage="+currentPage);
		  System.out.println(MobileUtil.decodeString(dbrw));
	 } 
	    public static  String get(String urlNameString, String param){
	        urlNameString = urlNameString + "?" + param;
	        String result="";
	          try {
	                // 根据地址获取请求
	                HttpGet request = new HttpGet(urlNameString);//这里发送get请求
	                // 获取当前客户端对象
	                HttpClient httpClient = new DefaultHttpClient();
	                // 通过请求对象获取响应对象
	                HttpResponse response = httpClient.execute(request);
	                
	                // 判断网络连接状态码是否正常(0--200都数正常)
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result= EntityUtils.toString(response.getEntity(),"utf-8");
	                } 
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        return result;
	        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
	    }
	    public static  String post(String urlNameString, String param){
	    	String result="";
	    	try {
	        	//POST的URL
	        	HttpPost httppost=new HttpPost(urlNameString);
	        	//建立HttpPost对象
	        	List<NameValuePair> params=new ArrayList<NameValuePair>();
	        	String []paramArray=param.split("&");
	        	for (int i = 0; i < paramArray.length; i++) {
	        		String [] par=paramArray[i].toString().split("=");
	        		//建立一个NameValuePair数组，用于存储欲传送的参数
	        		params.add(new BasicNameValuePair(par[0],par[1]));
				}
	        	//添加参数
	        	httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
	        	//设置编码
	        	HttpResponse response=new DefaultHttpClient().execute(httppost);
	        	//发送Post,并返回一个HttpResponse对象
		        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){//如果状态码为200,就是正常返回
						result=EntityUtils.toString(response.getEntity());
		        }
			} catch (ParseException | IOException e) {
					e.printStackTrace();
			}
	        return result;
	        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
	    }
}
