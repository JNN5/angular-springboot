package io.twodigits.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.twodigits.todo.model.TodoDynamoDb;
import io.twodigits.todo.service.TodoService;

@RestController
public class TodoController {

  @Autowired
  private TodoService todoService;
  
  @PostMapping("/api/todo")
  public TodoDynamoDb addTodo(@RequestBody String text) {
    return todoService.addTodo(text);
  }

  @GetMapping("/api/todos")
  public Iterable<TodoDynamoDb> getAllTodos() {
    return todoService.getAllTodos();
  }

  @GetMapping("/api/todo/{id}")
  public Optional<TodoDynamoDb> getTodoById(@PathVariable String id) {
    return todoService.getTodoById(id);
  }

  //@GetMapping("api/todos/") // TBD with Query

  @PutMapping("/api/todo/{id}")
  public TodoDynamoDb updateTodo(@PathVariable String id, @RequestBody TodoDynamoDb todo) {
    return todoService.updateTodo(todo);
  }

  @DeleteMapping("/api/todo/{id}")
  public void deleteTodo(@PathVariable String id) {
    todoService.deleteTodo(id);
  }
}
