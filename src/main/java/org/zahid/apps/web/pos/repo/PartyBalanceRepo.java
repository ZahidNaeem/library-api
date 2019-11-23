package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;

import java.util.List;

@Repository
public interface PartyBalanceRepo extends JpaRepository<PartyBalance, Long> {
    List<PartyBalance> findAllByParty(Party party);

    Long generateID();
}
