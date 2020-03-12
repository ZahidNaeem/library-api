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
public class BookQualifier {

    private final AuthorService authorService;

    private final SubjectService subjectService;

    private final PublisherService publisherService;

    private final ResearcherService researcherService;

    private final ShelfService shelfService;

    private final VolumeMapper volumeMapper;

    private final BookTransLineMapper bookTransLineMapper;

    @Named("author")
    public AuthorEntity author (final Long author){
        return author != null ? authorService.findById(author) : null;
    }

    @Named("subject")
    public SubjectEntity subject (final Long subject){
        return subject != null ? subjectService.findById(subject) : null;
    }

    @Named("publisher")
    public PublisherEntity publisher (final Long publisher){
        return publisher != null ? publisherService.findById(publisher) : null;
    }

    @Named("researcher")
    public ResearcherEntity researcher (final Long researcher){
        return researcher != null ? researcherService.findById(researcher) : null;
    }

    @Named("shelf")
    public ShelfEntity shelf (final Long shelf){
        return shelf != null ? shelfService.findById(shelf) : null;
    }

    @Named("volumeModels")
    public List<VolumeModel> volumeModels (final List<VolumeEntity> volumes){
        return volumeMapper.toModels(volumes);
    }

    @Named("volumeEntities")
    public List<VolumeEntity> volumeEntities (final List<VolumeModel> volumes){
        return volumeMapper.toEntities(volumes);
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
