package org.zahid.apps.web.pos.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.zahid.apps.web.pos.dto.InvoiceMainDTO;
import org.zahid.apps.web.pos.entity.InvoiceMain;
import org.zahid.apps.web.pos.entity.NavigationDtl;
import org.zahid.apps.web.pos.mapper.InvoiceMainMapper;
import org.zahid.apps.web.pos.model.InvoiceMainModel;
import org.zahid.apps.web.pos.service.InvoiceMainService;

import java.util.HashSet;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/invoice")
public class InvoiceMainController {

  private static final Logger LOG = LogManager.getLogger(InvoiceMainController.class);

  @Autowired
  private InvoiceMainService invoiceMainService;

  @Autowired
  private InvoiceMainMapper mapper;

  private final int[] poIndx = {-1};
  private final int[] piIndx = {-1};
  private final int[] priIndx = {-1};
  private final int[] soIndx = {-1};
  private final int[] siIndx = {-1};
  private final int[] sriIndx = {-1};

  private static void setInvoiceForDtls(final InvoiceMain invoice) {
    if (CollectionUtils.isNotEmpty(invoice.getInvoiceDtls())) {
      invoice.getInvoiceDtls().forEach(dtl -> {
        dtl.setInvoiceMain(invoice);
      });
    }
  }

  @GetMapping("all")
  public List<InvoiceMainModel> findAll() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAll());
  }

  @GetMapping("po/all")
  public List<InvoiceMainModel> findAllPOs() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllPOs());
  }

  @GetMapping("pi/all")
  public List<InvoiceMainModel> findAllPurchaseInvoices() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllPurchaseInvoices());
  }

  @GetMapping("pri/all")
  public List<InvoiceMainModel> findAllPurchaseReturnInvoices() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllPurchaseReturnInvoices());
  }

  @GetMapping("so/all")
  public List<InvoiceMainModel> findAllSOs() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllSOs());
  }

  @GetMapping("si/all")
  public List<InvoiceMainModel> findAllSaleInvoices() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllSaleInvoices());
  }

  @GetMapping("sri/all")
  public List<InvoiceMainModel> findAllSaleReturnInvoices() {
    return mapper.mapInvoicesToInvoiceModels(invoiceMainService.findAllSaleReturnInvoices());
  }

  @GetMapping("{po/id}")
  public InvoiceMainDTO findPOById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    poIndx[0] = findAllPOs().indexOf(model);
    LOG.info("Index in findById(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @GetMapping("{pi/id}")
  public InvoiceMainDTO findPIById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    piIndx[0] = findAllPurchaseInvoices().indexOf(model);
    LOG.info("Index in findById(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @GetMapping("{pri/id}")
  public InvoiceMainDTO findPRIById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    priIndx[0] = findAllPurchaseReturnInvoices().indexOf(model);
    LOG.info("Index in findById(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @GetMapping("{so/id}")
  public InvoiceMainDTO findSOById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    soIndx[0] = findAllSOs().indexOf(model);
    LOG.info("Index in findById(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @GetMapping("{si/id}")
  public InvoiceMainDTO findSIById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    siIndx[0] = findAllSaleInvoices().indexOf(model);
    LOG.info("Index in findById(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @GetMapping("{sri/id}")
  public InvoiceMainDTO findSRIById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    sriIndx[0] = findAllSaleReturnInvoices().indexOf(model);
    LOG.info("Index in findById(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @GetMapping("{id}")
  public InvoiceMainDTO findById(@PathVariable("id") final Long id) {
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainService.findById(id));
    poIndx[0] = findAll().indexOf(model);
    LOG.info("Index in findById(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAll(), poIndx[0]);
  }

  @GetMapping("po/first")
  public InvoiceMainDTO firstPO() {
    poIndx[0] = 0;
    LOG.info("Index in first(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @GetMapping("pi/first")
  public InvoiceMainDTO firstPI() {
    piIndx[0] = 0;
    LOG.info("Index in first(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @GetMapping("pri/first")
  public InvoiceMainDTO firstPRI() {
    priIndx[0] = 0;
    LOG.info("Index in first(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @GetMapping("so/first")
  public InvoiceMainDTO firstSO() {
    soIndx[0] = 0;
    LOG.info("Index in first(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @GetMapping("si/first")
  public InvoiceMainDTO firstSI() {
    siIndx[0] = 0;
    LOG.info("Index in first(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @GetMapping("sri/first")
  public InvoiceMainDTO firstSRI() {
    sriIndx[0] = 0;
    LOG.info("Index in first(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @GetMapping("po/previous")
  public InvoiceMainDTO previousPO() {
    poIndx[0]--;
    LOG.info("Index in previous(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @GetMapping("pi/previous")
  public InvoiceMainDTO previousPI() {
    piIndx[0]--;
    LOG.info("Index in previous(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @GetMapping("pri/previous")
  public InvoiceMainDTO previousPRI() {
    priIndx[0]--;
    LOG.info("Index in previous(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @GetMapping("so/previous")
  public InvoiceMainDTO previousSO() {
    soIndx[0]--;
    LOG.info("Index in previous(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @GetMapping("si/previous")
  public InvoiceMainDTO previousSI() {
    siIndx[0]--;
    LOG.info("Index in previous(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @GetMapping("sri/previous")
  public InvoiceMainDTO previousSRI() {
    sriIndx[0]--;
    LOG.info("Index in previous(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @GetMapping("po/next")
  public InvoiceMainDTO nextPO() {
    poIndx[0]++;
    LOG.info("Index in next(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @GetMapping("pi/next")
  public InvoiceMainDTO nextPI() {
    piIndx[0]++;
    LOG.info("Index in next(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @GetMapping("pri/next")
  public InvoiceMainDTO nextPRI() {
    priIndx[0]++;
    LOG.info("Index in next(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @GetMapping("so/next")
  public InvoiceMainDTO nextSO() {
    soIndx[0]++;
    LOG.info("Index in next(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @GetMapping("si/next")
  public InvoiceMainDTO nextSI() {
    siIndx[0]++;
    LOG.info("Index in next(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @GetMapping("sri/next")
  public InvoiceMainDTO nextSRI() {
    sriIndx[0]++;
    LOG.info("Index in next(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @GetMapping("po/last")
  public InvoiceMainDTO lastPO() {
    poIndx[0] = findAllPOs().size() - 1;
    LOG.info("Index in last(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @GetMapping("pi/last")
  public InvoiceMainDTO lastPI() {
    piIndx[0] = findAllPurchaseInvoices().size() - 1;
    LOG.info("Index in last(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @GetMapping("pri/last")
  public InvoiceMainDTO lastPRI() {
    priIndx[0] = findAllPurchaseReturnInvoices().size() - 1;
    LOG.info("Index in last(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @GetMapping("so/last")
  public InvoiceMainDTO lastSO() {
    soIndx[0] = findAllSOs().size() - 1;
    LOG.info("Index in last(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @GetMapping("si/last")
  public InvoiceMainDTO lastSI() {
    siIndx[0] = findAllSaleInvoices().size() - 1;
    LOG.info("Index in last(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @GetMapping("sri/last")
  public InvoiceMainDTO lastSRI() {
    sriIndx[0] = findAllSaleReturnInvoices().size() - 1;
    LOG.info("Index in last(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @PostMapping(path = "po/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO savePO(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().size() + 1) ? invoiceMainService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    poIndx[0] = this.findAllPOs().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", poIndx[0]);
    return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
  }

  @PostMapping(path = "pi/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO savePI(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().size() + 1) ? invoiceMainService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    piIndx[0] = this.findAllPurchaseInvoices().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", piIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
  }

  @PostMapping(path = "pri/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO savePRI(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().size() + 1) ? invoiceMainService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    priIndx[0] = this.findAllPurchaseReturnInvoices().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", priIndx[0]);
    return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
  }

  @PostMapping(path = "so/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO saveSO(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().size() + 1) ? invoiceMainService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    soIndx[0] = this.findAllSOs().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", soIndx[0]);
    return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
  }

  @PostMapping(path = "si/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO saveSI(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().size() + 1) ? invoiceMainService.generateID() : (findAll().size() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    siIndx[0] = this.findAllSaleInvoices().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", siIndx[0]);
    return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
  }

  @PostMapping(path = "sri/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO saveSRI(@RequestBody final InvoiceMainModel invoiceMainModel) {
        /*if (null == dto.getInvoiceMainCode()) {
            dto.setInvoiceMainCode(invoiceMainService.generateID() >= (findAll().srize() + 1) ? invoiceMainService.generateID() : (findAll().srize() + 1));
        }*/
    final InvoiceMain invoice = mapper.toInvoiceMain(invoiceMainModel);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    setInvoiceForDtls(invoice);
    final InvoiceMain invoiceMainSaved = invoiceMainService
        .save(invoice);
    final InvoiceMainModel model = mapper.fromInvoiceMain(invoiceMainSaved);
    sriIndx[0] = this.findAllSaleReturnInvoices().indexOf(model);
    LOG.info("Index in saveInvoiceMain(): {}", sriIndx[0]);
    return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
  }

  @PostMapping(path = "saveAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<InvoiceMain> saveAll(@RequestBody final List<InvoiceMainModel> invoiceMainModels) {
    final List<InvoiceMain> invoices = mapper.mapInvoiceModelsToInvoices(invoiceMainModels);
    //    Below line added, because when converted from model to invoice, there is no invoice set in invoiceDtl list.
    invoices.forEach(invoice -> {
      setInvoiceForDtls(invoice);
    });
    return invoiceMainService
        .save(new HashSet<>(invoices));
  }

  @DeleteMapping("/delete/po/{id}")
  public InvoiceMainDTO deletePOById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        poIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", poIndx[0]);
        return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/po", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deletePO(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", poIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        poIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", poIndx[0]);
        return getInvoiceMainDTO(findAllPOs(), poIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping("/delete/pi/{id}")
  public InvoiceMainDTO deletePIById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        piIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", piIndx[0]);
        return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/pi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deletePI(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", piIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        piIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", piIndx[0]);
        return getInvoiceMainDTO(findAllPurchaseInvoices(), piIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping("/delete/pri/{id}")
  public InvoiceMainDTO deletePRIById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        priIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", priIndx[0]);
        return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/pri", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deletePRI(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", priIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        priIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", priIndx[0]);
        return getInvoiceMainDTO(findAllPurchaseReturnInvoices(), priIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping("/delete/so/{id}")
  public InvoiceMainDTO deleteSOById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        soIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", soIndx[0]);
        return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/so", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deleteSO(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", soIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        soIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", soIndx[0]);
        return getInvoiceMainDTO(findAllSOs(), soIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping("/delete/si/{id}")
  public InvoiceMainDTO deleteSIById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        siIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", siIndx[0]);
        return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/si", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deleteSI(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", siIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        siIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", siIndx[0]);
        return getInvoiceMainDTO(findAllSaleInvoices(), siIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping("/delete/sri/{id}")
  public InvoiceMainDTO deleteSRIById(@PathVariable("id") final Long id) {
    if (!invoiceMainService.exists(id)) {
      throw new IllegalArgumentException("Invoice with id: " + id + " does not exist");
    } else {
      try {
        invoiceMainService.deleteById(id);
        sriIndx[0]--;
        LOG.info("Index in deleteInvoiceMainById(): {}", sriIndx[0]);
        return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @DeleteMapping(path = "delete/sri", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public InvoiceMainDTO deleteSRI(@RequestBody final InvoiceMainModel invoiceMainModel) {
    LOG.info("Index: {}", sriIndx[0]);
    if (null == invoiceMainModel || null == invoiceMainModel.getInvNum() || !invoiceMainService
        .exists(invoiceMainModel.getInvNum())) {
      throw new IllegalArgumentException(
          "Invoice with id: " + invoiceMainModel.getInvNum() + " does not exist");
    } else {
      try {
        invoiceMainService
            .delete(mapper.toInvoiceMain(invoiceMainModel));
        sriIndx[0]--;
        LOG.info("Index in deleteInvoiceMain(): {}", sriIndx[0]);
        return getInvoiceMainDTO(findAllSaleReturnInvoices(), sriIndx[0]);
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

  private final InvoiceMainDTO getInvoiceMainDTO(List<InvoiceMainModel> models, int indx) {
    if (indx < 0 || indx > models.size() - 1) {
      LOG.info("Index in getInvoiceMainDTO(): {}", indx);
      throw new IndexOutOfBoundsException();
    } else {
      final NavigationDtl dtl = resetNavigation();
      final InvoiceMainModel model = models.get(indx);
      final InvoiceMainDTO invoiceMainDTO = new InvoiceMainDTO();
      invoiceMainDTO.setInvoice(model);
      if (indx > 0) {
        dtl.setFirst(false);
      }
      if (indx < models.size() - 1) {
        dtl.setLast(false);
      }
      invoiceMainDTO.setNavigationDtl(dtl);
      return invoiceMainDTO;
    }
  }
}
