package com.aptech.business.mobile.util;


public class MobileUtil {
	public static void main(String[] args) {
		String s = MobileUtil.StringEncryptDes("admin##123456##设备标识码");
		System.out.println(s);
		s=MobileUtil.decodeString(s);
		System.out.println(s);
	}

	/**
	 * 解密
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String decodeString(String value) {
		String returnStr = value;
		try {
			if (value != null) {
				while (true) {
					if (returnStr.contains("%")) {
						returnStr = DesEncodeUtil.toURLDecoded(returnStr);
					} else {
						break;
					}
				}

				returnStr = DesEncodeUtil.decryptDES(returnStr,
						DesEncodeUtil.getDesCode());
				returnStr = DesEncodeUtil.toURLDecoded(returnStr);
			} else {
				return "";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return returnStr;
	}

	/**
	 * 加密
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String StringEncryptDes(String inputStr) {
		String resultDes = "";
		try {
			resultDes = DesEncodeUtil.toURLEncoded(inputStr);
			resultDes = DesEncodeUtil.encryptDES(resultDes,
					DesEncodeUtil.getDesCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultDes;
	}


}
