package org.zahid.apps.web.pos.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.dto.PartyDTO;
import org.zahid.apps.web.pos.entity.NavigationDtl;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.mapper.PartyMapper;
import org.zahid.apps.web.pos.model.PartyModel;
import org.zahid.apps.web.pos.service.PartyService;

import java.util.HashSet;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/party")
public class PartyController {

  private static final Logger LOG = LogManager.getLogger(PartyController.class);
  @Autowired
  private PartyService partyService;

  @Autowired
  private PartyMapper mapper;

  private final int[] indx = {-1};

  private static void setPartyForBalance(final Party party) {
    if (CollectionUtils.isNotEmpty(party.getPartyBalances())) {
      party.getPartyBalances().forEach(balance -> {
        balance.setParty(party);
      });
    }
  }

  @GetMapping("all")
  public List<PartyModel> findAll() {
    return mapper.mapPartiesToPartyModels(partyService.findAll());
  }

  @GetMapping("{id}")
  public PartyDTO findById(@PathVariable("id") final Long id) {
    final PartyModel model = mapper.fromParty(partyService.findById(id));
    indx[0] = findAll().indexOf(model);
    LOG.info("Index in findById(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @GetMapping("first")
  public PartyDTO first() {
    indx[0] = 0;
    LOG.info("Index in first(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @GetMapping("previous")
  public PartyDTO previous() {
    indx[0]--;
    LOG.info("Index in previous(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @GetMapping("next")
  public PartyDTO next() {
    indx[0]++;
    LOG.info("Index in next(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @GetMapping("last")
  public PartyDTO last() {
    indx[0] = findAll().size() - 1;
    LOG.info("Index in last(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PartyDTO save(@RequestBody final PartyModel partyModel) {
        /*if (null == party.getPartyCode()) {
            party.setPartyCode(partyService.generateID() >= (findAll().size() + 1) ? partyService.generateID() : (findAll().size() + 1));
        }*/
    final Party party = mapper.toParty(partyModel);
    //    Below line added, because when converted from model to party, there is no party set in partyBalance list.
    setPartyForBalance(party);
    final Party partySaved = partyService.save(party);
    final PartyModel modelSaved = mapper.fromParty(partySaved);
    indx[0] = this.findAll().indexOf(modelSaved);
    LOG.info("Index in saveParty(): {}", indx[0]);
    return getPartyDTO(findAll(), indx[0]);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Party> saveAll(@RequestBody final List<PartyModel> partyModels) {
    final List<Party> parties = mapper.mapPartyModelsToParties(partyModels);
    //    Below line added, because when converted from model to party, there is no party set in partyBalance list.
    parties.forEach(party -> {
      setPartyForBalance(party);
    });
    return partyService
        .save(new HashSet<>(parties));
  }

  @DeleteMapping("/delete/{id}")
  public PartyDTO deleteById(@PathVariable("id") final Long id) {
    if (!partyService.exists(id)) {
      throw new IllegalArgumentException("Party with id: " + id + " does not exist");
    } else {
      try {
        partyService.deleteById(id);
        indx[0]--;
        LOG.info("Index in deletePartyById(): {}", indx[0]);
        return getPartyDTO(findAll(), indx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PartyDTO delete(@RequestBody final PartyModel partyModel) {
    LOG.info("Index: {}", indx);
    if (null == partyModel || null == partyModel.getPartyCode() || !partyService
        .exists(partyModel.getPartyCode())) {
      throw new IllegalArgumentException("Party does not exist");
    } else {
      try {
        partyService.delete(mapper.toParty(partyModel));
        indx[0]--;
        LOG.info("Index in deleteParty(): {}", indx[0]);
        return getPartyDTO(findAll(), indx[0]);
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

  private final PartyDTO getPartyDTO(List<PartyModel> parties, int indx) {
    if (indx < 0 || indx > parties.size() - 1) {
      LOG.info("Index in getPartyDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final NavigationDtl dtl = resetNavigation();
      final PartyModel party = parties.get(indx);
      final PartyDTO partyDTO = new PartyDTO();
      partyDTO.setParty(party);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < parties.size() - 1) {
        dtl.setLast(false);
      }
      partyDTO.setNavigationDtl(dtl);
      return partyDTO;
    }
  }
}
