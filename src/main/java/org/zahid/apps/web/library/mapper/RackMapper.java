package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
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
    @Mapping(target = "volumes", expression = "java(rack != null ? mapVolumeEntitiesToVolumeModels(rack.getVolumes()) : null)")
    public abstract RackModel fromRack(final RackEntity rack);

    @Mapping(target = "shelf", expression = "java(model != null && model.getShelf() != null ? shelfService.findById(model.getShelf()) : null)")
    @Mapping(target = "volumes", expression = "java(model != null ? mapVolumeModelsToVolumes(model.getVolumes()) : null)")
    public abstract RackEntity toRack(final RackModel model);

    public List<RackModel> mapRackEntitiesToRackModels(final List<RackEntity> racks) {
        if (CollectionUtils.isEmpty(racks)) {
            return new ArrayList<>();
        }
        final List<RackModel> models = new ArrayList<>();
        racks.forEach(rack -> {
            models.add(this.fromRack(rack));
        });
        return models;
    }

    public List<RackEntity> mapRackModelsToRacks(final List<RackModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<RackEntity> racks = new ArrayList<>();
        models.forEach(model -> {
            racks.add(this.toRack(model));
        });
        return racks;
    }

    public List<VolumeModel> mapVolumeEntitiesToVolumeModels(final List<VolumeEntity> volumes) {
        return volumeMapper.mapVolumeEntitiesToVolumeModels(volumes);
    }

    public List<VolumeEntity> mapVolumeModelsToVolumes(final List<VolumeModel> models) {
        return volumeMapper.mapVolumeModelsToVolumes(models);
    }
}
