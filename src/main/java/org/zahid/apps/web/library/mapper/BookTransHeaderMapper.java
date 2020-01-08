package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.model.BookTransHeaderModel;
import org.zahid.apps.web.library.model.BookTransLineModel;
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
    @Mapping(target = "bookTransLines", expression = "java(bookTransHeader != null ? toBookTransLineModels(bookTransHeader.getBookTransLines()) : null)")
    public abstract BookTransHeaderModel toBookTransHeaderModel(final BookTransHeaderEntity bookTransHeader);

    @Mapping(target = "reader", expression = "java(model != null && model.getReader() != null ? readerService.findById(model.getReader()) : null)")
    @Mapping(target = "bookTransLines", expression = "java(model != null ? toBookTransLineEntities(model.getBookTransLines()) : null)")
    public abstract BookTransHeaderEntity toBookTransHeaderEntity(final BookTransHeaderModel model);

    public List<BookTransLineModel> toBookTransLineModels(final List<BookTransLineEntity> bookTransLines) {
        return bookTransLineMapper.toBookTransLineModels(bookTransLines);
    }

    public List<BookTransLineEntity> toBookTransLineEntities(final List<BookTransLineModel> models) {
        return bookTransLineMapper.toBookTransLineEntities(models);
    }

    public List<BookTransHeaderModel> toBookTransHeaderModels(final List<BookTransHeaderEntity> bookTransHeaders) {
        if (CollectionUtils.isEmpty(bookTransHeaders)) {
            return new ArrayList<>();
        }
        final List<BookTransHeaderModel> models = new ArrayList<>();
        bookTransHeaders.forEach(bookTransHeader -> {
            models.add(this.toBookTransHeaderModel(bookTransHeader));
        });
        return models;
    }

    public List<BookTransHeaderEntity> toBookTransHeaderEntities(final List<BookTransHeaderModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookTransHeaderEntity> bookTransHeaders = new ArrayList<>();
        models.forEach(model -> {
            bookTransHeaders.add(this.toBookTransHeaderEntity(model));
        });
        return bookTransHeaders;
    }
}
