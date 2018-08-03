package com.usys.store.zuul.hystrix.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.usys.store.zuul.constant.ServerConst;

@Component 
public class UserServiceFallback implements ZuulFallbackProvider{
	
	private String result = "{\"code\":\"50001\",\"msg\":\"服务器繁忙\",\"data\":\"用户权限服务（userService）不可用\"}";

	@Override
	public String getRoute() {
		return ServerConst.STORE_USER_CONSUMER;
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {  
            @Override  
            public HttpStatus getStatusCode() throws IOException {  
                return HttpStatus.OK;  
            }  
  
            @Override  
            public int getRawStatusCode() throws IOException {  
                return this.getStatusCode().value();  
            }  
  
            @Override  
            public String getStatusText() throws IOException {  
                return this.getStatusCode().getReasonPhrase();  
            }  
  
            @Override  
            public void close() {  
  
            }  
  
            @Override  
            public InputStream getBody() throws IOException {  
                return new ByteArrayInputStream(result.getBytes()); 
            }  
  
            @Override  
            public HttpHeaders getHeaders() {  
                HttpHeaders headers = new HttpHeaders();  
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));  
                headers.setContentType(mt);  
                return headers;  
            }  
        };  
	}

}
