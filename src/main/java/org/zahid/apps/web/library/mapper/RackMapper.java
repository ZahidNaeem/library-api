package org.zahid.apps.web.library.mapper;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.mapper.qualifier.RackQualifier;
import org.zahid.apps.web.library.model.RackDetail;
import org.zahid.apps.web.library.model.RackModel;

@Mapper(
    componentModel = "spring",
    uses = {RackQualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RackMapper {

    @Mapping(target = "shelf", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfId() : null)")
    @Mapping(target = "volumes", qualifiedByName = "volumeModels")
    RackModel toModel(final RackEntity rack);

    @Mapping(target = "shelf", qualifiedByName = "shelf")
    @Mapping(target = "volumes", qualifiedByName = "volumeEntities")
    RackEntity toEntity(final RackModel model);

    @Mapping(target = "shelfId", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfId() : null)")
    @Mapping(target = "shelfName", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfName() : null)")
    RackDetail toDetail(final RackEntity rack);

    default List<RackModel> toModels(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackModel> models = new ArrayList<>();
        racks.forEach(rack -> {
            models.add(this.toModel(rack));
        });
        return models;
    }

    default List<RackEntity> toEntities(final List<RackModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<RackEntity> racks = new ArrayList<>();
        models.forEach(model -> {
            racks.add(this.toEntity(model));
        });
        return racks;
    }

    default List<RackDetail> toDetails(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackDetail> details = new ArrayList<>();
        racks.forEach(rack -> {
            details.add(this.toDetail(rack));
        });
        return details;
    }
}
