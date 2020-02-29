package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.model.BookTransHeaderModel;
import org.zahid.apps.web.library.service.ReaderService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookTransHeaderMapper {

    @Autowired
    protected ReaderService readerService;

    @Autowired
    protected BookTransLineMapper bookTransLineMapper;

    @Mapping(target = "reader", expression = "java(bookTransHeader != null && bookTransHeader.getReader() != null ? bookTransHeader.getReader().getReaderId() : null)")
    @Mapping(target = "bookTransLines", expression = "java(bookTransHeader != null ? bookTransLineMapper.toModels(bookTransHeader.getBookTransLines()) : null)")
    public abstract BookTransHeaderModel toModel(final BookTransHeaderEntity bookTransHeader);

    @Mapping(target = "reader", expression = "java(model != null && model.getReader() != null ? readerService.findById(model.getReader()) : null)")
    @Mapping(target = "bookTransLines", expression = "java(model != null ? bookTransLineMapper.toEntities(model.getBookTransLines()) : null)")
    public abstract BookTransHeaderEntity toEntity(final BookTransHeaderModel model);

    public List<BookTransHeaderModel> toModels(final List<BookTransHeaderEntity> bookTransHeaders) {
        if (CollectionUtils.isEmpty(bookTransHeaders)) {
            return new ArrayList<>();
        }
        final List<BookTransHeaderModel> models = new ArrayList<>();
        bookTransHeaders.forEach(bookTransHeader -> {
            models.add(this.toModel(bookTransHeader));
        });
        return models;
    }

    public List<BookTransHeaderEntity> toEntities(final List<BookTransHeaderModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookTransHeaderEntity> bookTransHeaders = new ArrayList<>();
        models.forEach(model -> {
            bookTransHeaders.add(this.toEntity(model));
        });
        return bookTransHeaders;
    }
}
