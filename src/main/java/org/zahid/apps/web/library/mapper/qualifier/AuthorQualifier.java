package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.*;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.mapper.BookTransLineMapper;
import org.zahid.apps.web.library.mapper.VolumeMapper;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.BookTransLineModel;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.*;

import java.util.List;

@Component
public class AuthorQualifier extends CommonQualifier {

    @Autowired
    public AuthorQualifier(final BookMapper bookMapper) {
        super(bookMapper);
    }
}
