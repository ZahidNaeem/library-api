package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.InvoiceMain;

import java.util.List;

@Repository
public interface InvoiceMainRepo extends JpaRepository<InvoiceMain, Long> {

    List<InvoiceMain> findAllPO();

    List<InvoiceMain> findAllPI();

    List<InvoiceMain> findAllPRI();

    List<InvoiceMain> findAllSO();

    List<InvoiceMain> findAllSI();

    List<InvoiceMain> findAllSRI();
}
