package com.alabtaal.library.mapper.qualifier;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.service.BookTransHeaderService;
import com.alabtaal.library.service.ShelfService;

@Component
@Data
@RequiredArgsConstructor
public class RackQualifier {

  private ShelfService shelfService;

  private final VolumeMapper volumeMapper;

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
