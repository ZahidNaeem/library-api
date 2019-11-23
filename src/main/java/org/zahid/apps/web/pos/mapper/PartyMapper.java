package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.InvoiceMain;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.model.InvoiceMainModel;
import org.zahid.apps.web.pos.model.PartyBalanceModel;
import org.zahid.apps.web.pos.model.PartyModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PartyMapper {

  @Autowired
  public InvoiceMainMapper invoiceMainMapper;

  @Autowired
  public PartyBalanceMapper partyBalanceMapper;

  @Mapping(target = "invoiceMains", expression = "java(party != null ? mapInvoicesToInvModels(party.getInvoiceMains()) : null)")
  @Mapping(target = "partyBalances", expression = "java(party != null ? mapBalancesToBalanceModels(party.getPartyBalances()) : null)")
  public abstract PartyModel fromParty(final Party party);

  @Mapping(target = "invoiceMains", expression = "java(model != null ? mapInvModelsToInvoices(model.getInvoiceMains()) : null)")
  @Mapping(target = "partyBalances", expression = "java(model != null ? mapBalanceModelsToBalances(model.getPartyBalances()) : null)")
  public abstract Party toParty(final PartyModel model);

  public List<InvoiceMainModel> mapInvoicesToInvModels(final List<InvoiceMain> invoices) {
    return invoiceMainMapper.mapInvoicesToInvoiceModels(invoices);
  }

  public List<InvoiceMain> mapInvModelsToInvoices(final List<InvoiceMainModel> models) {
    return invoiceMainMapper.mapInvoiceModelsToInvoices(models);
  }

  public List<PartyBalanceModel> mapBalancesToBalanceModels(final List<PartyBalance> balances) {
    return partyBalanceMapper.mapBalancesToBalanceModels(balances);
  }

  public List<PartyBalance> mapBalanceModelsToBalances(final List<PartyBalanceModel> models) {
    return partyBalanceMapper.mapBalanceModelsToBalances(models);
  }

  public List<PartyModel> mapPartiesToPartyModels(final List<Party> parties) {
    if (CollectionUtils.isEmpty(parties)) {
      return new ArrayList<>();
    }
    final List<PartyModel> models = new ArrayList<>();
    parties.forEach(party -> {
      models.add(this.fromParty(party));
    });
    return models;
  }

  public List<Party> mapPartyModelsToParties(final List<PartyModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<Party> parties = new ArrayList<>();
    models.forEach(model -> {
      parties.add(this.toParty(model));
    });
    return parties;
  }

}
