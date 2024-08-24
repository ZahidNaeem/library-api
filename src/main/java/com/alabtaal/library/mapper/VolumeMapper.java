package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.VolumeModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {Qualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VolumeMapper {

  @Mapping(target = "book", qualifiedByName = "bookETM")
  @Mapping(target = "rack", qualifiedByName = "rackETM")
  @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
  VolumeModel toModel(final VolumeEntity volume);

  @Mapping(target = "book", qualifiedByName = "bookMTE")
  @Mapping(target = "rack", qualifiedByName = "rackMTE")
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
