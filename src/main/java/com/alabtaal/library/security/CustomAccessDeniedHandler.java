package com.alabtaal.library.security;

import com.alabtaal.library.payload.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    String user = "User";

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      user = authentication.getName();
    }

    String errorMessage =
        user + ", You are not authorized to access protected URL: '" + request.getRequestURI() + "' with method: " + request.getMethod();

    ApiResponse<String> apiResponse = ApiResponse
        .<String>builder()
        .success(false)
        .entity(null)
        .message(errorMessage)
        .build();

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    final ServletOutputStream outputStream = response.getOutputStream();
    //      String customJsonString = toJSONString(apiResponse).toString();
    final String jsonString = objectMapper.writeValueAsString(apiResponse);
    outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
    outputStream.flush();
  }

//  private static JSONObject toJSONString(ApiResponse<String> apiResponse) throws JSONException {
//    String result = "{}";
//    StringBuilder sb = new StringBuilder();
//    final Field[] declaredFields = apiResponse.getClass().getDeclaredFields();
//    Arrays
//        .stream(declaredFields)
//        .forEach(field -> {
//          field.setAccessible(true);
//          try {
//            sb.append(",").append(field.getName()).append(":").append(field.getName().equals("message") ? "'" + field.get(apiResponse) + "'" : field.get(apiResponse));
//          } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//          }
//        });
//    if (sb.toString().length() > 1) {
//      result = sb.substring(1);
//      result = "{" + result + "}";
//      System.out.println(result);
//    }
//    return new JSONObject(result);
//  }
}
