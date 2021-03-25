package io.twodigits.todo.service;

import java.util.Optional;

import io.twodigits.todo.model.TodoDynamoDb;

public interface TodoService {

  /**
   * Standard CRUD operations with the respective parameters
   */
  TodoDynamoDb addTodo(String text);
  Iterable<TodoDynamoDb> getAllTodos();
  Optional<TodoDynamoDb> getTodoById(String id);
  TodoDynamoDb updateTodo(TodoDynamoDb todo);
  void deleteTodo(String id);
}
