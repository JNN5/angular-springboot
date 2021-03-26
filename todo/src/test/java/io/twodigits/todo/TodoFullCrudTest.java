package io.twodigits.todo;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import io.twodigits.todo.model.TodoDynamoDb;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {""})
public class TodoFullCrudTest {
  
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

	@Test
  @SuppressWarnings("squid:S2699") // Sonar doesn't recognize Springs Assert class
	public void todoFullCrudTest() throws Exception {

    String firstTodoText = "first todo";
		TodoDynamoDb firstTodo = this.createTodo(firstTodoText);
    Assert.isTrue(firstTodo.getText().equals(firstTodoText), "the returned todo from the create request contains the same text that was submitted");
    Assert.notNull(firstTodo.getId(), "the returned todo contains an id");

    TodoDynamoDb fristTodoFromServer = this.getTodo(firstTodo.getId());
    Assert.isTrue(fristTodoFromServer.getText().equals(firstTodoText), "the returned todo from the get request contains the same text that was submitted");

    String secondTodoText = "second todo";
		TodoDynamoDb secondTodo = this.createTodo(secondTodoText);
    Assert.isTrue(secondTodo.getText().equals(secondTodoText), "the returned todo from the create request contains the same text that was submitted");

    String firstTodoUpdatedText = "updated todo";
    Boolean done = true;
    TodoDynamoDb updatedFirstTodo = this.updateTodo(new TodoDynamoDb(firstTodo.getId(), firstTodoUpdatedText, done));
    Assert.isTrue(updatedFirstTodo.getId().equals(firstTodo.getId()), "the returned todo from the update request has the same Id that was submitted");
    Assert.isTrue(updatedFirstTodo.getText().equals(firstTodoUpdatedText), "the returned todo from the update request contains the same text that was submitted");
    Assert.isTrue(updatedFirstTodo.getDone().equals(done), "the returned todo from the update request contains the same done value that was submitted");

    TodoDynamoDb updatedTodoFromServer = this.getTodo(updatedFirstTodo.getId());
    Assert.isTrue(updatedTodoFromServer.toString().equals(updatedFirstTodo.toString()), "the returned todo from the update request is the same that was submitted");

    String getTodosString = this.getTodos();
    Assert.isTrue(getTodosString.contains(firstTodo.getId()), "Find id of first todo in getTodos reponse");
    Assert.isTrue(getTodosString.contains(secondTodo.getId()), "Find id of second todo in getTodos reponse");

    this.deleteTodo(secondTodo.getId());

    String getTodosStringAfterDelete = this.getTodos();
    Assert.isTrue(getTodosStringAfterDelete.contains(firstTodo.getId()), "Find id of first todo in getTodos reponse");
    Assert.doesNotContain(getTodosStringAfterDelete, secondTodo.getId(), "Second todo was successfully deleted");

    this.deleteTodo(firstTodo.getId());
	}

  public ResponseEntity<String> callRestAPIandAssertReponseIsOkay (String path, HttpMethod method, String payload) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> request = new HttpEntity<String>(payload.toString(), headers);
    System.out.println("request: " + request);
		ResponseEntity<String> response =  restTemplate.exchange("http://localhost:" + port + path, method, request, String.class);
    System.out.println("raw response Body: " + response.getBody());
    Assert.notNull(response, "The response shouldn't be null");
		Assert.isTrue(response.getStatusCode().value() == HttpStatus.OK.value(), "The http status code is 200");
    return response;
  }

  public TodoDynamoDb createTodo(String text) throws IOException {
    String payload = "{\"text\": \""+ text + "\"}";
    ResponseEntity<String> response = this.callRestAPIandAssertReponseIsOkay("/api/todo", HttpMethod.POST, payload);
    return Utils.retrieveResourceFromResponse(response, TodoDynamoDb.class);
  }

  public TodoDynamoDb getTodo(String id) throws IOException {
    String payload = "{}";
    ResponseEntity<String> response = this.callRestAPIandAssertReponseIsOkay("/api/todo/" + id, HttpMethod.GET, payload);
    return Utils.retrieveResourceFromResponse(response, TodoDynamoDb.class);
  }

  public String getTodos() throws IOException {
    String payload = "{}";
    ResponseEntity<String> response = this.callRestAPIandAssertReponseIsOkay("/api/todos", HttpMethod.GET, payload);
    return response.getBody();
  }

  public TodoDynamoDb updateTodo(TodoDynamoDb todo) throws IOException {
    String payload = "{" + "\"id\": \""+ todo.getId() + "\"," + "\"text\": \""+ todo.getText() + "\"," + "\"done\": \""+ todo.getDone() + "\"" + "}";
    ResponseEntity<String> response = this.callRestAPIandAssertReponseIsOkay("/api/todo/" + todo.getId(), HttpMethod.PUT, payload);
    return Utils.retrieveResourceFromResponse(response, TodoDynamoDb.class);
  }

  public void deleteTodo(String id) throws IOException {
    String payload = "{}";
    this.callRestAPIandAssertReponseIsOkay("/api/todo/" + id, HttpMethod.DELETE, payload);
  }
}
