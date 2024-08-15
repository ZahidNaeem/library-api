package com.alabtaal.library.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.exception.InternalServerErrorException;
import com.alabtaal.library.mapper.RackMapper;
import com.alabtaal.library.model.RackDetail;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.RackService;
import com.alabtaal.library.service.ShelfService;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("racks")
@RequiredArgsConstructor
public class RackController {

    private static final Logger LOG = LogManager.getLogger(RackController.class);

    private final RackService rackService;

    private final RackMapper mapper;

    private final ShelfService shelfService;

    private static void setRackForVolumes(final RackEntity rack) {
        if (CollectionUtils.isNotEmpty(rack.getVolumes())) {
            rack.getVolumes().forEach(volume -> {
                volume.setRack(rack);
            });
        }
    }

    private List<RackModel> rackModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        rackModels = mapper.toModels(rackService.findAll());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RackModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<RackModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(rackModels)
                        .build()
        );
    }

    @GetMapping("details")
    public ResponseEntity<ApiResponse<List<RackDetail>>> findAllDetails() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<RackDetail>>builder()
                        .success(true)
                        .message("findAllDetails response")
                        .entity(mapper.toDetails(rackService.findAll()))
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<RackModel>> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<RackModel>builder()
                        .success(true)
                        .message("findById response")
                        .entity(mapper.toModel(rackService.findById(id)))
                        .build()
        );
    }

    @GetMapping("shelf/{shelfId}")
    public ResponseEntity<ApiResponse<List<RackModel>>> findByShelf(@PathVariable("shelfId") final Long shelfId) {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<RackModel>>builder()
                        .success(true)
                        .message("findByShelf response")
                        .entity(mapper.toModels(rackService.findAllByShelf(shelfService.findById(shelfId))))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<RackModel>> save(@RequestBody final RackModel model) {
        final RackEntity rack = mapper.toEntity(model);
        final RackEntity savedRack = rackService.save(rack);
        setRackForVolumes(rack);
        return ResponseEntity.ok(
                ApiResponse
                        .<RackModel>builder()
                        .success(true)
                        .message("Rack saved seccessfully")
                        .entity(mapper.toModel(savedRack))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RackEntity>>> saveAll(@RequestBody final Set<RackModel> rackModel) {
        final Set<RackEntity> racks = new HashSet<>();
        rackModel.forEach(model -> {
            final RackEntity rack = mapper.toEntity(model);
            setRackForVolumes(rack);
            racks.add(rack);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<RackEntity>>builder()
                        .success(true)
                        .message("All racks saved seccessfully")
                        .entity(rackService.save(racks))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable("id") final Long id) {
        if (!rackService.exists(id)) {
            throw new IllegalArgumentException("Item rack with id: " + id + " does not exist");
        } else {
            try {
                rackService.deleteById(id);
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("Rack deleted seccessfully")
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
    public ResponseEntity<ApiResponse<Boolean>> delete(@RequestBody final RackModel model) {
        if (null == model || null == model.getRackId() || !rackService
                .exists(model.getRackId())) {
            throw new IllegalArgumentException("Item rack does not exist");
        } else {
            try {
                rackService.delete(mapper.toEntity(model));
                return ResponseEntity.ok(
                        ApiResponse
                                .<Boolean>builder()
                                .success(true)
                                .message("Rack deleted seccessfully")
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
