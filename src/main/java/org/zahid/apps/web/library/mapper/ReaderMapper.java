package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.model.ReaderModel;
import org.zahid.apps.web.library.model.BookTransHeaderModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderMapper {

    @Autowired
    protected BookTransHeaderMapper bookTransHeaderMapper;

    @Mapping(target = "bookTransHeaders", expression = "java(reader != null ? toBookTransHeaderModels(reader.getBookTransHeaders()) : null)")
    public abstract ReaderModel toReaderModel(final ReaderEntity reader);

    @Mapping(target = "bookTransHeaders", expression = "java(model != null ? toBookTransHeaderEntities(model.getBookTransHeaders()) : null)")
    public abstract ReaderEntity toReaderEntity(final ReaderModel model);

    public List<BookTransHeaderModel> toBookTransHeaderModels(final List<BookTransHeaderEntity> bookTransHeaders) {
        return bookTransHeaderMapper.toBookTransHeaderModels(bookTransHeaders);
    }

    public List<BookTransHeaderEntity> toBookTransHeaderEntities(final List<BookTransHeaderModel> models) {
        return bookTransHeaderMapper.toBookTransHeaderEntities(models);
    }

    public List<ReaderModel> toReaderModels(final List<ReaderEntity> readers) {
        if (CollectionUtils.isEmpty(readers)) {
            return new ArrayList<>();
        }
        final List<ReaderModel> models = new ArrayList<>();
        readers.forEach(reader -> {
            models.add(this.toReaderModel(reader));
        });
        return models;
    }

    public List<ReaderEntity> toReaderEntities(final List<ReaderModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ReaderEntity> readers = new ArrayList<>();
        models.forEach(model -> {
            readers.add(this.toReaderEntity(model));
        });
        return readers;
    }
}
