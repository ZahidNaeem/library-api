package org.zahid.apps.web.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.entity.InvoiceDtl;
import org.zahid.apps.web.pos.exception.InvoiceDtlNotFoundException;
import org.zahid.apps.web.pos.exception.ItemNotFoundException;
import org.zahid.apps.web.pos.repo.InvoiceDtlRepo;
import org.zahid.apps.web.pos.service.InvoiceDtlService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceDtlServiceImpl implements InvoiceDtlService {

    @Autowired
    private InvoiceDtlRepo repo;

    @Override
    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    @Override
    public List<InvoiceDtl> findAll() {
        return repo.findAll();
    }

    @Override
    public InvoiceDtl findById(Long id) {
        return Optional.ofNullable(repo.findById(id))
                .map(invoiceDtl -> invoiceDtl.get())
                .orElseThrow(() -> new InvoiceDtlNotFoundException("Invoice detail with id " + id + " not found"));
        /*final Optional<InvoiceDtl> stock = repo.findById(id);
        if (stock.isPresent()) {
            return stock.get();
        }
        throw new InvoiceDtlNotFoundException("Invoice detail with id " + id + " not found");*/
    }

    @Override
    public List<InvoiceDtl> findByInvoice(Long invNum) {
        return repo.findByInvoice(invNum);
    }

    @Override
    public InvoiceDtl save(InvoiceDtl invoiceDtl) {
        return repo.save(invoiceDtl);
    }

    @Override
    public List<InvoiceDtl> save(Set<InvoiceDtl> invoiceDtls) {
        return repo.saveAll(invoiceDtls);
    }

    @Override
    public void delete(InvoiceDtl invoiceDtl) {
        repo.delete(invoiceDtl);
    }

    @Override
    public void delete(Set<InvoiceDtl> invoiceDtls) {
        repo.deleteAll(invoiceDtls);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        repo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<InvoiceDtl> invoiceDtls) {
        repo.deleteInBatch(invoiceDtls);
    }
}
