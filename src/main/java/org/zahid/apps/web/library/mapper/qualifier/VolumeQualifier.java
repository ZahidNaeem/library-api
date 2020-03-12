package org.zahid.apps.web.library.mapper.qualifier;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.BookTransLineEntity;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.ShelfEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.mapper.BookTransLineMapper;
import org.zahid.apps.web.library.model.BookTransLineModel;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.BookService;
import org.zahid.apps.web.library.service.RackService;

@Component
@Data
@RequiredArgsConstructor
public class VolumeQualifier {

  private final BookService bookService;

  private final RackService rackService;

  private final BookTransLineMapper bookTransLineMapper;

  @Named("book")
  public BookEntity book(final Long book) {
    return book != null ? bookService.findById(book) : null;
  }

  @Named("rack")
  public RackEntity rack(final Long rack) {
    return rack != null ? rackService.findById(rack) : null;
  }

  @Named("bookTransLineModels")
  public List<BookTransLineModel> bookTransLineModels(final List<BookTransLineEntity> bookTransLines) {
    return bookTransLineMapper.toModels(bookTransLines);
  }

  @Named("bookTransLineEntities")
  public List<BookTransLineEntity> bookTransLine(final List<BookTransLineModel> bookTransLines) {
    return bookTransLineMapper.toEntities(bookTransLines);
  }
}
