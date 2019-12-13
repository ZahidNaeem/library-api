package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.AuthorEntity;
import org.zahid.apps.web.library.exception.AuthorNotFoundException;
import org.zahid.apps.web.library.repo.AuthorRepo;
import org.zahid.apps.web.library.service.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public List<AuthorEntity> findAll() {
        return authorRepo.findAll();
    }

    @Override
    public AuthorEntity findById(Long id) {
        return Optional.ofNullable(authorRepo.findById(id))
                .map(author -> author.get())
                .orElseThrow(() -> new AuthorNotFoundException("Author with id: " + id + " not found"));
    }

    @Override
    public boolean exists(Long id) {
        return authorRepo.existsById(id);
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepo.save(author);
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
        authorRepo.deleteById(id);
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