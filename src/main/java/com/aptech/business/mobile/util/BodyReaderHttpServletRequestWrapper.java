package com.aptech.business.mobile.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jodd.io.FastByteArrayOutputStream;
import jodd.io.StreamUtil;




public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;  

	public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		FastByteArrayOutputStream output = new FastByteArrayOutputStream();
		StreamUtil.copy(request.getReader(), output, "utf-8");
		body=output.toByteArray();
	}
	@Override  
    public BufferedReader getReader() throws IOException {  
        return new BufferedReader(new InputStreamReader(getInputStream()));  
    }  
  
    @Override  
    public ServletInputStream getInputStream() throws IOException {  
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
        return new ServletInputStream() {  
  
            @Override  
            public int read() throws IOException {  
                return bais.read();  
            }  
        };  
    }  
	      
}
