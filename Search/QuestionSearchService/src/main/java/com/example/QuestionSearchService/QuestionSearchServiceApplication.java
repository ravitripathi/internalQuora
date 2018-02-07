package com.example.QuestionSearchService;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class QuestionSearchServiceApplication {


	@Bean
	public SolrClient solrClient() {
		SolrClient client=new HttpSolrClient.Builder().withBaseSolrUrl("http://localhost:8983/solr/quora").build();

		return client;

	}
	public static void main(String[] args) {
		SpringApplication.run(QuestionSearchServiceApplication.class, args);
	}





}
