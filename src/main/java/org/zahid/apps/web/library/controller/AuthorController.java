package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.library.dto.AuthorDTO;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.mapper.AuthorMapper;
import org.zahid.apps.web.library.model.AuthorModel;
import org.zahid.apps.web.library.service.AuthorService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

  private static final Logger LOG = LogManager.getLogger(AuthorController.class);
  @Autowired
  private AuthorService authorService;

  @Autowired
  private AuthorMapper mapper;

  private final int[] indx = {-1};

  private static void setAuthorForBooks(final AuthorEntity author) {
    if (CollectionUtils.isNotEmpty(author.getBooks())) {
      author.getBooks().forEach(book -> {
        book.setAuthor(author);
      });
    }
  }

  @GetMapping
  public ResponseEntity<List<AuthorModel>> findAll() {
    return ResponseEntity.ok(mapper.toAuthorModels(authorService.findAll()));
  }

  @GetMapping("{id}")
  public ResponseEntity<AuthorDTO> findById(@PathVariable("id") final Long id) {
    final AuthorModel model = mapper.toAuthorModel(authorService.findById(id));
    indx[0] = findAll().getBody().indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @GetMapping("{id}/name")
  public ResponseEntity<String> getAuthorName(@PathVariable("id") final Long id) {
    return ResponseEntity.ok(authorService.findById(id).getAuthorName());
  }

  @GetMapping("first")
  public ResponseEntity<AuthorDTO> first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @GetMapping("previous")
  public ResponseEntity<AuthorDTO> previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @GetMapping("next")
  public ResponseEntity<AuthorDTO> next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @GetMapping("last")
  public ResponseEntity<AuthorDTO> last() {
    indx[0] = findAll().getBody().size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthorDTO> save(@RequestBody final AuthorModel model) {
    final AuthorEntity author = mapper.toAuthorEntity(model);
//    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
    setAuthorForBooks(author);
    final AuthorEntity authorSaved = authorService.save(author);
    final AuthorModel savedModel = mapper.toAuthorModel(authorSaved);
    indx[0] = this.findAll().getBody().indexOf(savedModel);
    LOG.info("Index in saveAuthor(): {}", indx[0]);
    return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
  }

  @PostMapping(path = "all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AuthorEntity>> saveAll(@RequestBody final List<AuthorModel> models) {
    final List<AuthorEntity> authors = mapper.toAuthorEntities(models);
    //    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
    authors.forEach(author -> {
      setAuthorForBooks(author);
    });
    return ResponseEntity.ok(authorService.save(new HashSet<>(authors)));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<AuthorDTO> deleteById(@PathVariable("id") final Long id) {
    if (!authorService.exists(id)) {
      throw new IllegalArgumentException("AuthorEntity with id: " + id + " does not exist");
    } else {
      try {
        authorService.deleteById(id);
        indx[0]--;
        LOG.info("Index in deleteAuthorById(): {}", indx[0]);
        return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthorDTO> delete(@RequestBody final AuthorModel model) {
    LOG.info("Index: {}", indx);
    if (null == model || null == model.getAuthorId() || !authorService
        .exists(model.getAuthorId())) {
      throw new IllegalArgumentException("AuthorEntity does not exist");
    } else {
      try {
        authorService.delete(mapper.toAuthorEntity(model));
        indx[0]--;
        LOG.info("Index in deleteAuthor(): {}", indx[0]);
        return ResponseEntity.ok(getAuthorDTO(findAll().getBody(), indx[0]));
      } catch (Exception e) {
        e.printStackTrace();
          return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
