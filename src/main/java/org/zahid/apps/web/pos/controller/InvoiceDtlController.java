package org.zahid.apps.web.pos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.entity.InvoiceDtl;
import org.zahid.apps.web.pos.mapper.InvoiceDtlMapper;
import org.zahid.apps.web.pos.model.InvoiceDtlModel;
import org.zahid.apps.web.pos.service.InvoiceDtlService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/invoiceDtl")
public class InvoiceDtlController {

  private static final Logger LOG = LogManager.getLogger(InvoiceDtlController.class);

  @Autowired
  private InvoiceDtlService invoiceDtlService;

  @Autowired
  private InvoiceDtlMapper mapper;

  @GetMapping("all")
  public List<InvoiceDtlModel> findAll() {
    final List<InvoiceDtlModel> models = new ArrayList<>();
    invoiceDtlService.findAll().forEach(dtl -> {
      models.add(mapper.fromInvoiceDtl(dtl));
    });
    return models;
  }

  @GetMapping("{id}")
  public InvoiceDtlModel findById(@PathVariable("id") final Long id) {
    return mapper.fromInvoiceDtl(invoiceDtlService.findById(id));
  }

  @GetMapping("inv/{invNum}")
  public List<InvoiceDtlModel> findByInvoiceId(@PathVariable("invNum") final Long invNum) {
    return mapper.mapInvoiceDtlsToInvoiceDtlModels(invoiceDtlService.findByInvoice(invNum));
  }

  @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceDtlModel save(@RequestBody final InvoiceDtlModel model) {
        /*if (null == invoiceDtl.getInvoiceDtlId()) {
            invoiceDtl.setInvoiceDtlId(invoiceDtlService.generateID() >= (findAll().size() + 1) ? invoiceDtlService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceDtl dtl = invoiceDtlService.save(mapper.toInvoiceDtl(model));
    return mapper.fromInvoiceDtl(dtl);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<InvoiceDtl> saveAll(@RequestBody final Set<InvoiceDtlModel> models) {
    final Set<InvoiceDtl> invoiceDtls = new HashSet<>();
    models.forEach(model -> {
      final InvoiceDtl invoiceDtl = mapper.toInvoiceDtl(model);
      invoiceDtls.add(invoiceDtl);
    });
    return invoiceDtlService.save(invoiceDtls);
  }

  @DeleteMapping("delete/{id}")
  public boolean deleteById(@PathVariable("id") final Long id) {
    if (!invoiceDtlService.exists(id)) {
      throw new IllegalArgumentException("InvoiceDtl with id: " + id + " does not exist");
    } else {
      try {
        invoiceDtlService.deleteById(id);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }

  @DeleteMapping(path = "delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean delete(@RequestBody final InvoiceDtlModel model) {
    if (null == model || null == model.getInvDtlNum() || !invoiceDtlService
        .exists(model.getInvDtlNum())) {
      throw new IllegalArgumentException("InvoiceDtl does not exist");
    } else {
      try {
        invoiceDtlService.delete(mapper.toInvoiceDtl(model));
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
  }
}
