package com.wilmir.txvcc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MyDataRestConfig implements WebMvcConfigurer, RepositoryRestConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowCredentials(false)
			.allowedOrigins("*")
			.allowedMethods("*");
	}

	
	
	
	/*
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		exposeIds(config);
		
		config.getCorsRegistry()
				.addMapping("/**")
				.allowCredentials(false)
				.allowedOrigins("*")
				.allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE")
				.exposedHeaders("Authorization", "Content-Type");
		
	}
	*/

	/*
	private void exposeIds(RepositoryRestConfiguration config) {
		
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		
		config.exposeIdsFor(domainTypes);
	}
	*/
	

	
}
