package org.zahid.apps.web.library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.payload.response.LibraryResponse;
import org.zahid.apps.web.library.payload.response.SearchVolumeResponse;
import org.zahid.apps.web.library.service.BookService;
import org.zahid.apps.web.library.service.VolumeService;

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
    public LibraryResponse<List<VolumeModel>> findAll() {
        return LibraryResponse
                .<List<VolumeModel>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(mapper.toVolumeModels(volumeService.findAll())))
                .build();
    }

    @GetMapping("{id}")
    public LibraryResponse<VolumeModel> findById(@PathVariable("id") final Long id) {
        return LibraryResponse
                .<VolumeModel>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(mapper.toVolumeModel(volumeService.findById(id))))
                .build();
    }

    @GetMapping("resp/all")
    public LibraryResponse<List<SearchVolumeResponse>> findAllSearchResponses() {
//        return LibraryResponse(mapper.toVolumeModels(volumeService.findAllByBook(bookService.findById(bookId))));
        return LibraryResponse
                .<List<SearchVolumeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(volumeService.findAllSearchResponses()))
                .build();
    }

    @GetMapping("book/{bookId}")
    public LibraryResponse<List<SearchVolumeResponse>> findByBook(@PathVariable("bookId") final Long bookId) {
//        return LibraryResponse(mapper.toVolumeModels(volumeService.findAllByBook(bookService.findById(bookId))));
        return LibraryResponse
                .<List<SearchVolumeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(volumeService.findAllSearchResponses()))
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LibraryResponse<VolumeModel> save(@RequestBody final VolumeModel model) {
        final VolumeEntity savedVolume = volumeService.save(mapper.toVolumeEntity(model));
        return LibraryResponse
                .<VolumeModel>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(mapper.toVolumeModel(savedVolume)))
                .build();
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LibraryResponse<List<VolumeEntity>> saveAll(@RequestBody final Set<VolumeModel> volumeModel) {
        final Set<VolumeEntity> volumes = new HashSet<>();
        volumeModel.forEach(model -> {
            final VolumeEntity volume = mapper.toVolumeEntity(model);
            volumes.add(volume);
        });
        return LibraryResponse
                .<List<VolumeEntity>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .entity(ResponseEntity.ok(volumeService.save(volumes)))
                .build();
    }

    @DeleteMapping("{id}")
    public LibraryResponse<Boolean> deleteById(@PathVariable("id") final Long id) {
        if (!volumeService.exists(id)) {
            throw new IllegalArgumentException("Item volume with id: " + id + " does not exist");
        } else {
            try {
                volumeService.deleteById(id);
                return LibraryResponse
                        .<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .message("")
                        .entity(ResponseEntity.ok(true))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                return LibraryResponse
                        .<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .message("")
                        .entity(ResponseEntity.ok(false))
                        .build();
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LibraryResponse<Boolean> delete(@RequestBody final VolumeModel model) {
        if (null == model || null == model.getVolumeId() || !volumeService
                .exists(model.getVolumeId())) {
            throw new IllegalArgumentException("Item volume does not exist");
        } else {
            try {
                volumeService.delete(mapper.toVolumeEntity(model));
                return LibraryResponse
                        .<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .message("")
                        .entity(ResponseEntity.ok(true))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                return LibraryResponse
                        .<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .message("")
                        .entity(ResponseEntity.ok(false))
                        .build();
            }
        }
    }
}
