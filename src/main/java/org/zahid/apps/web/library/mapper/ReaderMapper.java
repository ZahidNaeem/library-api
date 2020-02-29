package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.ReaderEntity;
import org.zahid.apps.web.library.model.ReaderModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReaderMapper {

    @Autowired
    protected BookTransHeaderMapper bookTransHeaderMapper;

    @Mapping(target = "bookTransHeaders", expression = "java(reader != null ? bookTransHeaderMapper.toModels(reader.getBookTransHeaders()) : null)")
    public abstract ReaderModel toModel(final ReaderEntity reader);

    @Mapping(target = "bookTransHeaders", expression = "java(model != null ? bookTransHeaderMapper.toEntities(model.getBookTransHeaders()) : null)")
    public abstract ReaderEntity toEntity(final ReaderModel model);

    public List<ReaderModel> toModels(final List<ReaderEntity> readers) {
        if (CollectionUtils.isEmpty(readers)) {
            return new ArrayList<>();
        }
        final List<ReaderModel> models = new ArrayList<>();
        readers.forEach(reader -> {
            models.add(this.toModel(reader));
        });
        return models;
    }

    public List<ReaderEntity> toEntities(final List<ReaderModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ReaderEntity> readers = new ArrayList<>();
        models.forEach(model -> {
            readers.add(this.toEntity(model));
        });
        return readers;
    }
}
