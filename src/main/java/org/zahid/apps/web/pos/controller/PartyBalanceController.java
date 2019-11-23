package org.zahid.apps.web.pos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.mapper.PartyBalanceMapper;
import org.zahid.apps.web.pos.model.PartyBalanceModel;
import org.zahid.apps.web.pos.service.PartyBalanceService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/balance")
public class PartyBalanceController {

  private static final Logger LOG = LogManager.getLogger(ItemController.class);
  @Autowired
  private PartyBalanceService partyBalanceService;

  @Autowired
  private PartyBalanceMapper mapper;

  @GetMapping("all")
  public List<PartyBalanceModel> findAll() {
    return mapper.mapBalancesToBalanceModels(partyBalanceService.getPartyBalanceList());
  }

  @GetMapping("{id}")
  public PartyBalanceModel findById(@PathVariable("id") final Long id) {
    return mapper.fromPartyBalance(partyBalanceService.findById(id));
  }

  @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PartyBalanceModel save(@RequestBody final PartyBalanceModel model) {
        /*if (null == partyBalance.getPartyBalanceId()) {
            partyBalance.setPartyBalanceId(partyBalanceService.generateID() >= (findAll().size() + 1) ? partyBalanceService.generateID() : (findAll().size() + 1));
        }*/
    final PartyBalance balanceSaed = partyBalanceService.save(mapper.toPartyBalance(model));
    return mapper.fromPartyBalance(balanceSaed);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PartyBalance> saveAll(@RequestBody final Set<PartyBalanceModel> partyBalanceModel) {
    final Set<PartyBalance> partyBalances = new HashSet<>();
    partyBalanceModel.forEach(model -> {
      final PartyBalance partyBalance = mapper.toPartyBalance(model);
      partyBalances.add(partyBalance);
    });
    return partyBalanceService.save(partyBalances);
  }

  @DeleteMapping("delete/{id}")
  public boolean deleteById(@PathVariable("id") final Long id) {
    if (!partyBalanceService.exists(id)) {
      throw new IllegalArgumentException("Item partyBalance with id: " + id + " does not exist");
    } else {
      try {
        partyBalanceService.deleteById(id);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }

  @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean delete(@RequestBody final PartyBalanceModel model) {
    if (null == model || null == model.getPartyBalanceId() || !partyBalanceService
        .exists(model.getPartyBalanceId())) {
      throw new IllegalArgumentException("Item partyBalance does not exist");
    } else {
      try {
        partyBalanceService.delete(mapper.toPartyBalance(model));
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }
}
