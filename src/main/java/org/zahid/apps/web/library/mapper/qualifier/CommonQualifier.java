package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.model.BookModel;

import java.util.List;

@Component
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CommonQualifier {

    private final BookMapper bookMapper;

    @Named("bookModels")
    public List<BookModel> bookModels(final List<BookEntity> books) {
        return bookMapper.toModels(books);
    }

    @Named("bookEntities")
    public List<BookEntity> bookEntities(final List<BookModel> books) {
        return bookMapper.toEntities(books);
    }
}
