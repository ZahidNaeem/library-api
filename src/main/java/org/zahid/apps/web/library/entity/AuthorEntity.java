package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = AuthorEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "authorId")
@Entity
@Table(name = "author", schema = "library", catalog = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"author_name"})
})
public class AuthorEntity extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    private Long authorId;

    @NotNull
    @Column(name = "author_name")
    private String authorName;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private List<BookEntity> books;
}
