package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.mapper.BookTransHeaderMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.BookTransHeaderModel;

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
