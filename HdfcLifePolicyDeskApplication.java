package com.hdfclife.desk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hdfclife.desk.config.HdfcProperties;


@EnableConfigurationProperties(HdfcProperties.class)
@SpringBootApplication
public class HdfcLifePolicyDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HdfcLifePolicyDeskApplication.class, args);
	}

}
