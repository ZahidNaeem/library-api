package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.InvoiceDtl;
import org.zahid.apps.web.pos.model.InvoiceDtlModel;
import org.zahid.apps.web.pos.service.InvoiceMainService;
import org.zahid.apps.web.pos.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InvoiceDtlMapper {

  @Autowired
  protected ItemService itemService;

  @Autowired
  protected InvoiceMainService invoiceMainService;

  @Mapping(target = "item", expression = "java(dtl != null && dtl.getItem() != null ? dtl.getItem().getItemCode() : null)")
  @Mapping(target = "invoiceMain", expression = "java(dtl != null && dtl.getInvoiceMain() != null ? dtl.getInvoiceMain().getInvNum() : null)")
  public abstract InvoiceDtlModel fromInvoiceDtl(final InvoiceDtl dtl);

  @Mapping(target = "item", expression = "java(model != null && model.getItem() != null ? itemService.findById(model.getItem()) : null)")
  @Mapping(target = "invoiceMain", expression = "java(model != null && model.getInvoiceMain() != null ? invoiceMainService.findById(model.getInvoiceMain()) : null)")
  public abstract InvoiceDtl toInvoiceDtl(final InvoiceDtlModel model);

  public List<InvoiceDtlModel> mapInvoiceDtlsToInvoiceDtlModels(
      final List<InvoiceDtl> InvoiceDtls) {
    if (CollectionUtils.isEmpty(InvoiceDtls)) {
      return new ArrayList<>();
    }
    final List<InvoiceDtlModel> models = new ArrayList<>();
    InvoiceDtls.forEach(InvoiceDtl -> {
      models.add(this.fromInvoiceDtl(InvoiceDtl));
    });
    return models;
  }

  public List<InvoiceDtl> mapInvoiceDtlModelsToInvoiceDtls(final List<InvoiceDtlModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<InvoiceDtl> InvoiceDtls = new ArrayList<>();
    models.forEach(model -> {
      InvoiceDtls.add(this.toInvoiceDtl(model));
    });
    return InvoiceDtls;
  }
}
