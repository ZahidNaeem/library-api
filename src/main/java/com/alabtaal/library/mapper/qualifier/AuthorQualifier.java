package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.*;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.mapper.BookTransLineMapper;
import com.alabtaal.library.mapper.VolumeMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.BookTransLineModel;
import com.alabtaal.library.model.VolumeModel;
import com.alabtaal.library.service.*;

import java.util.List;

@Component
public class AuthorQualifier extends CommonQualifier {

    @Autowired
    public AuthorQualifier(final BookMapper bookMapper) {
        super(bookMapper);
    }
}
