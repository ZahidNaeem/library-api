package org.zahid.apps.web.library.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("author")
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

    @GetMapping("all")
    public List<AuthorModel> findAll() {
        return mapper.mapAuthorEntitiesToAuthorModels(authorService.findAll());
    }

    @GetMapping("{id}")
    public AuthorDTO findById(@PathVariable("id") final Long id) {
        final AuthorModel model = mapper.fromAuthor(authorService.findById(id));
        indx[0] = findAll().indexOf(model);
        LOG.info("Index in findById(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @GetMapping("{id}/name")
    public String getAuthorName(@PathVariable("id") final Long id) {
        return authorService.findById(id).getAuthorName();
    }

    @GetMapping("first")
    public AuthorDTO first() {
        indx[0] = 0;
        LOG.info("Index in first(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @GetMapping("previous")
    public AuthorDTO previous() {
        indx[0]--;
        LOG.info("Index in previous(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @GetMapping("next")
    public AuthorDTO next() {
        indx[0]++;
        LOG.info("Index in next(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @GetMapping("last")
    public AuthorDTO last() {
        indx[0] = findAll().size() - 1;
        LOG.info("Index in last(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDTO save(@RequestBody final AuthorModel model) {
        final AuthorEntity author = mapper.toAuthor(model);
//    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
        setAuthorForBooks(author);
        final AuthorEntity authorSaved = authorService.save(author);
        final AuthorModel savedModel = mapper.fromAuthor(authorSaved);
        indx[0] = this.findAll().indexOf(savedModel);
        LOG.info("Index in saveAuthor(): {}", indx[0]);
        return getAuthorDTO(findAll(), indx[0]);
    }

    @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuthorEntity> saveAll(@RequestBody final List<AuthorModel> models) {
        final List<AuthorEntity> authors = mapper.mapAuthorModelsToAuthors(models);
        //    Below line added, because when converted from model to AuthorEntity, there is no author set in book list.
        authors.forEach(author -> {
            setAuthorForBooks(author);
        });
        return authorService.save(new HashSet<>(authors));
    }

    @DeleteMapping("/delete/{id}")
    public AuthorDTO deleteById(@PathVariable("id") final Long id) {
        if (!authorService.exists(id)) {
            throw new IllegalArgumentException("AuthorEntity with id: " + id + " does not exist");
        } else {
            try {
                authorService.deleteById(id);
                indx[0]--;
                LOG.info("Index in deleteAuthorById(): {}", indx[0]);
                return getAuthorDTO(findAll(), indx[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDTO delete(@RequestBody final AuthorModel model) {
        LOG.info("Index: {}", indx);
        if (null == model || null == model.getAuthorId() || !authorService.exists(model.getAuthorId())) {
            throw new IllegalArgumentException("AuthorEntity does not exist");
        } else {
            try {
                authorService.delete(mapper.toAuthor(model));
                indx[0]--;
                LOG.info("Index in deleteAuthor(): {}", indx[0]);
                return getAuthorDTO(findAll(), indx[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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
