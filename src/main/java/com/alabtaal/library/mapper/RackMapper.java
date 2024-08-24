package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.model.RackDetail;
import com.alabtaal.library.model.RackModel;
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
public interface RackMapper {

  @Mapping(target = "shelf", qualifiedByName = "shelfETM")
  @Mapping(target = "volumes", qualifiedByName = "volumesETM")
  RackModel toModel(final RackEntity rack);

  @Mapping(target = "shelf", qualifiedByName = "shelfMTE")
  @Mapping(target = "volumes", qualifiedByName = "volumesMTE")
  RackEntity toEntity(final RackModel model);

  @Mapping(target = "shelf", qualifiedByName = "shelfETM")
  @Mapping(target = "shelfName", source = "shelf", qualifiedByName = "shelfETD")
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
