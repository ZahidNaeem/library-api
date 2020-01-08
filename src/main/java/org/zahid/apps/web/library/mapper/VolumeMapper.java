package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.model.BookTransLineModel;
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

    @Autowired
    protected BookTransLineMapper bookTransLineMapper;

    @Mapping(target = "book", expression = "java(volume != null && volume.getBook() != null ? volume.getBook().getBookId() : null)")
    @Mapping(target = "rack", expression = "java(volume != null && volume.getRack() != null ? volume.getRack().getRackId() : null)")
    @Mapping(target = "bookTransLines", expression = "java(volume != null ? toBookTransLineModels(volume.getBookTransLines()) : null)")
    public abstract VolumeModel toVolumeModel(final VolumeEntity volume);

    @Mapping(target = "book", expression = "java(model != null && model.getBook() != null ? bookService.findById(model.getBook()) : null)")
    @Mapping(target = "rack", expression = "java(model != null && model.getRack() != null ? rackService.findById(model.getRack()) : null)")
    @Mapping(target = "bookTransLines", expression = "java(model != null ? toBookTransLineEntities(model.getBookTransLines()) : null)")
    public abstract VolumeEntity toVolumeEntity(final VolumeModel model);

    public List<VolumeModel> toVolumeModels(final List<VolumeEntity> volumes) {
        if (CollectionUtils.isEmpty(volumes)) {
            return new ArrayList<>();
        }
        final List<VolumeModel> models = new ArrayList<>();
        volumes.forEach(volume -> {
            models.add(this.toVolumeModel(volume));
        });
        return models;
    }

    public List<VolumeEntity> toVolumeEntities(final List<VolumeModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<VolumeEntity> volumes = new ArrayList<>();
        models.forEach(model -> {
            volumes.add(this.toVolumeEntity(model));
        });
        return volumes;
    }

    public List<BookTransLineModel> toBookTransLineModels(final List<BookTransLineEntity> bookTransLines) {
        return bookTransLineMapper.toBookTransLineModels(bookTransLines);
    }

    public List<BookTransLineEntity> toBookTransLineEntities(final List<BookTransLineModel> models) {
        return bookTransLineMapper.toBookTransLineEntities(models);
    }
}
