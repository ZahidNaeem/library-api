package com.alabtaal.library.mapper.qualifier;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.service.BookService;
import com.alabtaal.library.service.RackService;

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
