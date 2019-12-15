package org.zahid.apps.web.library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.VolumeService;
import org.zahid.apps.web.library.service.BookService;

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

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<VolumeModel>> findAll() {
        return ResponseEntity.ok(mapper.mapVolumeEntitiesToVolumeModels(volumeService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<VolumeModel> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(mapper.fromVolume(volumeService.findById(id)));
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<List<VolumeModel>> findByBook(@PathVariable("bookId") final Long bookId) {
        return ResponseEntity.ok(mapper.mapVolumeEntitiesToVolumeModels(volumeService.findAllByBook(bookService.findById(bookId))));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VolumeModel> save(@RequestBody final VolumeModel model) {
        final VolumeEntity savedVolume = volumeService.save(mapper.toVolume(model));
        return ResponseEntity.ok(mapper.fromVolume(savedVolume));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VolumeEntity>> saveAll(@RequestBody final Set<VolumeModel> volumeModel) {
        final Set<VolumeEntity> volumes = new HashSet<>();
        volumeModel.forEach(model -> {
            final VolumeEntity volume = mapper.toVolume(model);
            volumes.add(volume);
        });
        return ResponseEntity.ok(volumeService.save(volumes));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") final Long id) {
        if (!volumeService.exists(id)) {
            throw new IllegalArgumentException("Item volume with id: " + id + " does not exist");
        } else {
            try {
                volumeService.deleteById(id);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@RequestBody final VolumeModel model) {
        if (null == model || null == model.getVolumeId() || !volumeService
                .exists(model.getVolumeId())) {
            throw new IllegalArgumentException("Item volume does not exist");
        } else {
            try {
                volumeService.delete(mapper.toVolume(model));
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }
}
