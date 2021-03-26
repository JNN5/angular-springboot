package io.twodigits.todo;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

  public static <T> T retrieveResourceFromResponse(final ResponseEntity<String> response, final Class<T> clazz) throws IOException {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(response.getBody(), clazz);
  }
}
