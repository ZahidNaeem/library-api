package org.zahid.apps.web.pos.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private Long organizationCode;
    private String organizationName;
}
