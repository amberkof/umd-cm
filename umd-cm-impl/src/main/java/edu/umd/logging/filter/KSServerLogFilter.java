package edu.umd.logging.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
 



public final class KSServerLogFilter implements Filter 
{
  private FilterConfig filterConfigObj = null;

  public void init(FilterConfig filterConfigObj) {
    this.filterConfigObj = filterConfigObj;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException 
  {
	HttpServletRequest httpRequest = ((HttpServletRequest) request);
    String remoteAddress =  request.getRemoteAddr();
    String uri = httpRequest.getRequestURI();
	String protocol = request.getProtocol();
	String localHostname = java.net.InetAddress.getLocalHost().getHostName();
	String localIp = java.net.InetAddress.getLocalHost().getAddress().toString();
	int localPort = httpRequest.getLocalPort();
	String serverHostname = httpRequest.getServerName();
	int serverPort = httpRequest.getServerPort();
	String sessionId = null;
	String user = null;

	try{
		user = SecurityContextHolder.getContext().getAuthentication().getName();		
	}catch(NullPointerException ex){
		user = "null";
	}
	
	try{
		sessionId = httpRequest.getSession(false).getId();	
	}catch(NullPointerException ex){
		sessionId = "null";
	}
	
	

    chain.doFilter(request, response);
    
    filterConfigObj.getServletContext().log(
		"User[" + user + "]"     + 
		" Session[" + sessionId + "] " + 
        " User IP[ " + remoteAddress + "]" +
        " Local Hostname[ " + localHostname + "]" +
        " Local IP[ " +  localIp + "]" + 
        " Local Port[ " + localPort  + "]" +
        " Server Hostname[ " + serverHostname + "]" +
        " Server Port[ " + serverPort  + "]" + 
        " Resource File[" + uri + "]" +
		" Protocol[" + protocol + "]"
     );
  }

  
  public void destroy() { }
}