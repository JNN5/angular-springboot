package io.twodigits.todo.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.twodigits.todo.repository.TodoRepositoryDynamoDb;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = TodoRepositoryDynamoDb.class)
public class DynamoDBConfig {

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
      return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-central-1"))
                .build();
  }
}