package org.zahid.apps.web.pos.entity;

import lombok.*;

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
