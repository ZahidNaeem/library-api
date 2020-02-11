package org.zahid.apps.web.library.controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.RackMapper;
import org.zahid.apps.web.library.model.RackDetail;
import org.zahid.apps.web.library.model.RackModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.RackService;
import org.zahid.apps.web.library.service.ShelfService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("racks")
public class RackController {

    private static final Logger LOG = LogManager.getLogger(RackController.class);

    @Autowired
    private RackService rackService;

    @Autowired
    private RackMapper mapper;

    @Autowired
    private ShelfService shelfService;

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
        rackModels = mapper.toRackModels(rackService.findAll());
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
                        .entity(mapper.toRackDetails(rackService.findAll()))
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
                        .entity(mapper.toRackModel(rackService.findById(id)))
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
                        .entity(mapper.toRackModels(rackService.findAllByShelf(shelfService.findById(shelfId))))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<RackModel>> save(@RequestBody final RackModel model) {
        final RackEntity rack = mapper.toRackEntity(model);
        final RackEntity savedRack = rackService.save(rack);
        setRackForVolumes(rack);
        return ResponseEntity.ok(
                ApiResponse
                        .<RackModel>builder()
                        .success(true)
                        .message("Rack saved seccessfully")
                        .entity(mapper.toRackModel(savedRack))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RackEntity>>> saveAll(@RequestBody final Set<RackModel> rackModel) {
        final Set<RackEntity> racks = new HashSet<>();
        rackModel.forEach(model -> {
            final RackEntity rack = mapper.toRackEntity(model);
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
                rackService.delete(mapper.toRackEntity(model));
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
