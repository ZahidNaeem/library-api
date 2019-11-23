package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.Party;

@Repository
public interface PartyRepo extends JpaRepository<Party, Long> {
    Long generateID();
}
