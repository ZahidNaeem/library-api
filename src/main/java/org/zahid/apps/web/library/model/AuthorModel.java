package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.Auditable;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel extends Auditable<Long> {

    private Long authorId;

    private String authorName;

    private String remarks;

    private List<BookModel> books;
}
