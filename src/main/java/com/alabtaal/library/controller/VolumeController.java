package com.alabtaal.library.controller;

import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.payload.response.SearchVolumeResponse;
import com.alabtaal.library.service.VolumeService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("volumes")
@RequiredArgsConstructor
public class VolumeController {

  private static final Logger LOG = LoggerFactory.getLogger(VolumeController.class);

  private final VolumeService volumeService;

  private final VolumeMapper mapper;

//    @Autowired
//    private BookService bookService;

  private List<VolumeModel> volumeModels = new ArrayList<>();

  @PostConstruct
  public void init() {
    volumeModels = mapper.toModels(volumeService.findAll());
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<VolumeModel>>> findAll() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<VolumeModel>>builder()
            .success(true)
            .message("findAll response")
            .entity(volumeModels)
            .build()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<VolumeModel>> findById(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<VolumeModel>builder()
            .success(true)
            .message("findById response")
            .entity(mapper.toModel(volumeService.findById(id)))
            .build()
    );
  }

  @GetMapping("resp/all")
  public ResponseEntity<ApiResponse<List<SearchVolumeResponse>>> findAllSearchResponses() {
    return ResponseEntity.ok(
        ApiResponse
            .<List<SearchVolumeResponse>>builder()
            .success(true)
            .message("findAllSearchResponses response")
            .entity(volumeService.findAllSearchResponses())
            .build()
    );
  }

  @GetMapping("book/{bookId}")
  public ResponseEntity<ApiResponse<List<SearchVolumeResponse>>> findByBook(@PathVariable("bookId") final UUID bookId) {
    return ResponseEntity.ok(
        ApiResponse
            .<List<SearchVolumeResponse>>builder()
            .success(true)
            .message("findAllSearchResponses response")
            .entity(volumeService.findAllSearchResponses())
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<VolumeModel>> save(@RequestBody final VolumeModel model) {
    final VolumeEntity savedVolume = volumeService.save(mapper.toEntity(model));
    return ResponseEntity.ok(
        ApiResponse
            .<VolumeModel>builder()
            .success(true)
            .message("Volume saved seccessfully")
            .entity(mapper.toModel(savedVolume))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<VolumeEntity>>> saveAll(@RequestBody final Set<VolumeModel> volumeModel) {
    final Set<VolumeEntity> volumes = new HashSet<>();
    volumeModel.forEach(model -> {
      final VolumeEntity volume = mapper.toEntity(model);
      volumes.add(volume);
    });
    return ResponseEntity.ok(
        ApiResponse
            .<List<VolumeEntity>>builder()
            .success(true)
            .message("All volumes saved seccessfully")
            .entity(volumeService.save(volumes))
            .build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable("id") final UUID id) {
    if (!volumeService.exists(id)) {
      throw new IllegalArgumentException("Item volume with id: " + id + " does not exist");
    } else {
      volumeService.deleteById(id);
      return ResponseEntity.ok(
          ApiResponse
              .<Boolean>builder()
              .success(true)
              .message("Volume deleted seccessfully")
              .entity(true)
              .build()
      );
    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<Boolean>> delete(@RequestBody final VolumeModel model) {
    if (null == model || null == model.getId() || !volumeService.exists(model.getId())) {
      throw new IllegalArgumentException("Item volume does not exist");
    } else {
      volumeService.delete(mapper.toEntity(model));
      return ResponseEntity.ok(
          ApiResponse
              .<Boolean>builder()
              .success(true)
              .message("Volume deleted seccessfully")
              .entity(true)
              .build()
      );
    }
  }
}
