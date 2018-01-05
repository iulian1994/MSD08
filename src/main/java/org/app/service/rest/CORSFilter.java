package org.app.service.rest;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

@WebFilter(urlPatterns="*")
public class CORSFilter implements Filter{
	private static final Logger logger = Logger.getLogger(CORSFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// CORS headers
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		String reqHead = httpRequest.getHeader("Access-Control-Request-Headers");
		if(null != reqHead){
			httpResponse.addHeader("Access-Control-Allow-Headers", reqHead);
        }
//		logger.info("**** DEBUG: CORSFilter added headers. " + getRequestBody(httpRequest));
		logger.info("**** DEBUG: CORSFilter added headers. ");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("**** DEBUG: CORSFilter init");
	}

	
	private String getRequestBody(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
	    BufferedReader bufferedReader = null;
	    String content = "";

	    try {
	        //InputStream inputStream = request.getInputStream();
	        //inputStream.available();
	        //if (inputStream != null) {
	        bufferedReader =  request.getReader() ; //new BufferedReader(new InputStreamReader(inputStream));
	        char[] charBuffer = new char[128];
	        int bytesRead;
	        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
	            sb.append(charBuffer, 0, bytesRead);
	        }
	        //} else {
	        //        sb.append("");
	        //}
	        return sb.toString();
	    } catch (IOException ex) {
	        //throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                //throw ex;
	            }
	        }
	    }

	    return null;
	}
}
