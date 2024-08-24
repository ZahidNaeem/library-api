package com.alabtaal.library.controller;

import com.alabtaal.library.dto.AuthorDTO;
import com.alabtaal.library.dto.NavigationDtl;
import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.mapper.AuthorMapper;
import com.alabtaal.library.model.AuthorModel;
import com.alabtaal.library.payload.response.ApiResponse;
import com.alabtaal.library.service.AuthorService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
  private final AuthorService authorService;
  private final AuthorMapper mapper;
  private final int[] index = {-1};
  private List<AuthorModel> authorModels = new ArrayList<>();

  private static void setAuthorForBooks(final AuthorEntity author) {
    if (CollectionUtils.isNotEmpty(author.getBooks())) {
      author.getBooks().forEach(book -> {
        book.setAuthor(author);
      });
    }
  }

  private static NavigationDtl resetNavigation() {
    NavigationDtl dtl = new NavigationDtl();
    dtl.setFirst(true);
    dtl.setLast(true);
    return dtl;
  }

  private static AuthorDTO getAuthorDTO(List<AuthorModel> models, int indx) {
    final NavigationDtl dtl = resetNavigation();
    if (models.isEmpty()) {
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

  @PostConstruct
  public void init() {
    authorModels = mapper.toModels(authorService.findAll());
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
    authorModels = mapper.toModels(authorService.searchAuthor(mapper.toEntity(model)));
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
  public ResponseEntity<ApiResponse<AuthorDTO>> findById(@PathVariable("id") final UUID id) {
    final AuthorModel model = mapper.toModel(authorService.findById(id));
    index[0] = authorModels.indexOf(model);
    LOG.info("Index in findById(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("findById response")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @GetMapping("{id}/name")
  public ResponseEntity<ApiResponse<String>> getAuthorName(@PathVariable("id") final UUID id) {
    return ResponseEntity.ok(
        ApiResponse
            .<String>builder()
            .success(true)
            .message("getAuthorName response")
            .entity(authorService.findById(id).getName())
            .build()
    );
  }

  @GetMapping("first")
  public ResponseEntity<ApiResponse<AuthorDTO>> first() {
    index[0] = 0;
    LOG.info("Index in first(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("first record response")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @GetMapping("previous")
  public ResponseEntity<ApiResponse<AuthorDTO>> previous() {
    index[0]--;
    LOG.info("Index in previous(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("previous record response")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @GetMapping("next")
  public ResponseEntity<ApiResponse<AuthorDTO>> next() {
    index[0]++;
    LOG.info("Index in next(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("next record response")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @GetMapping("last")
  public ResponseEntity<ApiResponse<AuthorDTO>> last() {
    index[0] = authorModels.size() - 1;
    LOG.info("Index in last(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("last record response")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<AuthorDTO>> save(@RequestBody final AuthorModel model) {
    final AuthorEntity author = mapper.toEntity(model);
//    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
    setAuthorForBooks(author);
    final AuthorEntity authorSaved = authorService.save(author);
    final AuthorModel savedModel = mapper.toModel(authorSaved);
    init();
    index[0] = this.authorModels.indexOf(savedModel);
    LOG.info("Index in saveAuthor(): {}", index[0]);
    return ResponseEntity.ok(
        ApiResponse
            .<AuthorDTO>builder()
            .success(true)
            .message("Author saved seccessfully")
            .entity(getAuthorDTO(authorModels, index[0]))
            .build()
    );
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<AuthorEntity>>> saveAll(@RequestBody final List<AuthorModel> models) {
    final List<AuthorEntity> authors = mapper.toEntities(models);
    //    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
    authors.forEach(AuthorController::setAuthorForBooks);
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
  public ResponseEntity<ApiResponse<AuthorDTO>> deleteById(@PathVariable("id") final UUID id) {
    if (!authorService.exists(id)) {
      throw new IllegalArgumentException("AuthorEntity with id: " + id + " does not exist");
    } else {

        authorService.deleteById(id);
        init();
        index[0]--;
        LOG.info("Index in deleteAuthorById(): {}", index[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<AuthorDTO>builder()
                .success(true)
                .message("Author deleted seccessfully")
                .entity(getAuthorDTO(authorModels, index[0]))
                .build()
        );

    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<AuthorDTO>> delete(@RequestBody final AuthorModel model) {
    LOG.info("Index: {}", index);
    if (null == model || null == model.getId() || !authorService.exists(model.getId())) {
      throw new IllegalArgumentException("AuthorEntity does not exist");
    } else {

        authorService.delete(mapper.toEntity(model));
        init();
        index[0]--;
        LOG.info("Index in deleteAuthor(): {}", index[0]);
        return ResponseEntity.ok(
            ApiResponse
                .<AuthorDTO>builder()
                .success(true)
                .message("Author deleted seccessfully")
                .entity(getAuthorDTO(authorModels, index[0]))
                .build()
        );

    }
  }
}
