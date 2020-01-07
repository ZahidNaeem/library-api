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
import org.zahid.apps.web.library.payload.response.SearchVolumeResponse;
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
        return ResponseEntity.ok(mapper.toVolumeModels(volumeService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<VolumeModel> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(mapper.toVolumeModel(volumeService.findById(id)));
    }

    @GetMapping("resp/all")
    public ResponseEntity<List<SearchVolumeResponse>> findAllSearchResponses() {
//        return ResponseEntity.ok(mapper.toVolumeModels(volumeService.findAllByBook(bookService.findById(bookId))));
        return ResponseEntity.ok(volumeService.findAllSearchResponses());
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<List<SearchVolumeResponse>> findByBook(@PathVariable("bookId") final Long bookId) {
//        return ResponseEntity.ok(mapper.toVolumeModels(volumeService.findAllByBook(bookService.findById(bookId))));
        return ResponseEntity.ok(volumeService.findAllByBookId(bookId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VolumeModel> save(@RequestBody final VolumeModel model) {
        final VolumeEntity savedVolume = volumeService.save(mapper.toVolumeEntity(model));
        return ResponseEntity.ok(mapper.toVolumeModel(savedVolume));
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VolumeEntity>> saveAll(@RequestBody final Set<VolumeModel> volumeModel) {
        final Set<VolumeEntity> volumes = new HashSet<>();
        volumeModel.forEach(model -> {
            final VolumeEntity volume = mapper.toVolumeEntity(model);
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
                volumeService.delete(mapper.toVolumeEntity(model));
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(false);
            }
        }
    }
}
