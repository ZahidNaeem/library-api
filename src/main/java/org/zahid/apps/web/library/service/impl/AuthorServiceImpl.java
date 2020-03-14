package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.exception.ChildRecordFoundException;
import org.zahid.apps.web.library.repo.AuthorRepo;
import org.zahid.apps.web.library.service.AuthorService;
import org.zahid.apps.web.library.utils.Miscellaneous;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOG = LogManager.getLogger(AuthorServiceImpl.class);

    private final AuthorRepo authorRepo;

    @Override
    public List<AuthorEntity> findAll() {
        return authorRepo.findAllByOrderByAuthorIdAsc();
    }

    @Override
    public List<AuthorEntity> searchAuthor(AuthorEntity authorEntity) {
        return authorRepo.searchAuthor(authorEntity);
    }

    @Override
    public AuthorEntity findById(Long id) {
        return authorRepo.findById(id)
                .orElse(new AuthorEntity());
    }

    @Override
    public boolean exists(Long id) {
        return authorRepo.existsById(id);
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepo.saveAndFlush(author);
    }

    @Override
    public List<AuthorEntity> save(Set<AuthorEntity> authors) {
        return authorRepo.saveAll(authors);
    }

    @Override
    public void delete(AuthorEntity author) {
        authorRepo.delete(author);
    }

    @Override
    public void delete(Set<AuthorEntity> authors) {
        authorRepo.deleteAll(authors);
    }

    @Override
    public void deleteById(Long id) {
        try {
            authorRepo.deleteById(id);
        } catch (Exception e) {
            final Exception ex = Miscellaneous.getNestedException(e);
            LOG.error("Exception in delete: {}", ex.getMessage());
            if(ex.getMessage().startsWith("ORA-02292")){
                throw new ChildRecordFoundException("You can't delete this record. Child record found");
            }
        }
    }

    @Override
    public void deleteAll() {
        authorRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        authorRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<AuthorEntity> authors) {
        authorRepo.deleteInBatch(authors);
    }
}
