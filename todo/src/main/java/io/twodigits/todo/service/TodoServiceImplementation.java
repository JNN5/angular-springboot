package io.twodigits.todo.service;

import java.util.Optional;

import com.amazonaws.services.dynamodbv2.document.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Service;

import io.twodigits.todo.model.TodoDynamoDb;
import io.twodigits.todo.repository.TodoRepositoryDynamoDb;

@Service
public class TodoServiceImplementation implements TodoService {

  @Autowired
  private TodoRepositoryDynamoDb repository;


  @Override
  public TodoDynamoDb addTodo(String text) {
    TodoDynamoDb todo = new TodoDynamoDb(text, false);
    repository.save(todo);
    return todo;
  }

  @Override
  public Iterable<TodoDynamoDb> getAllTodos() {
    return repository.findAll();
  }

  @Override
  public Optional<TodoDynamoDb> getTodoById(String id) {
    return repository.findById(id);
  }
  
  @Override
  public TodoDynamoDb updateTodo(TodoDynamoDb todo) {
    repository.save(todo);
    return todo;
  }

  @Override
  public void deleteTodo(String id) {
    repository.deleteById(id);
  }
  
}
