package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = BookTransLineEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "lineId")
@Entity
@Table(name = "book_trans_line", schema = "library", catalog = "")
public class BookTransLineEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "line_id")
    private Long lineId;

    @NotNull
    @Column(name = "row_key")
    private String rowKey;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "volume_id")
    private VolumeEntity volume;

    //    @NotNull
    @ManyToOne
    @JoinColumn(name = "header_id")
    private BookTransHeaderEntity bookTransHeader;
}
