package io.twodigits.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
  
  @CrossOrigin
  @PostMapping("/api/todo")
  public TodoDynamoDb addTodo(@RequestBody TodoDynamoDb todo) {
    return todoService.addTodo(todo.getText());
  }

  @CrossOrigin
  @GetMapping("/api/todos")
  public Iterable<TodoDynamoDb> getAllTodos() {
    return todoService.getAllTodos();
  }

  @CrossOrigin
  @GetMapping("/api/todo/{id}")
  public Optional<TodoDynamoDb> getTodoById(@PathVariable String id) {
    return todoService.getTodoById(id);
  }

  //@GetMapping("api/todos/") // TBD with Query  
  @CrossOrigin
  @PutMapping("/api/todo/{id}")
  public TodoDynamoDb updateTodo(@PathVariable String id, @RequestBody TodoDynamoDb todo) throws Exception {
    return todoService.updateTodo(todo);
  }

  @CrossOrigin
  @DeleteMapping("/api/todo/{id}")
  public void deleteTodo(@PathVariable String id) {
    todoService.deleteTodo(id);
  }
}
