package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.*;
import org.zahid.apps.web.library.mapper.BookTransLineMapper;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.BookTransLineModel;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.*;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class BookTransHeaderQualifier {

    private final ReaderService readerService;

    private final BookTransLineMapper bookTransLineMapper;

    @Named("reader")
    public ReaderEntity reader (final Long reader){
        return reader != null ? readerService.findById(reader) : null;
    }

    @Named("bookTransLineModels")
    public List<BookTransLineModel> bookTransLineModels (final List<BookTransLineEntity> bookTransLines){
        return bookTransLineMapper.toModels(bookTransLines);
    }

    @Named("bookTransLineEntities")
    public List<BookTransLineEntity> bookTransLineEntities (final List<BookTransLineModel> bookTransLines){
        return bookTransLineMapper.toEntities(bookTransLines);
    }
}
