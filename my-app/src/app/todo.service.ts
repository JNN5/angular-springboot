import { TODOS } from './mock-todos';
import { Todo } from './todo';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  constructor() {}

  getTodos(): Todo[] {
    return TODOS;
  }
}
