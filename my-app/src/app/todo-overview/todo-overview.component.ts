import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TodoService } from './../todo.service';
import { Todo } from './../todo';

@Component({
  selector: 'app-todo-overview',
  templateUrl: './todo-overview.component.html',
  styleUrls: ['./todo-overview.component.css'],
})
export class TodoOverviewComponent implements OnInit {
  loading$: Observable<boolean>;
  todos$: Observable<Todo[]>;
  // todos: Todo[] = [];

  constructor(private todoService: TodoService) {
    this.todos$ = todoService.entities$;
    this.loading$ = todoService.loading$;
  }

  ngOnInit(): void {
    this.getTodos();
  }

  handleEvent(newState: string, todo: Todo): void {
    if (newState === 'done') {
      const updatedTodo: Todo = {
        id: todo.id,
        text: todo.text,
        done: true,
      };
      this.update(updatedTodo);
    }
    if (newState === 'delete') {
      this.delete(todo);
    }
  }

  add(todo: Todo) {
    this.todoService.add(todo);
  }

  delete(todo: Todo) {
    this.todoService.delete(todo.id);
  }

  getTodos() {
    this.todoService.getAll();
  }

  update(todo: Todo) {
    this.todoService.update(todo);
  }
}
