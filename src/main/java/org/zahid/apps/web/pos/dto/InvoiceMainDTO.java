package org.zahid.apps.web.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.pos.entity.NavigationDtl;
import org.zahid.apps.web.pos.model.InvoiceMainModel;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceMainDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private NavigationDtl navigationDtl;
  private InvoiceMainModel invoice;
}
