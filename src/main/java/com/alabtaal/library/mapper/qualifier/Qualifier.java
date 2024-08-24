package com.alabtaal.library.mapper.qualifier;

import static com.alabtaal.library.util.LambdaExceptionUtil.rethrowFunction;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.entity.RoleEntity;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.mapper.BookTransHeaderMapper;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.mapper.RackMapper;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.BookTransHeaderModel;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.model.RackModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.service.AuthorService;
import com.alabtaal.library.service.BookService;
import com.alabtaal.library.service.BookTransHeaderService;
import com.alabtaal.library.service.PublisherService;
import com.alabtaal.library.service.RackService;
import com.alabtaal.library.service.ReaderService;
import com.alabtaal.library.service.ResearcherService;
import com.alabtaal.library.service.RoleService;
import com.alabtaal.library.service.ShelfService;
import com.alabtaal.library.service.SubjectService;
import com.alabtaal.library.service.VolumeService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Qualifier {

  private final BookMapper bookMapper;
  private final BookService bookService;
  private final VolumeService volumeService;
  private final RackService rackService;
  private final AuthorService authorService;
  private final SubjectService subjectService;
  private final PublisherService publisherService;
  private final ResearcherService researcherService;
  private final ShelfService shelfService;
  private final VolumeMapper volumeMapper;
  private final BookTransLineMapper bookTransLineMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private final RackMapper rackMapper;
  private final BookTransHeaderMapper bookTransHeaderMapper;
  private BookTransHeaderService bookTransHeaderService;
  private final ReaderService readerService;

  @Named("readerETM")
  public UUID readerETM(final ReaderEntity reader) {
    return reader.getId();
  }

  @Named("readerMTE")
  public ReaderEntity readerMTE(final UUID reader) {
    if (reader != null) {
      assert readerService != null;
      return readerService.findById(reader);
    } else {
      return null;
    }
  }


  @Named("bookTransLineModels")
  public List<BookTransLineModel> bookTransLineModels(final List<BookTransLineEntity> bookTransLines) {
    assert bookTransLineMapper != null;
    return bookTransLineMapper.toModels(bookTransLines);
  }

  @Named("bookTransLineEntities")
  public List<BookTransLineEntity> bookTransLineEntities(final List<BookTransLineModel> bookTransLines) {
    assert bookTransLineMapper != null;
    return bookTransLineMapper.toEntities(bookTransLines);
  }

  @Named("shelfETM")
  public UUID shelfETM(final ShelfEntity shelf) {
    return shelf.getId();
  }

  @Named("shelfETD")
  public String shelfETD(final ShelfEntity shelf) {
    return shelf.getName();
  }

  @Named("shelfMTE")
  public ShelfEntity shelfMTE(final UUID shelf) {
    if (shelf != null) {
      assert shelfService != null;
      return shelfService.findById(shelf);
    } else {
      return null;
    }
  }

  @Named("volumesETM")
  public List<VolumeModel> volumesETM(final List<VolumeEntity> volumes) {
    assert volumeMapper != null;
    return volumeMapper.toModels(volumes);
  }

  @Named("volumesMTE")
  public List<VolumeEntity> volumesMTE(final List<VolumeModel> volumes) {
    assert volumeMapper != null;
    return volumeMapper.toEntities(volumes);
  }

  @Named("bookTransHeaderETM")
  public UUID bookTransHeaderETM(final BookTransHeaderEntity header) {
    return header.getId();
  }

  @Named("bookTransHeaderMTE")
  public BookTransHeaderEntity bookTransHeaderMTE(final UUID header) {
    return header != null ? bookTransHeaderService.findById(header) : null;
  }

  @Named("bookTransHeaderModels")
  public List<BookTransHeaderModel> bookTransHeaderModels(final List<BookTransHeaderEntity> entities) {
    assert bookTransHeaderMapper != null;
    return bookTransHeaderMapper.toModels(entities);
  }

  @Named("bookTransHeaderEntities")
  public List<BookTransHeaderEntity> bookTransHeaderEntities(final List<BookTransHeaderModel> models) {
    assert bookTransHeaderMapper != null;
    return bookTransHeaderMapper.toEntities(models);
  }

  @Named("parentSubjectETM")
  public UUID parentSubjectETM(final SubjectEntity parentSubjectEntity) {
    return parentSubjectEntity.getId();
  }

  @Named("parentSubjectMTE")
  public SubjectEntity parentSubjectMTE(final UUID parentSubjectId) {
    if (parentSubjectId != null) {
      assert subjectService != null;
      return subjectService.findById(parentSubjectId);
    } else {
      return null;
    }
  }

  @Named("rackModels")
  public List<RackModel> rackModels(final List<RackEntity> racks) {
    assert rackMapper != null;
    return rackMapper.toModels(racks);
  }

  @Named("rackEntities")
  public List<RackEntity> rackEntities(final List<RackModel> racks) {
    assert rackMapper != null;
    return rackMapper.toEntities(racks);
  }

  @Named(value = "password")
  public String encodePassword(final String password) {
    assert passwordEncoder != null;
    return passwordEncoder.encode(password);
  }

  @Named(value = "roles")
  public Set<RoleEntity> roles(final Set<RoleName> roles) throws ResourceNotFoundException {
    assert roleService != null;
    return Optional.ofNullable(roles)
        .orElseGet(Collections::emptySet)
        .stream()
        .map(rethrowFunction(roleService::findByNameThrowError))
        .collect(Collectors.toSet());
  }

  @Named("authorETM")
  public UUID authorETM(final AuthorEntity author) {
    return author.getId();
  }

  @Named("authorMTE")
  public AuthorEntity authorMTE(final UUID author) {
    if (author != null) {
      assert authorService != null;
      return authorService.findById(author);
    } else {
      return null;
    }
  }

  @Named("authorMTX")
  public String authorMTX(final UUID author) {
    if (author != null) {
      assert authorService != null;
      return Optional.ofNullable(authorService.findById(author))
          .map(AuthorEntity::getName)
          .orElse(null);
    } else {
      return null;
    }
  }

  @Named("subjectETM")
  public UUID subjectETM(final SubjectEntity subject) {
    return subject.getId();
  }

  @Named("subjectMTE")
  public SubjectEntity subjectMTE(final UUID subject) {
    if (subject != null) {
      assert subjectService != null;
      return subjectService.findById(subject);
    } else {
      return null;
    }
  }

  @Named("subjectMTX")
  public String subjectMTX(final UUID subject) {
    if (subject != null) {
      assert subjectService != null;
      return Optional.ofNullable(subjectService.findById(subject))
          .map(SubjectEntity::getName)
          .orElse(null);
    } else {
      return null;
    }
  }

  @Named("publisherETM")
  public UUID publisherETM(final PublisherEntity publisher) {
    return publisher.getId();
  }

  @Named("publisherMTE")
  public PublisherEntity publisherMTE(final UUID publisher) {
    if (publisher != null) {
      assert publisherService != null;
      return publisherService.findById(publisher);
    } else {
      return null;
    }
  }

  @Named("publisherMTX")
  public String publisherMTX(final UUID publisher) {
    if (publisher != null) {
      assert publisherService != null;
      return Optional.ofNullable(publisherService.findById(publisher))
          .map(PublisherEntity::getName)
          .orElse(null);
    } else {
      return null;
    }
  }

  @Named("researcherETM")
  public UUID researcherETM(final ResearcherEntity researcher) {
    return researcher.getId();
  }

  @Named("researcherMTE")
  public ResearcherEntity researcherMTE(final UUID researcher) {
    if (researcher != null) {
      assert researcherService != null;
      return researcherService.findById(researcher);
    } else {
      return null;
    }
  }

  @Named("researcherMTX")
  public String researcherMTX(final UUID researcher) {
    if (researcher != null) {
      assert researcherService != null;
      return Optional.ofNullable(researcherService.findById(researcher))
          .map(ResearcherEntity::getName)
          .orElse(null);
    } else {
      return null;
    }
  }

  @Named("volumeModels")
  public List<VolumeModel> volumeModels(final List<VolumeEntity> volumes) {
    assert volumeMapper != null;
    return volumeMapper.toModels(volumes);
  }

  @Named("volumeEntities")
  public List<VolumeEntity> volumeEntities(final List<VolumeModel> volumes) {
    assert volumeMapper != null;
    return volumeMapper.toEntities(volumes);
  }

  @Named("bookETM")
  public UUID bookETM(final BookEntity book) {
    return book.getId();
  }

  @Named("bookMTE")
  public BookEntity bookMTE(final UUID book) {
    if (book != null) {
      assert bookService != null;
      return bookService.findById(book);
    } else {
      return null;
    }
  }

  @Named("rackETM")
  public UUID rackETM(final RackEntity rack) {
    return rack.getId();
  }

  @Named("rackMTE")
  public RackEntity rackMTE(final UUID rack) {
    if (rack != null) {
      assert rackService != null;
      return rackService.findById(rack);
    } else {
      return null;
    }
  }

  @Named("volumeETM")
  public UUID volumeETM(final VolumeEntity volume) {
    return volume.getId();
  }

  @Named("volumeMTE")
  public VolumeEntity volumeMTE(final UUID volume) {
    if (volume != null) {
      assert volumeService != null;
      return volumeService.findById(volume);
    } else {
      return null;
    }
  }

  @Named("bookModels")
  public List<BookModel> bookModels(final List<BookEntity> books) {
    assert bookMapper != null;
    return bookMapper.toModels(books);
  }

  @Named("bookEntities")
  public List<BookEntity> bookEntities(final List<BookModel> books) {
    assert bookMapper != null;
    return bookMapper.toEntities(books);
  }
}
