package org.zahid.apps.web.library.mapper;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.mapper.qualifier.VolumeQualifier;
import org.zahid.apps.web.library.model.VolumeModel;

@Mapper(
    componentModel = "spring",
    uses = {VolumeQualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VolumeMapper {

    @Mapping(target = "book", expression = "java(volume != null && volume.getBook() != null ? volume.getBook().getBookId() : null)")
    @Mapping(target = "rack", expression = "java(volume != null && volume.getRack() != null ? volume.getRack().getRackId() : null)")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
    VolumeModel toModel(final VolumeEntity volume);

    @Mapping(target = "book", qualifiedByName = "book")
    @Mapping(target = "rack", qualifiedByName = "rack")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineEntities")
    VolumeEntity toEntity(final VolumeModel model);

    default List<VolumeModel> toModels(final List<VolumeEntity> volumes) {
        if (CollectionUtils.isEmpty(volumes)) {
            return new ArrayList<>();
        }
        final List<VolumeModel> models = new ArrayList<>();
        volumes.forEach(volume -> {
            models.add(this.toModel(volume));
        });
        return models;
    }

    default List<VolumeEntity> toEntities(final List<VolumeModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<VolumeEntity> volumes = new ArrayList<>();
        models.forEach(model -> {
            volumes.add(this.toEntity(model));
        });
        return volumes;
    }
}
