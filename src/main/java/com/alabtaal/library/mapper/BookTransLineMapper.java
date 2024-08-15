package com.alabtaal.library.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.mapper.qualifier.BookTransLineQualifier;
import com.alabtaal.library.model.BookTransLineModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {BookTransLineQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookTransLineMapper {

    @Mapping(target = "bookTransHeader", expression = "java(bookTransLine != null && bookTransLine.getBookTransHeader() != null ? bookTransLine.getBookTransHeader().getHeaderId() : null)")
    @Mapping(target = "book", expression = "java(bookTransLine != null && bookTransLine.getBook() != null ? bookTransLine.getBook().getBookId() : null)")
    @Mapping(target = "volume", expression = "java(bookTransLine != null && bookTransLine.getVolume() != null ? bookTransLine.getVolume().getVolumeId() : null)")
    BookTransLineModel toModel(final BookTransLineEntity bookTransLine);

    @Mapping(target = "bookTransHeader", qualifiedByName = "bookTransHeader")
    @Mapping(target = "book", qualifiedByName = "book")
    @Mapping(target = "volume", qualifiedByName = "volume")
    BookTransLineEntity toEntity(final BookTransLineModel model);

    default List<BookTransLineModel> toModels(final List<BookTransLineEntity> bookTransLines) {
        if (CollectionUtils.isEmpty(bookTransLines)) {
            return new ArrayList<>();
        }
        final List<BookTransLineModel> models = new ArrayList<>();
        bookTransLines.forEach(bookTransLine -> {
            models.add(this.toModel(bookTransLine));
        });
        return models;
    }

    default List<BookTransLineEntity> toEntities(final List<BookTransLineModel> models) {
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
