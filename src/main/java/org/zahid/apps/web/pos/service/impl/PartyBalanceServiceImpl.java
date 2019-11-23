package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.exception.PartyBalanceNotFoundException;
import org.zahid.apps.web.pos.repo.PartyBalanceRepo;
import org.zahid.apps.web.pos.service.PartyBalanceService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PartyBalanceServiceImpl implements PartyBalanceService {

    @Autowired
    private PartyBalanceRepo partyBalanceRepo;
    //    private final Logger LOG = Logger.getLogger(PartyBalanceServiceImpl.class.getName());
    private final Logger LOG = LogManager.getLogger(PartyBalanceServiceImpl.class);

    @Override
    public Long generateID() {
        return partyBalanceRepo.generateID();
    }

    @Override
    public boolean exists(Long id) {
        return partyBalanceRepo.existsById(id);
    }

    @Override
    public List<PartyBalance> getPartyBalanceList() {
        return partyBalanceRepo.findAll();
    }

    @Override
    public List<PartyBalance> getCurrentPartyBalanceList() {
        // TODO:
        return null;
    }

    @Override
    public List<PartyBalance> getCurrentPartyBalanceListFromDB() {
        // TODO:
        return null;
    }

    @Override
    public List<PartyBalance> getPartyBalanceListFromDB(Party party) {
        return partyBalanceRepo.findAllByParty(party);
    }

    @Override
    public PartyBalance findById(Long id) {
        return Optional.ofNullable(partyBalanceRepo.findById(id))
                .map(balance -> balance.get())
                .orElseThrow(() -> new PartyBalanceNotFoundException("Party balance with id " + id + " not found"));
        /*final Optional<PartyBalance> balance = partyBalanceRepo.findById(id);
        if (balance.isPresent()) {
            return balance.get();
        }
        throw new PartyBalanceNotFoundException("Party balance with id " + id + " not found");*/
    }

    //	@Override
//	public List<PartyBalance> findAllByParty(Party party) {
//		return party.getPartyBalances();
//	}
    @Override
    public List<PartyBalance> addBalanceToBalanceList(PartyBalance stock) {
        getCurrentPartyBalanceList().add(stock);
        return getCurrentPartyBalanceList();
    }

    @Override
    public Party getCurrentParty() {
        // TODO:
        return null;
    }

    @Override
    public PartyBalance attachBalanceWithParty(PartyBalance stock) {
        /*partyController.getNavigationController().object.getPartyBalances().add(stock);
        return stock;*/
        // TODO:
        return null;
    }

//    @Override
//    public List<PartyBalance> attachStockWithParty(List<PartyBalance> stocks) {
//        --partyController.getNavigationController().object.getPartyBalances().addAll(stocks);
//        return stocks;
//    }

    //	@Override
//	public PartyBalance prepareCreate() {
//		PartyBalance stock = new PartyBalance();
//		stock.setParty(partyController.getNavigationController().object);
//		partyController.getNavigationController().object.getPartyBalances().add(stock);
//		return stock;
//	}
    @Override
    public PartyBalance save(PartyBalance partyBalance) {
        return partyBalanceRepo.save(partyBalance);
    }

    @Override
    public List<PartyBalance> save(Set<PartyBalance> partyBalances) {
        /*if (partyController.getDmlRecords().size() > 0) {
//            LOG("Parties: " + partyController.getDmlRecords().size());
            partyController.save();
        }*/
        return partyBalanceRepo.saveAll(partyBalances);
    }

    @Override
    public void delete(PartyBalance partyBalance) throws DataIntegrityViolationException {
        partyBalanceRepo.delete(partyBalance);
    }

    @Override
    public void delete(Set<PartyBalance> partyBalances) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAll(partyBalances);
    }

    @Override
    public void deleteById(Long id) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteById(id);
    }

    @Override
    public void deleteAll() throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<PartyBalance> partyBalances) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteInBatch(partyBalances);
    }

}
