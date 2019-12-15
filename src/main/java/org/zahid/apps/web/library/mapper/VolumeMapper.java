package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.BookService;
import org.zahid.apps.web.library.service.RackService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VolumeMapper {

    @Autowired
    protected BookService bookService;

    @Autowired
    protected RackService rackService;

    @Mapping(target = "book", expression = "java(volume != null && volume.getBook() != null ? volume.getBook().getBookId() : null)")
    @Mapping(target = "rack", expression = "java(volume != null && volume.getRack() != null ? volume.getRack().getRackId() : null)")
    public abstract VolumeModel fromVolume(final VolumeEntity volume);

    @Mapping(target = "book", expression = "java(model != null && model.getBook() != null ? bookService.findById(model.getBook()) : null)")
    @Mapping(target = "rack", expression = "java(model != null && model.getRack() != null ? rackService.findById(model.getRack()) : null)")
    public abstract VolumeEntity toVolume(final VolumeModel model);

    public List<VolumeModel> mapVolumeEntitiesToVolumeModels(final List<VolumeEntity> volumes) {
        if (CollectionUtils.isEmpty(volumes)) {
            return new ArrayList<>();
        }
        final List<VolumeModel> models = new ArrayList<>();
        volumes.forEach(volume -> {
            models.add(this.fromVolume(volume));
        });
        return models;
    }

    public List<VolumeEntity> mapVolumeModelsToVolumes(final List<VolumeModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<VolumeEntity> volumes = new ArrayList<>();
        models.forEach(model -> {
            volumes.add(this.toVolume(model));
        });
        return volumes;
    }
}
