package org.zahid.apps.web.pos.enumeration;

import org.zahid.apps.web.pos.exception.PartyTypeNotFoundException;

import java.util.Arrays;

public enum PartyType {
  SUPPLIER("Supplier"),
  BUYER("Buyer");

  private String value;

  PartyType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public PartyType fromValue(final String value) {
    return Arrays.stream(PartyType.values()).filter(partyType -> value.equals(partyType))
        .findFirst()
        .orElseThrow(() -> new PartyTypeNotFoundException("Party type '" + value + "' not found"));
  }
}
