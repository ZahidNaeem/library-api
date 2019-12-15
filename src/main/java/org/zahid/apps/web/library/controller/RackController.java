package org.zahid.apps.web.library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.mapper.RackMapper;
import org.zahid.apps.web.library.model.RackModel;
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

    @GetMapping
    public ResponseEntity<List<RackModel>> findAll() {
        return ResponseEntity.ok(mapper.mapRackEntitiesToRackModels(rackService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<RackModel> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(mapper.fromRack(rackService.findById(id)));
    }

    @GetMapping("shelf/{shelfId}")
    public ResponseEntity<List<RackModel>> findByShelf(@PathVariable("shelfId") final Long shelfId) {
        return ResponseEntity.ok(mapper.mapRackEntitiesToRackModels(rackService.findAllByShelf(shelfService.findById(shelfId))));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RackModel> save(@RequestBody final RackModel model) {
        final RackEntity savedRack = rackService.save(mapper.toRack(model));
        return ResponseEntity.ok(mapper.fromRack(savedRack));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RackEntity>> saveAll(@RequestBody final Set<RackModel> rackModel) {
        final Set<RackEntity> racks = new HashSet<>();
        rackModel.forEach(model -> {
            final RackEntity rack = mapper.toRack(model);
            racks.add(rack);
        });
        return ResponseEntity.ok(rackService.save(racks));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") final Long id) {
        if (!rackService.exists(id)) {
            throw new IllegalArgumentException("Item rack with id: " + id + " does not exist");
        } else {
            try {
                rackService.deleteById(id);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@RequestBody final RackModel model) {
        if (null == model || null == model.getRackId() || !rackService
                .exists(model.getRackId())) {
            throw new IllegalArgumentException("Item rack does not exist");
        } else {
            try {
                rackService.delete(mapper.toRack(model));
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }
}
