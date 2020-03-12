package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.mapper.qualifier.BookTransHeaderQualifier;
import org.zahid.apps.web.library.model.BookTransHeaderModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {BookTransHeaderQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookTransHeaderMapper {

    @Mapping(target = "reader", expression = "java(bookTransHeader != null && bookTransHeader.getReader() != null ? bookTransHeader.getReader().getReaderId() : null)")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
    BookTransHeaderModel toModel(final BookTransHeaderEntity bookTransHeader);

    @Mapping(target = "reader", qualifiedByName = "reader")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineEntities")
    BookTransHeaderEntity toEntity(final BookTransHeaderModel model);

    default List<BookTransHeaderModel> toModels(final List<BookTransHeaderEntity> bookTransHeaders) {
        if (CollectionUtils.isEmpty(bookTransHeaders)) {
            return new ArrayList<>();
        }
        final List<BookTransHeaderModel> models = new ArrayList<>();
        bookTransHeaders.forEach(bookTransHeader -> {
            models.add(this.toModel(bookTransHeader));
        });
        return models;
    }

    default List<BookTransHeaderEntity> toEntities(final List<BookTransHeaderModel> models) {
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
