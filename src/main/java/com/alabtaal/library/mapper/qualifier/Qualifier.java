package com.alabtaal.library.mapper.qualifier;

import static com.alabtaal.library.util.LambdaExceptionUtil.rethrowFunction;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.BookTransHeaderEntity;
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
import com.alabtaal.library.repo.AuthorRepo;
import com.alabtaal.library.repo.BookRepo;
import com.alabtaal.library.repo.BookTransHeaderRepo;
import com.alabtaal.library.repo.PublisherRepo;
import com.alabtaal.library.repo.RackRepo;
import com.alabtaal.library.repo.ReaderRepo;
import com.alabtaal.library.repo.ResearcherRepo;
import com.alabtaal.library.repo.ShelfRepo;
import com.alabtaal.library.repo.SubjectRepo;
import com.alabtaal.library.repo.VolumeRepo;
import com.alabtaal.library.service.RoleService;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class Qualifier {

  private final BookRepo bookRepo;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private final ResearcherRepo researcherRepo;
  private final ReaderRepo readerRepo;
  private final ShelfRepo shelfRepo;
  private final BookTransHeaderRepo bookTransHeaderRepo;
  private final SubjectRepo subjectRepo;
  private final AuthorRepo authorRepo;
  private final PublisherRepo publisherRepo;
  private final RackRepo rackRepo;
  private final VolumeRepo volumeRepo;

  @Named("readerMTE")
  public ReaderEntity readerMTE(final UUID reader) {
    return readerRepo != null && reader != null ? readerRepo.findById(reader).orElse(null) : null;
  }

  @Named("shelfMTE")
  public ShelfEntity shelfMTE(final UUID shelf) {
    return shelfRepo != null && shelf != null ? shelfRepo.findById(shelf).orElse(null) : null;
  }

  @Named("bookTransHeaderMTE")
  public BookTransHeaderEntity bookTransHeaderMTE(final UUID header) {
    return header != null ? bookTransHeaderRepo.findById(header).orElse(null) : null;
  }

  @Named("subjectMTE")
  public SubjectEntity subjectMTE( final UUID subject) {
    return subjectRepo != null && subject != null ? subjectRepo.findById(subject).orElse(null) : null;
  }

  @Named("subjectMTX")
  public String subjectMTX(final UUID subject) {
    return Optional.ofNullable(subjectMTE(subject))
        .map(SubjectEntity::getName)
        .orElse(null);
  }

  @Named("parentSubjectMTE")
  public SubjectEntity parentSubjectMTE( final UUID parentSubjectId) {
    return subjectRepo != null && parentSubjectId != null ? subjectRepo.findById(parentSubjectId).orElse(null) : null;
  }

  @Named(value = "password")
  public String encodePassword(final String password) {
    return passwordEncoder.encode(password);
  }

  @Named(value = "roles")
  public Set<RoleEntity> roles(final Set<RoleName> roles) throws ResourceNotFoundException {
    return Optional.ofNullable(roles)
        .orElseGet(Collections::emptySet)
        .stream()
        .map(rethrowFunction(roleService::findByNameThrowError))
        .collect(Collectors.toSet());
  }

  @Named("authorMTE")
  public AuthorEntity authorMTE( final UUID author) {
    return authorRepo != null && author != null ? authorRepo.findById(author).orElse(null) : null;
  }

  @Named("authorMTX")
  public String authorMTX(final UUID author) {
    return Optional.ofNullable(authorMTE(author))
        .map(AuthorEntity::getName)
        .orElse(null);
  }

  @Named("publisherMTE")
  public PublisherEntity publisherMTE( final UUID publisher) {
    return publisherRepo != null && publisher != null ? publisherRepo.findById(publisher).orElse(null) : null;
  }

  @Named("publisherMTX")
  public String publisherMTX(final UUID publisher) {
    return Optional.ofNullable(publisherMTE(publisher))
        .map(PublisherEntity::getName)
        .orElse(null);
  }

  @Named("researcherMTE")
  public ResearcherEntity researcherMTE( final UUID researcher) {
    return researcherRepo != null && researcher != null ? researcherRepo.findById(researcher).orElse(null) : null;
  }

  @Named("researcherMTX")
  public String researcherMTX(final UUID researcher) {
    return Optional.ofNullable(researcherMTE(researcher))
        .map(ResearcherEntity::getName)
        .orElse(null);
  }

  @Named("bookMTE")
  public BookEntity bookMTE( final UUID book) {
    return bookRepo != null && book != null ? bookRepo.findById(book).orElse(null) : null;
  }

  @Named("rackMTE")
  public RackEntity rackMTE( final UUID rack) {
    return rackRepo != null && rack != null ? rackRepo.findById(rack).orElse(null) : null;
  }

  @Named("volumeMTE")
  public VolumeEntity volumeMTE( final UUID volume) {
    return volumeRepo != null && volume != null ? volumeRepo.findById(volume).orElse(null) : null;
  }
}
