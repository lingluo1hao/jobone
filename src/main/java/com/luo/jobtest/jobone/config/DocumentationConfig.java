package com.usys.store.zuul.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider{

	@Override
	public List<SwaggerResource> get() {
		ArrayList<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("会员服务", "/member/v2/api-docs", "2.0"));
        resources.add(swaggerResource("店员服务", "/shopper/v2/api-docs", "2.0"));
        resources.add(swaggerResource("基础服务", "/base/v2/api-docs", "2.0"));
        resources.add(swaggerResource("用户服务", "/user/v2/api-docs", "2.0"));
        resources.add(swaggerResource("后台管理服务", "/management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("文件服务", "/file/v2/api-docs", "2.0"));
        resources.add(swaggerResource("数据概览服务","/overview/v2/api-docs","2.0"));
        return resources;
	}
	
	private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
