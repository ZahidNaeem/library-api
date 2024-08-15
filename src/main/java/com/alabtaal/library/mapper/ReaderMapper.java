package com.alabtaal.library.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.mapper.qualifier.ReaderQualifier;
import com.alabtaal.library.model.ReaderModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ReaderQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ReaderMapper {

    @Mapping(target = "bookTransHeaders", qualifiedByName = "bookTransHeaderModels")
    ReaderModel toModel(final ReaderEntity reader);

    @Mapping(target = "bookTransHeaders", qualifiedByName = "bookTransHeaderEntities")
    ReaderEntity toEntity(final ReaderModel model);

    default List<ReaderModel> toModels(final List<ReaderEntity> readers) {
        if (CollectionUtils.isEmpty(readers)) {
            return new ArrayList<>();
        }
        final List<ReaderModel> models = new ArrayList<>();
        readers.forEach(reader -> {
            models.add(this.toModel(reader));
        });
        return models;
    }

    default List<ReaderEntity> toEntities(final List<ReaderModel> models) {
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
