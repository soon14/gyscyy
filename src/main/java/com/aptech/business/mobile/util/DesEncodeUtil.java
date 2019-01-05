package com.aptech.business.mobile.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncodeUtil {
	private static byte[] IV = { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static String DES = "DES";
	private static String CIPHER = "DES/CBC/PKCS5Padding";
	
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		IvParameterSpec zeroIv = new IvParameterSpec(IV);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("utf-8"), DES);
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
		return Base64.encode(encryptedData);
	}

	@SuppressWarnings("static-access")
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] byteMi = new Base64().decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(IV);
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("utf-8"), DES);
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData,"utf-8");
	}
	
	
	public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try
        {
//            String str = new String(paramString.getBytes(), "utf-8");
            String str = URLEncoder.encode(paramString, "utf-8");
            return str;
        }
        catch (Exception localException)
        {
        }

        return "";
    }
    public static String toURLDecoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try
        {
//            String str = new String(paramString.getBytes(), "utf-8");
            String str = URLDecoder.decode(paramString, "utf-8");
            return str;
        }
        catch (Exception localException)
        {
        }

        return "";
    }

    
	/**
	 * 获取Des密钥
	 * 
	 * @return Des密钥
	 */
	public static String getDesCode() {
		return "#sxsd!qe";
//		return PropertiesConfig.props.getProperty("desCode");
	}

}