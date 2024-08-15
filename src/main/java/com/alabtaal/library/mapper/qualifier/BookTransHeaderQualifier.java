package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.*;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.service.*;

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
