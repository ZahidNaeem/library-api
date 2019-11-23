package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.exception.PartyNotFoundException;
import org.zahid.apps.web.pos.repo.PartyRepo;
import org.zahid.apps.web.pos.service.PartyService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PartyServiceImpl implements PartyService {

  private PartyRepo partyRepo;
  private final Logger LOG = LogManager.getLogger(PartyServiceImpl.class);

  public PartyServiceImpl() {

  }

  @Autowired
  public PartyServiceImpl(PartyRepo partyRepo) {
    this.partyRepo = partyRepo;
  }

  private Sort orderBy(String column) {
    return Sort.by(Sort.Direction.ASC, column);
  }

  @Override
  public Long generateID() {
    return partyRepo.generateID();
  }

  @Override
  public boolean exists(Long id) {
    return partyRepo.existsById(id);
  }

  @Override
  public List<Party> findAll() {
    return partyRepo.findAll(orderBy("partyCode"));
  }

  @Override
  public Party findById(Long id) {
    return Optional.ofNullable(partyRepo.findById(id))
            .map(party -> party.get())
            .orElseThrow(() -> new PartyNotFoundException("Party with id " + id + " not found"));
    /*final Optional<Party> party = partyRepo.findById(id);
    if (party.isPresent()) {
      return party.get();
    }
    throw new PartyNotFoundException("Party with id " + id + " not found");*/
  }

  @Override
  public Party save(Party party) throws DataIntegrityViolationException {
    return partyRepo.save(party);
  }

  @Override
  public List<Party> save(Set<Party> parties) throws DataIntegrityViolationException {
    List<Party> returnParties = partyRepo.saveAll(parties);
    return returnParties;
  }

  @Override
  public void delete(Party party) throws DataIntegrityViolationException {
    partyRepo.delete(party);
  }

  @Override
  public void delete(Set<Party> parties) throws DataIntegrityViolationException {
    partyRepo.deleteAll(parties);
  }

  @Override
  public void deleteById(Long id) throws DataIntegrityViolationException {
    partyRepo.deleteById(id);
  }

  @Override
  public void deleteAll() throws DataIntegrityViolationException {
    partyRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() throws DataIntegrityViolationException {
    partyRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<Party> parties) throws DataIntegrityViolationException {
    partyRepo.deleteInBatch(parties);
  }
}
