package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = BookTransHeaderEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "headerId")
@Entity
@Table(name = "book_trans_header", schema = "library", catalog = "")
public class BookTransHeaderEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "header_id")
    private Long headerId;

    @NotNull
    @Column(name = "trans_type")
    private String transType;

    @NotNull
    @Column(name = "trans_date")
    private Date transDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private ReaderEntity reader;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bookTransHeader")
    private List<BookTransLineEntity> bookTransLines;
}
