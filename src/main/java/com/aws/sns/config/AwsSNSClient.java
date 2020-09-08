package com.aws.sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;

@Configuration
public class AwsSNSClient {
	
	@Value("${aws.access.key}")
	private String AWS_AK;
	
	@Value("${aws.secreat.key}")
	private String AWS_SK;

	@Primary
	@Bean
	public AmazonSNSClient getSnsClient() {
		return (AmazonSNSClient) AmazonSNSAsyncClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_AK, AWS_SK))).build();
	}
}
