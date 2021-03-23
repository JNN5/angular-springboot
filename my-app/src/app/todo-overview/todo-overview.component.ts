import { Component, OnInit } from '@angular/core';
import { TodoService } from './../todo.service';
import { Todo } from './../todo';

@Component({
  selector: 'app-todo-overview',
  templateUrl: './todo-overview.component.html',
  styleUrls: ['./todo-overview.component.css'],
})
export class TodoOverviewComponent implements OnInit {
  todos: Todo[] = [];

  constructor(private todoService: TodoService) {}

  ngOnInit(): void {
    this.getTodos();
  }

  getTodos(): void {
    this.todos = this.todoService.getTodos();
  }

  handleEvent(newState: string, todo: Todo): void {
    if (newState === 'done') {
      this.setStateToDone(todo);
    }
    if (newState === 'delete') {
      this.deleteTodo(todo);
    }
  }

  setStateToDone(todo: Todo): void {
    todo.state = 'done';
    this.todos = [...this.todos.filter((t) => t.id !== todo.id), todo];
  }

  deleteTodo(todo: Todo): void {
    this.todos = [...this.todos.filter((t) => t.id !== todo.id)];
  }
}
