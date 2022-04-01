/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

/**
 *
 * @author calum
 */
import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;


@Configuration
public class QRImageConversion {
	
	@Bean
	public HttpMessageConverter<BufferedImage> msgConverter() {
	    return new BufferedImageHttpMessageConverter();
	}

}
