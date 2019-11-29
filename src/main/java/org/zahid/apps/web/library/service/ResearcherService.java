package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.ResearcherEntity;

import java.util.List;
import java.util.Set;

public interface ResearcherService {

    List<ResearcherEntity> findAll();

    ResearcherEntity findById(final Long id);

    boolean exists(Long id);

    ResearcherEntity save(ResearcherEntity researcher);

    List<ResearcherEntity> save(Set<ResearcherEntity> researchers);

    void delete(ResearcherEntity researcher);

    void delete(Set<ResearcherEntity> researchers);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<ResearcherEntity> researchers);
}
