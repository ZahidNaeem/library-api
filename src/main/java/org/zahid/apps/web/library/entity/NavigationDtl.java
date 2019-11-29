package org.zahid.apps.web.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigationDtl implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean first;
    private boolean last;
}
