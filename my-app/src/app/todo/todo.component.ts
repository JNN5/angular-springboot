import { TodoService } from './../todo.service';
import { Todo } from './../todo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css'],
})
export class TodoComponent implements OnInit {
  @Input() todo!: Todo;
  @Output() todoEvent = new EventEmitter<string>();

  constructor(private todoService: TodoService) {}

  ngOnInit(): void {}

  setStateToDone(): void {
    this.todoEvent.emit('done');
  }

  deleteTodo(): void {
    this.todoEvent.emit('delete');
  }
}
