package com.alabtaal.library.mapper.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alabtaal.library.mapper.BookMapper;

@Component
public class ResearcherQualifier extends CommonQualifier {

    @Autowired
    public ResearcherQualifier(final BookMapper bookMapper) {
        super(bookMapper);
    }
}
