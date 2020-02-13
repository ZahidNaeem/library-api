package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.AuthorDTO;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.exception.InternalServerErrorException;
import org.zahid.apps.web.library.mapper.AuthorMapper;
import org.zahid.apps.web.library.model.AuthorModel;
import org.zahid.apps.web.library.payload.response.ApiResponse;
import org.zahid.apps.web.library.service.AuthorService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private static final Logger LOG = LogManager.getLogger(AuthorController.class);
    private List<AuthorModel> authorModels = new ArrayList<>();

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper mapper;

    @PostConstruct
    public void init() {
        authorModels = mapper.toAuthorModels(authorService.findAll());
    }

    private final int[] indx = {-1};

    private static void setAuthorForBooks(final AuthorEntity author) {
        if (CollectionUtils.isNotEmpty(author.getBooks())) {
            author.getBooks().forEach(book -> {
                book.setAuthor(author);
            });
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorModel>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse
                        .<List<AuthorModel>>builder()
                        .success(true)
                        .message("findAll response")
                        .entity(authorModels)
                        .build()
        );
    }

    @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AuthorModel>>> searchAuthor(@RequestBody final AuthorModel model) {
        authorModels = mapper.toAuthorModels(authorService.searchAuthor(mapper.toAuthorEntity(model)));
        return ResponseEntity.ok(
                ApiResponse
                        .<List<AuthorModel>>builder()
                        .success(true)
                        .message("searchAuthor response")
                        .entity(authorModels)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AuthorDTO>> findById(@PathVariable("id") final Long id) {
        final AuthorModel model = mapper.toAuthorModel(authorService.findById(id));
        indx[0] = authorModels.indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("findById response")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("{id}/name")
    public ResponseEntity<ApiResponse<String>> getAuthorName(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(
                ApiResponse
                        .<String>builder()
                        .success(true)
                        .message("getAuthorName response")
                        .entity(authorService.findById(id).getAuthorName())
                        .build()
        );
    }

    @GetMapping("first")
    public ResponseEntity<ApiResponse<AuthorDTO>> first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("first record response")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("previous")
    public ResponseEntity<ApiResponse<AuthorDTO>> previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("previous record response")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("next")
    public ResponseEntity<ApiResponse<AuthorDTO>> next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("next record response")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @GetMapping("last")
    public ResponseEntity<ApiResponse<AuthorDTO>> last() {
        indx[0] = authorModels.size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("last record response")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AuthorDTO>> save(@RequestBody final AuthorModel model) {
        final AuthorEntity author = mapper.toAuthorEntity(model);
//    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
        setAuthorForBooks(author);
        final AuthorEntity authorSaved = authorService.save(author);
        final AuthorModel savedModel = mapper.toAuthorModel(authorSaved);
        init();
        indx[0] = this.authorModels.indexOf(savedModel);
        LOG.info("Index in saveAuthor(): {}", indx[0]);
        return ResponseEntity.ok(
                ApiResponse
                        .<AuthorDTO>builder()
                        .success(true)
                        .message("Author saved seccessfully")
                        .entity(getAuthorDTO(authorModels, indx[0]))
                        .build()
        );
    }

    @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AuthorEntity>>> saveAll(@RequestBody final List<AuthorModel> models) {
        final List<AuthorEntity> authors = mapper.toAuthorEntities(models);
        //    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
        authors.forEach(author -> {
            setAuthorForBooks(author);
        });
        return ResponseEntity.ok(
                ApiResponse
                        .<List<AuthorEntity>>builder()
                        .success(true)
                        .message("All authors saved seccessfully")
                        .entity(authorService.save(new HashSet<>(authors)))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<AuthorDTO>> deleteById(@PathVariable("id") final Long id) {
        if (!authorService.exists(id)) {
            throw new IllegalArgumentException("AuthorEntity with id: " + id + " does not exist");
        } else {
            try {
                authorService.deleteById(id);
                init();
                indx[0]--;
                LOG.info("Index in deleteAuthorById(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<AuthorDTO>builder()
                                .success(true)
                                .message("Author deleted seccessfully")
                                .entity(getAuthorDTO(authorModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AuthorDTO>> delete(@RequestBody final AuthorModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getAuthorId() || !authorService
                .exists(model.getAuthorId())) {
            throw new IllegalArgumentException("AuthorEntity does not exist");
        } else {
            try {
                authorService.delete(mapper.toAuthorEntity(model));
                init();
                indx[0]--;
                LOG.info("Index in deleteAuthor(): {}", indx[0]);
                return ResponseEntity.ok(
                        ApiResponse
                                .<AuthorDTO>builder()
                                .success(true)
                                .message("Author deleted seccessfully")
                                .entity(getAuthorDTO(authorModels, indx[0]))
                                .build()
                );
            } catch (Exception e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    private static final NavigationDtl resetNavigation() {
        NavigationDtl dtl = new NavigationDtl();
        dtl.setFirst(true);
        dtl.setLast(true);
        return dtl;
    }

    private static final AuthorDTO getAuthorDTO(List<AuthorModel> models, int indx) {
        final NavigationDtl dtl = resetNavigation();
        if (models.size() < 1) {
            final AuthorModel model = new AuthorModel();
            return AuthorDTO.builder()
                    .author(model)
                    .navigationDtl(dtl)
                    .build();
        }
        if (indx < 0 || indx > models.size() - 1) {
            LOG.info("models.size(): {}", models.size());
            LOG.info("Index in getAuthorDTO(): {}", indx);
            throw new IndexOutOfBoundsException();
        } else {
            final AuthorModel model = models.get(indx);
            if (indx > 0) {
                dtl.setFirst(false);
            }
            if (indx < models.size() - 1) {
                dtl.setLast(false);
            }

            return AuthorDTO.builder()
                    .author(model)
                    .navigationDtl(dtl)
                    .build();
        }
    }
}
