package io.twodigits.todo.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.twodigits.todo.repository.TodoRepositoryDynamoDb;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = TodoRepositoryDynamoDb.class)
public class DynamoDBConfig {

  private String defaultValueIfEndpointIsNotInEnv = "using the AWS default endpoint";
  private String dynamoDBLocalEndpoint;

  @Autowired
  public DynamoDBConfig(Environment env) {
    this.dynamoDBLocalEndpoint = env.getProperty("DYNAMODB_ENDPOINT", defaultValueIfEndpointIsNotInEnv);
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {

    if (dynamoDBLocalEndpoint.equals(defaultValueIfEndpointIsNotInEnv))      
      return AmazonDynamoDBClientBuilder.standard().build();
    else {
      return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBLocalEndpoint, "eu-central-1"))
                .build();
    }
  }
}