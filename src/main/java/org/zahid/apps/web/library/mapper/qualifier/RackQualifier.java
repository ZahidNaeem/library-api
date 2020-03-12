package org.zahid.apps.web.library.mapper.qualifier;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.BookTransHeaderService;
import org.zahid.apps.web.library.service.ShelfService;

@Component
@Data
@RequiredArgsConstructor
public class RackQualifier {

  private ShelfService shelfService;

  private VolumeMapper volumeMapper;

  @Named("shelf")
  public ShelfEntity shelf(final Long shelf) {
    return shelf != null ? shelfService.findById(shelf) : null;
  }

  @Named("volumeModels")
  public List<VolumeModel> volumeModels(final List<VolumeEntity> volumes) {
    return volumeMapper.toModels(volumes);
  }

  @Named("volumeEntities")
  public List<VolumeEntity> volume(final List<VolumeModel> volumes) {
    return volumeMapper.toEntities(volumes);
  }
}
