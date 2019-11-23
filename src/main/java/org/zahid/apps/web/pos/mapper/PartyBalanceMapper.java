package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.model.PartyBalanceModel;
import org.zahid.apps.web.pos.service.PartyService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PartyBalanceMapper {

  @Autowired
  public PartyService partyService;

  @Mapping(target = "party", expression = "java(partyBalance != null && partyBalance.getParty() != null ? partyBalance.getParty().getPartyCode() : null)")
  public abstract PartyBalanceModel fromPartyBalance(final PartyBalance partyBalance);

  @Mapping(target = "party", expression = "java(model != null && model.getParty() != null ? partyService.findById(model.getParty()) : null)")
  public abstract PartyBalance toPartyBalance(final PartyBalanceModel model);

  public List<PartyBalanceModel> mapBalancesToBalanceModels(
      final List<PartyBalance> partyBalances) {
    if (CollectionUtils.isEmpty(partyBalances)) {
      return new ArrayList<>();
    }
    final List<PartyBalanceModel> models = new ArrayList<>();
    partyBalances.forEach(partyBalance -> {
      models.add(this.fromPartyBalance(partyBalance));
    });
    return models;
  }

  public List<PartyBalance> mapBalanceModelsToBalances(final List<PartyBalanceModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<PartyBalance> partyBalances = new ArrayList<>();
    models.forEach(model -> {
      partyBalances.add(this.toPartyBalance(model));
    });
    return partyBalances;
  }
}
