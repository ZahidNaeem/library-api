package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.ResearcherEntity;
import org.zahid.apps.web.library.repo.ResearcherRepo;
import org.zahid.apps.web.library.service.ResearcherService;

import java.util.List;
import java.util.Set;

@Service
public class ResearcherServiceImpl implements ResearcherService {

    @Autowired
    private ResearcherRepo researcherRepo;

    @Override
    public List<ResearcherEntity> findAll() {
        return researcherRepo.findAllByOrderByResearcherIdAsc();
    }

    @Override
    public ResearcherEntity findById(Long id) {
        return researcherRepo.findById(id)
                .orElse(new ResearcherEntity());
    }

    @Override
    public boolean exists(Long id) {
        return researcherRepo.existsById(id);
    }

    @Override
    public ResearcherEntity save(ResearcherEntity researcher) {
        return researcherRepo.save(researcher);
    }

    @Override
    public List<ResearcherEntity> save(Set<ResearcherEntity> researchers) {
        return researcherRepo.saveAll(researchers);
    }

    @Override
    public void delete(ResearcherEntity researcher) {
        researcherRepo.delete(researcher);
    }

    @Override
    public void delete(Set<ResearcherEntity> researchers) {
        researcherRepo.deleteAll(researchers);
    }

    @Override
    public void deleteById(Long id) {
        researcherRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        researcherRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        researcherRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<ResearcherEntity> researchers) {
        researcherRepo.deleteInBatch(researchers);
    }
}
