package com.aptech.business.mobile.ticketManager.test;

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

import com.aptech.business.mobile.util.MobileUtil;

/** @ClassName:     UrlTest.java 
 * @author         zzq
 * @version        V1.0  
 * @Date           2017年11月01日 下午1:28:50 
 */

public class WorkTicketTwoTest {
	
	  public static void main(String[] args) throws UnsupportedEncodingException {
		  //获取TokenId
		  String accessCode="admin##123456##设备标识码";
		  accessCode=MobileUtil.StringEncryptDes(accessCode);
		  accessCode= URLEncoder.encode(accessCode,"utf-8");
		  String result =get("http://127.0.0.1:8080/gyscyy/mobile/token/getToken"
		  ,"accessCode="+accessCode);
		  JSONObject object=JSONObject.fromObject(MobileUtil.decodeString(result));
		  String tokenId=object.getString("token");
		  tokenId=MobileUtil.StringEncryptDes(tokenId);
		  tokenId= URLEncoder.encode(tokenId,"utf-8");
//		  System.out.println(tokenId);
//		  //登入
//		  String result2 =post("http://127.0.0.1:8080/gyscyy/mobile/login/login"
//				  ,"token="+tokenId);
//		  System.out.println(MobileUtil.decodeString(result2));
//		  //获取代办列表
		  String dbrw =post("http://127.0.0.1:8080/gyscyy/mobile/todoTask/getWorkList"
				  ,"token="+tokenId+"&pageSize=1&currentPage=1");
		  System.out.println(MobileUtil.decodeString(dbrw));
		  
		  //电气第二种票向审批页面发送，代办列表json
		  dbrw= URLEncoder.encode(dbrw,"utf-8");
		  String detail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getElectricInfo"
				  ,"token="+tokenId+"&json="+dbrw);
		  System.out.println(MobileUtil.decodeString(detail));
		  
		  
		  //电气第二种票向签发详细页面发送，审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String qfdetail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getQfDetail"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(qfdetail));*/
		  
		  
		  //电气第二种票向签发同意发送，签发详细页面的json
		 /* qfdetail= URLEncoder.encode(qfdetail,"utf-8");
		  //放入值开始
		  qfdetail=MobileUtil.decodeString(qfdetail);
		  Map<String,Object> map=JsonUtil.getMapFromJson(qfdetail);
		  map.put("approveIdea", "111111111111111111");
		  map.put("selectUser", "admin,yn1");
		  qfdetail=JsonUtil.toJson(map);
		  qfdetail=MobileUtil.StringEncryptDes(qfdetail);
		  qfdetail= URLEncoder.encode(qfdetail,"utf-8");
		  //放入值结束
		  String qfagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeQf"
				  ,"token="+tokenId+"&json="+qfdetail);
		  System.out.println(MobileUtil.decodeString(qfagree));*/
		  
		  
		 //电气第二种票向签发不同意发送，签发详细页面的json
		 /* qfdetail= URLEncoder.encode(qfdetail,"utf-8");
		  //放入值开始
		  qfdetail=MobileUtil.decodeString(qfdetail);
		  Map<String,Object> mapqf=JsonUtil.getMapFromJson(qfdetail);
		  mapqf.put("approveIdea", "签发人不同意");
		  qfdetail=JsonUtil.toJson(mapqf);
		  qfdetail=MobileUtil.StringEncryptDes(qfdetail);
		  qfdetail= URLEncoder.encode(qfdetail,"utf-8");
		  //放入值结束
		  String qfdisagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disAgreeQf"
				  ,"token="+tokenId+"&json="+qfdetail);
		  System.out.println(MobileUtil.decodeString(qfdisagree));*/
		  
		  //收票详细页面发送json@5
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String spDetailTwo =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getSpDetail"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(spDetailTwo));*/
		  
		  //收票同意发送json@6
		  /*spDetailTwo= URLEncoder.encode(spDetailTwo,"utf-8");
		  //放入值开始
		  spDetailTwo=MobileUtil.decodeString(spDetailTwo);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(spDetailTwo);
		  mapSp.put("approveIdea", "2222222222");
		  mapSp.put("selectUser", "admin");
		  spDetailTwo=JsonUtil.toJson(mapSp);
		  spDetailTwo=MobileUtil.StringEncryptDes(spDetailTwo);
		  spDetailTwo= URLEncoder.encode(spDetailTwo,"utf-8");
		  //放入值结束
		  String spagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeSp"
				  ,"token="+tokenId+"&json="+spDetailTwo);
		  System.out.println(MobileUtil.decodeString(spagree));*/
			  
		  //收票不同意发送json@7
		  /*spDetailTwo= URLEncoder.encode(spDetailTwo,"utf-8");
		  //放入值开始
		  spDetailTwo=MobileUtil.decodeString(spDetailTwo);
		  Map<String,Object> mapBsp=JsonUtil.getMapFromJson(spDetailTwo);
		  mapBsp.put("approveIdea", "33333");
		  spDetailTwo=JsonUtil.toJson(mapBsp);
		  spDetailTwo=MobileUtil.StringEncryptDes(spDetailTwo);
		  spDetailTwo= URLEncoder.encode(spDetailTwo,"utf-8");
		  //放入值结束
		  String spdisagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disAgreeSp"
				  ,"token="+tokenId+"&json="+spDetailTwo);
		  System.out.println(MobileUtil.decodeString(spdisagree));*/
		  
		  
		  //工作票取消 发送json@8
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String fpDetail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disAgreeFp"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(fpDetail));*/
		  
		  
		  //向在提交弹出框  发送审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String againList =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/againSureList"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(againList));*/
		  
		  
		  
		  //向再提交发送,审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  //放入值开始
		  detail=MobileUtil.decodeString(detail);
		  Map<String,Object> mapZtj=JsonUtil.getMapFromJson(detail);
		  mapZtj.put("selectUser", "admin,yn1");
		  detail=JsonUtil.toJson(mapZtj);
		  detail=MobileUtil.StringEncryptDes(detail);
		  detail= URLEncoder.encode(detail,"utf-8");
		  //放入值结束
		  String againSubmit =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/againSubmit"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(againSubmit));*/
		  
		  
		  //向许可弹出框发送,审批的json
		 /* detail= URLEncoder.encode(detail,"utf-8");
		  String xkDetail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getAddXk"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(xkDetail));
		  //向许可同意发送，许可弹出框的json
		  xkDetail= URLEncoder.encode(xkDetail,"utf-8");
		  //放入值开始
		  xkDetail=MobileUtil.decodeString(xkDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(xkDetail);
		  mapSp.put("approveIdea", "许可");
		  mapSp.put("wireway", "3434343");
		  mapSp.put("qksjZhu", "2017-10-30 16:35:20");
		  xkDetail=JsonUtil.toJson(mapSp);
		  xkDetail=MobileUtil.StringEncryptDes(xkDetail);
		  xkDetail= URLEncoder.encode(xkDetail,"utf-8");
		  //放入值结束
		  String xkagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeXk"
				  ,"token="+tokenId+"&json="+xkDetail);
		  System.out.println(MobileUtil.decodeString(xkagree));*/
		  
		  
		  //向工作交底发送审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  //放入值开始
		  detail=MobileUtil.decodeString(detail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(detail);
		  mapSp.put("workDisclosure", "工作交底111");
		  detail=JsonUtil.toJson(mapSp);
		  detail=MobileUtil.StringEncryptDes(detail);
		  detail= URLEncoder.encode(detail,"utf-8");
		  //放入值结束
		  String gzjd =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/sureGzjd"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(gzjd));*/
		  
		  
		  //向工作人员变动发送审批的json
		 /* detail= URLEncoder.encode(detail,"utf-8");
		  //放入值开始
		  detail=MobileUtil.decodeString(detail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(detail);
		  mapSp.put("workPersonGroup", "666666");
		  detail=JsonUtil.toJson(mapSp);
		  detail=MobileUtil.StringEncryptDes(detail);
		  detail= URLEncoder.encode(detail,"utf-8");
		  //放入值结束
		  String gzrybd =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/workPersonChange"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(gzrybd));*/
		  
		  
		  //终结详细页面 发送，审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String zjDetail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getAddZj"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(zjDetail));*/
		  //终结确定发送,终结详细页面的json
		  /*zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值开始
		  zjDetail=MobileUtil.decodeString(zjDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(zjDetail);
		  mapSp.put("approveIdea", "终结确定");
		  mapSp.put("endTimeZhu", "2017-10-31 10:31:20");
		  mapSp.put("remarkOther", "终结");
		  mapSp.put("selectUser", "admin,yn1");
		  zjDetail=JsonUtil.toJson(mapSp);
		  zjDetail=MobileUtil.StringEncryptDes(zjDetail);
		  zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值结束
		  String zjsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/sureZj"
				  ,"token="+tokenId+"&json="+zjDetail);
		  System.out.println(MobileUtil.decodeString(zjsure));*/
		  
		  
		  
		  //向终结许可同意发送,终结详细页面的json
		 /* zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值开始
		  zjDetail=MobileUtil.decodeString(zjDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(zjDetail);
		  mapSp.put("approveIdea", "终结许可同意");
		  zjDetail=JsonUtil.toJson(mapSp);
		  zjDetail=MobileUtil.StringEncryptDes(zjDetail);
		  zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值结束
		  String zjxkagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeZjXk"
				  ,"token="+tokenId+"&json="+zjDetail);
		  System.out.println(MobileUtil.decodeString(zjxkagree));*/
		  
		  
		  //向终结许可不同意  ,发送终结详细页面的json
		  /*zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值开始
		  zjDetail=MobileUtil.decodeString(zjDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(zjDetail);
		  mapSp.put("approveIdea", "终结许可不同意");
		  zjDetail=JsonUtil.toJson(mapSp);
		  zjDetail=MobileUtil.StringEncryptDes(zjDetail);
		  zjDetail= URLEncoder.encode(zjDetail,"utf-8");
		  //放入值结束
		  String zjxkdisagree =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disagreeZjXk"
				  ,"token="+tokenId+"&json="+zjDetail);
		  System.out.println(MobileUtil.decodeString(zjxkdisagree));*/
		  
		  
		  //向延期弹出框发送审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String yqDetail =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getAddYq"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(yqDetail));*/
		  
		  //向延期确定发送 延期弹出框的json
		 /* yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值开始
		  yqDetail=MobileUtil.decodeString(yqDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(yqDetail);
		  mapSp.put("selectUser", "admin,yn1");
		  mapSp.put("approveIdea", "延期同意");
		  yqDetail=JsonUtil.toJson(mapSp);
		  yqDetail=MobileUtil.StringEncryptDes(yqDetail);
		  yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值结束
		  String yqsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/sureYq"
				  ,"token="+tokenId+"&json="+yqDetail);
		  System.out.println(MobileUtil.decodeString(yqsure));*/
		  
		  //向延期许可的确定发送，延期弹出框的json
		  /*yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值开始
		  yqDetail=MobileUtil.decodeString(yqDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(yqDetail);
		  mapSp.put("selectUser", "admin");
		  mapSp.put("approveIdea", "延期许可同意");
		  mapSp.put("delayDate", "2017-10-31 10:31:20");
		  yqDetail=JsonUtil.toJson(mapSp);
		  yqDetail=MobileUtil.StringEncryptDes(yqDetail);
		  yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值结束
		  String yqxksure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/sureYqxk"
				  ,"token="+tokenId+"&json="+yqDetail);
		  System.out.println(MobileUtil.decodeString(yqxksure));*/
		  
		  
		  //向延期工作负责人的确定发送，延期弹出框的json
		  /*yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值开始
		  yqDetail=MobileUtil.decodeString(yqDetail);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(yqDetail);
		  mapSp.put("approveIdea", "延期工作负责人同意");
		  yqDetail=JsonUtil.toJson(mapSp);
		  yqDetail=MobileUtil.StringEncryptDes(yqDetail);
		  yqDetail= URLEncoder.encode(yqDetail,"utf-8");
		  //放入值结束
		  String yqfzrsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeYqFzr"
				  ,"token="+tokenId+"&json="+yqDetail);
		  System.out.println(MobileUtil.decodeString(yqfzrsure));*/
		  
		  
		  
		  //向工作负责人变更弹出框发送，审批的json
		  /*detail= URLEncoder.encode(detail,"utf-8");
		  String gzfzrbg =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/getAddGzfzrbd"
				  ,"token="+tokenId+"&json="+detail);
		  System.out.println(MobileUtil.decodeString(gzfzrbg));*/
		  
		  //向工作负责人变更确定发送，负责人变更的弹出框的json
		  /*gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值开始
		  gzfzrbg=MobileUtil.decodeString(gzfzrbg);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(gzfzrbg);
		  mapSp.put("approveIdea", "工作负责人变更同意");
		  mapSp.put("selectUser", "admin,yn1");
		  mapSp.put("changeNewPicId", "31");
		  gzfzrbg=JsonUtil.toJson(mapSp);
		  gzfzrbg=MobileUtil.StringEncryptDes(gzfzrbg);
		  gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值结束
		  String gzfzrbgsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/sureGzfzrbg"
				  ,"token="+tokenId+"&json="+gzfzrbg);
		  System.out.println(MobileUtil.decodeString(gzfzrbgsure));*/
		  
		  //向工作负责人变更签发人同意发送，工作负责人变更弹出框的json
		  /*gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值开始
		  gzfzrbg=MobileUtil.decodeString(gzfzrbg);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(gzfzrbg);
		  mapSp.put("approveIdea", "工作负责人变更签发的同意");
		  mapSp.put("selectUser", "admin");
		  gzfzrbg=JsonUtil.toJson(mapSp);
		  gzfzrbg=MobileUtil.StringEncryptDes(gzfzrbg);
		  gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值结束
		  String qfrsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeGzfzrbgQf"
				  ,"token="+tokenId+"&json="+gzfzrbg);
		  System.out.println(MobileUtil.decodeString(qfrsure));*/
		  
		  //向工作负责人变更签发人不同意发送，工作负责人变更的弹出框的json
		 /* gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值开始
		  gzfzrbg=MobileUtil.decodeString(gzfzrbg);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(gzfzrbg);
		  mapSp.put("approveIdea", "工作负责人变更签发的不同意");
		  gzfzrbg=JsonUtil.toJson(mapSp);
		  gzfzrbg=MobileUtil.StringEncryptDes(gzfzrbg);
		  gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值结束
		  String qfrsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disAgreeGzfzrbgQf"
				  ,"token="+tokenId+"&json="+gzfzrbg);
		  System.out.println(MobileUtil.decodeString(qfrsure));*/
		  
		  
		  
		  //向工作负责人变更许可人同意发送，工作负责人变更弹出框的json
		  /*gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值开始
		  gzfzrbg=MobileUtil.decodeString(gzfzrbg);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(gzfzrbg);
		  mapSp.put("approveIdea", "工作负责人变更许可的同意");
		  gzfzrbg=JsonUtil.toJson(mapSp);
		  gzfzrbg=MobileUtil.StringEncryptDes(gzfzrbg);
		  gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值结束
		  String qfrsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/agreeGzfzrbgXk"
				  ,"token="+tokenId+"&json="+gzfzrbg);
		  System.out.println(MobileUtil.decodeString(qfrsure));*/
			  
		  //工作负责人变更许可人不同意发送，工作负责人变更弹出框的json
		 /* gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值开始
		  gzfzrbg=MobileUtil.decodeString(gzfzrbg);
		  Map<String,Object> mapSp=JsonUtil.getMapFromJson(gzfzrbg);
		  mapSp.put("approveIdea", "工作负责人变更许可的不同意");
		  gzfzrbg=JsonUtil.toJson(mapSp);
		  gzfzrbg=MobileUtil.StringEncryptDes(gzfzrbg);
		  gzfzrbg= URLEncoder.encode(gzfzrbg,"utf-8");
		  //放入值结束
		  String qfrsure =post("http://127.0.0.1:8080/gyscyy/mobile/workTicketTwo/disAgreeGzfzrbgXk"
				  ,"token="+tokenId+"&json="+gzfzrbg);
		  System.out.println(MobileUtil.decodeString(qfrsure));*/
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
