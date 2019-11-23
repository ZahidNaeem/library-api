package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;

import java.util.List;
import java.util.Set;

public interface PartyBalanceService {

    Long generateID();

    boolean exists(Long id);

    List<PartyBalance> getPartyBalanceList();

    List<PartyBalance> getCurrentPartyBalanceList();

    List<PartyBalance> getCurrentPartyBalanceListFromDB();

    List<PartyBalance> getPartyBalanceListFromDB(Party party);

    PartyBalance findById(Long id);

    List<PartyBalance> addBalanceToBalanceList(PartyBalance balance);

    Party getCurrentParty();

    PartyBalance attachBalanceWithParty(PartyBalance balance);

//    List<PartyBalance> attachBalanceWithParty(List<PartyBalance> balances);

//    List<PartyBalance> findAllByParty(Party party);

    // PartyBalance prepareCreate();

    PartyBalance save(PartyBalance partyBalance);

    List<PartyBalance> save(Set<PartyBalance> partyBalances);

    void delete(PartyBalance partyBalance);

    void delete(Set<PartyBalance> partyBalances);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<PartyBalance> partyBalances);

}
