package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.*;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.service.BookService;
import com.alabtaal.library.service.BookTransHeaderService;
import com.alabtaal.library.service.ReaderService;
import com.alabtaal.library.service.VolumeService;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class BookTransLineQualifier {

    private BookTransHeaderService bookTransHeaderService;

    private final BookService bookService;

    private final VolumeService volumeService;

    @Named("bookTransHeader")
    public BookTransHeaderEntity bookTransHeader (final Long header){
        return header != null ? bookTransHeaderService.findById(header) : null;
    }

    @Named("book")
    public BookEntity book (final Long book){
        return book != null ? bookService.findById(book) : null;
    }

    @Named("volume")
    public VolumeEntity volume (final Long volume){
        return volume != null ? volumeService.findById(volume) : null;
    }
}
