package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.model.RackDetail;
import org.zahid.apps.web.library.model.RackModel;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RackMapper {

    @Autowired
    protected ShelfService shelfService;

    @Autowired
    protected VolumeMapper volumeMapper;

    @Mapping(target = "shelf", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfId() : null)")
    @Mapping(target = "volumes", expression = "java(rack != null ? volumeMapper.toModels(rack.getVolumes()) : null)")
    public abstract RackModel toModel(final RackEntity rack);

    @Mapping(target = "shelf", expression = "java(model != null && model.getShelf() != null ? shelfService.findById(model.getShelf()) : null)")
    @Mapping(target = "volumes", expression = "java(model != null ? volumeMapper.toEntities(model.getVolumes()) : null)")
    public abstract RackEntity toEntity(final RackModel model);

    @Mapping(target = "shelfId", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfId() : null)")
    @Mapping(target = "shelfName", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfName() : null)")
    public abstract RackDetail toDetail(final RackEntity rack);

    public List<RackModel> toModels(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackModel> models = new ArrayList<>();
        racks.forEach(rack -> {
            models.add(this.toModel(rack));
        });
        return models;
    }

    public List<RackEntity> toEntities(final List<RackModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<RackEntity> racks = new ArrayList<>();
        models.forEach(model -> {
            racks.add(this.toEntity(model));
        });
        return racks;
    }

    public List<RackDetail> toDetails(final List<RackEntity> racks) {
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
