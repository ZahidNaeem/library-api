package org.zahid.apps.web.library.mapper.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.mapper.BookMapper;

@Component
public class PublisherQualifier extends CommonQualifier {

    @Autowired
    public PublisherQualifier(final BookMapper bookMapper) {
        super(bookMapper);
    }
}
