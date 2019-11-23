package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.InvoiceDtl;
import org.zahid.apps.web.pos.entity.InvoiceMain;
import org.zahid.apps.web.pos.model.InvoiceDtlModel;
import org.zahid.apps.web.pos.model.InvoiceMainModel;
import org.zahid.apps.web.pos.service.InvoiceMainService;
import org.zahid.apps.web.pos.service.PartyService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InvoiceMainMapper {

  @Autowired
  public PartyService partyService;

  @Autowired
  public InvoiceMainService invoiceMainService;

  @Autowired
  public InvoiceDtlMapper invoiceDtlMapper;

  @Mapping(target = "party", expression = "java(invoiceMain != null && invoiceMain.getParty() != null ? invoiceMain.getParty().getPartyCode() : null)")
  @Mapping(target = "refInvoice", expression = "java(invoiceMain != null && invoiceMain.getRefInvoice() != null ? invoiceMain.getRefInvoice().getInvNum() : null)")
  @Mapping(target = "invoiceDtls", expression = "java(invoiceMain != null ? mapInvoiceDtlsToInvoiceDtlModels(invoiceMain.getInvoiceDtls()) : null)")
  public abstract InvoiceMainModel fromInvoiceMain(final InvoiceMain invoiceMain);

  @Mapping(target = "party", expression = "java(model != null && model.getParty() != null ? partyService.findById(model.getParty()) : null)")
  @Mapping(target = "refInvoice", expression = "java(model != null && model.getRefInvoice() != null ? invoiceMainService.findById(model.getRefInvoice()) : null)")
  @Mapping(target = "invoiceDtls", expression = "java(model != null ? mapInvoiceDtlModelsToInvoiceDtls(model.getInvoiceDtls()) : null)")
  public abstract InvoiceMain toInvoiceMain(final InvoiceMainModel model);

  public List<InvoiceDtl> mapInvoiceDtlModelsToInvoiceDtls(List<InvoiceDtlModel> models) {
    return invoiceDtlMapper.mapInvoiceDtlModelsToInvoiceDtls(models);
  }

  public List<InvoiceDtlModel> mapInvoiceDtlsToInvoiceDtlModels(List<InvoiceDtl> dtls) {
    return invoiceDtlMapper.mapInvoiceDtlsToInvoiceDtlModels(dtls);
  }

  public List<InvoiceMainModel> mapInvoicesToInvoiceModels(final List<InvoiceMain> invoices) {
    if (CollectionUtils.isEmpty(invoices)) {
      return new ArrayList<>();
    }
    final List<InvoiceMainModel> models = new ArrayList<>();
    invoices.forEach(invoice -> {
      models.add(this.fromInvoiceMain(invoice));
    });
    return models;
  }

  public List<InvoiceMain> mapInvoiceModelsToInvoices(final List<InvoiceMainModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<InvoiceMain> invoices = new ArrayList<>();
    models.forEach(model -> {
      invoices.add(this.toInvoiceMain(model));
    });
    return invoices;
  }
}
