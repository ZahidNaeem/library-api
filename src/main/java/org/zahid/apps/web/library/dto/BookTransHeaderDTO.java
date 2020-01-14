package org.zahid.apps.web.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.NavigationDtl;
import org.zahid.apps.web.library.model.BookTransHeaderModel;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransHeaderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private NavigationDtl navigationDtl;
    private BookTransHeaderModel bookTransHeader;
}
