package org.zahid.apps.web.library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.payload.response.SearchVolumeResponse;
import org.zahid.apps.web.library.service.VolumeService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("volumes")
public class VolumeController {

    private static final Logger LOG = LogManager.getLogger(VolumeController.class);

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private VolumeMapper mapper;

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
    public ResponseEntity<ApiResponse<VolumeModel>> findById(@PathVariable("id") final Long id) {
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
    public ResponseEntity<ApiResponse<List<SearchVolumeResponse>>> findByBook(@PathVariable("bookId") final Long bookId) {
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
    public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable("id") final Long id) {
        if (!volumeService.exists(id)) {
            throw new IllegalArgumentException("Item volume with id: " + id + " does not exist");
        } else {
            try {
                volumeService.deleteById(id);
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("Volume deleted seccessfully")
                                .entity(true)
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Boolean>> delete(@RequestBody final VolumeModel model) {
        if (null == model || null == model.getVolumeId() || !volumeService
                .exists(model.getVolumeId())) {
            throw new IllegalArgumentException("Item volume does not exist");
        } else {
            try {
                volumeService.delete(mapper.toEntity(model));
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("Volume deleted seccessfully")
                                .entity(true)
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }
}
