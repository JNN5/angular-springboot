package io.twodigits.todo.repository;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import io.twodigits.todo.model.TodoDynamoDb;

public interface TodoRepositoryDynamoDb extends CrudRepository<TodoDynamoDb, String> {
  @EnableScan
  Iterable<TodoDynamoDb> findAll();
 
}
