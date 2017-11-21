package com.providesupport.monitoring;

import com.providesupport.monitoring.WebsiteMonitoringToolApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebsiteMonitoringToolApplication.class);
	}

}
