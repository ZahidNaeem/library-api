package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.model.BookTransLineModel;
import org.zahid.apps.web.library.service.BookService;
import org.zahid.apps.web.library.service.BookTransHeaderService;
import org.zahid.apps.web.library.service.VolumeService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookTransLineMapper {

    @Autowired
    protected BookTransHeaderService bookTransHeaderService;

    @Autowired
    protected BookService bookService;

    @Autowired
    protected VolumeService volumeService;

    @Mapping(target = "bookTransHeader", expression = "java(bookTransLine != null && bookTransLine.getBookTransHeader() != null ? bookTransLine.getBookTransHeader().getHeaderId() : null)")
    @Mapping(target = "book", expression = "java(bookTransLine != null && bookTransLine.getBook() != null ? bookTransLine.getBook().getBookId() : null)")
    @Mapping(target = "volume", expression = "java(bookTransLine != null && bookTransLine.getVolume() != null ? bookTransLine.getVolume().getVolumeId() : null)")
    public abstract BookTransLineModel toModel(final BookTransLineEntity bookTransLine);

    @Mapping(target = "bookTransHeader", expression = "java(model != null && model.getBookTransHeader() != null ? bookTransHeaderService.findById(model.getBookTransHeader()) : null)")
    @Mapping(target = "book", expression = "java(model != null && model.getBook() != null ? bookService.findById(model.getBook()) : null)")
    @Mapping(target = "volume", expression = "java(model != null && model.getVolume() != null ? volumeService.findById(model.getVolume()) : null)")
    public abstract BookTransLineEntity toEntity(final BookTransLineModel model);

    public List<BookTransLineModel> toModels(final List<BookTransLineEntity> bookTransLines) {
        if (CollectionUtils.isEmpty(bookTransLines)) {
            return new ArrayList<>();
        }
        final List<BookTransLineModel> models = new ArrayList<>();
        bookTransLines.forEach(bookTransLine -> {
            models.add(this.toModel(bookTransLine));
        });
        return models;
    }

    public List<BookTransLineEntity> toEntities(final List<BookTransLineModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookTransLineEntity> bookTransLines = new ArrayList<>();
        models.forEach(model -> {
            bookTransLines.add(this.toEntity(model));
        });
        return bookTransLines;
    }
}
