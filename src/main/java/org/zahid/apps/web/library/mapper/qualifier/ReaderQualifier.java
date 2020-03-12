package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.mapper.BookTransHeaderMapper;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.BookTransHeaderModel;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ReaderQualifier {

    private final BookTransHeaderMapper bookTransHeaderMapper;

    @Named("bookTransHeaderModels")
    public List<BookTransHeaderModel> bookTransHeaderModels(final List<BookTransHeaderEntity> entities) {
        return bookTransHeaderMapper.toModels(entities);
    }

    @Named("bookTransHeaderEntities")
    public List<BookTransHeaderEntity> bookTransHeaderEntities(final List<BookTransHeaderModel> models) {
        return bookTransHeaderMapper.toEntities(models);
    }
}
