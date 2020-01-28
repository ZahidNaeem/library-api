package org.zahid.apps.web.library.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse<T> {
    private int code;
    private String message;
    private ResponseEntity<T> entity;
}
