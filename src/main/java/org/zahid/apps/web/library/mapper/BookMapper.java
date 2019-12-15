package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.service.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorService authorService;

    @Autowired
    protected SubjectService subjectService;

    @Autowired
    protected PublisherService publisherService;

    @Autowired
    protected ResearcherService researcherService;

    @Autowired
    protected ShelfService shelfService;

    @Autowired
    protected VolumeMapper volumeMapper;

    @Mapping(target = "author", expression = "java(book != null && book.getAuthor() != null ? book.getAuthor().getAuthorId() : null)")
    @Mapping(target = "subject", expression = "java(book != null && book.getSubject() != null ? book.getSubject().getSubjectId() : null)")
    @Mapping(target = "publisher", expression = "java(book != null && book.getPublisher() != null ? book.getPublisher().getPublisherId() : null)")
    @Mapping(target = "researcher", expression = "java(book != null && book.getResearcher() != null ? book.getResearcher().getResearcherId() : null)")
    @Mapping(target = "shelf", expression = "java(book != null && book.getShelf() != null ? book.getShelf().getShelfId() : null)")
    @Mapping(target = "volumes", expression = "java(book != null ? mapVolumeEntitiesToVolumeModels(book.getVolumes()) : null)")
    public abstract BookModel fromBook(final BookEntity book);

    @Mapping(target = "author", expression = "java(model != null && model.getAuthor() != null ? authorService.findById(model.getAuthor()) : null)")
    @Mapping(target = "subject", expression = "java(model != null && model.getSubject() != null ? subjectService.findById(model.getSubject()) : null)")
    @Mapping(target = "publisher", expression = "java(model != null && model.getPublisher() != null ? publisherService.findById(model.getPublisher()) : null)")
    @Mapping(target = "researcher", expression = "java(model != null && model.getResearcher() != null ? researcherService.findById(model.getResearcher()) : null)")
    @Mapping(target = "shelf", expression = "java(model != null && model.getShelf() != null ? shelfService.findById(model.getShelf()) : null)")
    @Mapping(target = "volumes", expression = "java(model != null ? mapVolumeModelsToVolumes(model.getVolumes()) : null)")
    public abstract BookEntity toBook(final BookModel model);

    public List<BookModel> mapBookEntitiesToBookModels(final List<BookEntity> Books) {
        if (CollectionUtils.isEmpty(Books)) {
            return new ArrayList<>();
        }
        final List<BookModel> models = new ArrayList<>();
        Books.forEach(BookEntity -> {
            models.add(this.fromBook(BookEntity));
        });
        return models;
    }

    public List<BookEntity> mapBookModelsToBookEntities(final List<BookModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookEntity> books = new ArrayList<>();
        models.forEach(model -> {
            books.add(this.toBook(model));
        });
        return books;
    }

    public List<VolumeModel> mapVolumeEntitiesToVolumeModels(final List<VolumeEntity> volumes) {
        return volumeMapper.mapVolumeEntitiesToVolumeModels(volumes);
    }

    public List<VolumeEntity> mapVolumeModelsToVolumes(final List<VolumeModel> models) {
        return volumeMapper.mapVolumeModelsToVolumes(models);
    }
}
