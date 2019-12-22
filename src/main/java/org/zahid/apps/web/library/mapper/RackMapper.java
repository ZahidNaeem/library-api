package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.model.RackDetail;
import org.zahid.apps.web.library.model.RackModel;
import org.zahid.apps.web.library.model.VolumeModel;
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
    @Mapping(target = "volumes", expression = "java(rack != null ? toVolumeModels(rack.getVolumes()) : null)")
    public abstract RackModel toRackModel(final RackEntity rack);

    @Mapping(target = "shelf", expression = "java(model != null && model.getShelf() != null ? shelfService.findById(model.getShelf()) : null)")
    @Mapping(target = "volumes", expression = "java(model != null ? toVolumeEntities(model.getVolumes()) : null)")
    public abstract RackEntity toRackEntity(final RackModel model);

    @Mapping(target = "shelfId", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfId() : null)")
    @Mapping(target = "shelfName", expression = "java(rack != null && rack.getShelf() != null ? rack.getShelf().getShelfName() : null)")
    public abstract RackDetail toRackDetail(final RackEntity rack);

    public List<RackModel> toRackModels(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackModel> models = new ArrayList<>();
        racks.forEach(rack -> {
            models.add(this.toRackModel(rack));
        });
        return models;
    }

    public List<RackEntity> toRackEntities(final List<RackModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<RackEntity> racks = new ArrayList<>();
        models.forEach(model -> {
            racks.add(this.toRackEntity(model));
        });
        return racks;
    }

    public List<RackDetail> toRackDetails(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackDetail> details = new ArrayList<>();
        racks.forEach(rack -> {
            details.add(this.toRackDetail(rack));
        });
        return details;
    }

    public List<VolumeModel> toVolumeModels(final List<VolumeEntity> volumes) {
        return volumeMapper.toVolumeModels(volumes);
    }

    public List<VolumeEntity> toVolumeEntities(final List<VolumeModel> models) {
        return volumeMapper.toVolumeEntities(models);
    }
}
