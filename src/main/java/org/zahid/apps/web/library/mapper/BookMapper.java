package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.VolumeModel;
import org.zahid.apps.web.library.payload.response.SearchBookResponse;
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
    @Mapping(target = "volumes", expression = "java(book != null ? toVolumeModels(book.getVolumes()) : null)")
    public abstract BookModel toBookModel(final BookEntity book);

    @Mapping(target = "author", expression = "java(model != null && model.getAuthor() != null ? authorService.findById(model.getAuthor()) : null)")
    @Mapping(target = "subject", expression = "java(model != null && model.getSubject() != null ? subjectService.findById(model.getSubject()) : null)")
    @Mapping(target = "publisher", expression = "java(model != null && model.getPublisher() != null ? publisherService.findById(model.getPublisher()) : null)")
    @Mapping(target = "researcher", expression = "java(model != null && model.getResearcher() != null ? researcherService.findById(model.getResearcher()) : null)")
    @Mapping(target = "shelf", expression = "java(model != null && model.getShelf() != null ? shelfService.findById(model.getShelf()) : null)")
    @Mapping(target = "volumes", expression = "java(model != null ? toVolumeEntities(model.getVolumes()) : null)")
    public abstract BookEntity toBookEntity(final BookModel model);

    /*@Mapping(target = "authorId", expression = "java(book != null && book.getAuthor() != null ? book.getAuthor().getAuthorId() : null)")
    @Mapping(target = "authorName", expression = "java(book != null && book.getAuthor() != null ? book.getAuthor().getAuthorName() : null)")
    @Mapping(target = "subjectId", expression = "java(book != null && book.getSubject() != null ? book.getSubject().getSubjectId() : null)")
    @Mapping(target = "subjectName", expression = "java(book != null && book.getSubject() != null ? book.getSubject().getSubjectName() : null)")
    @Mapping(target = "publisherId", expression = "java(book != null && book.getPublisher() != null ? book.getPublisher().getPublisherId() : null)")
    @Mapping(target = "publisherName", expression = "java(book != null && book.getPublisher() != null ? book.getPublisher().getPublisherName() : null)")
    @Mapping(target = "researcherId", expression = "java(book != null && book.getResearcher() != null ? book.getResearcher().getResearcherId() : null)")
    @Mapping(target = "researcherName", expression = "java(book != null && book.getResearcher() != null ? book.getResearcher().getResearcherName() : null)")
    @Mapping(target = "shelfId", expression = "java(book != null && book.getShelf() != null ? book.getShelf().getShelfId() : null)")
    @Mapping(target = "shelfName", expression = "java(book != null && book.getShelf() != null ? book.getShelf().getShelfName() : null)")
    public abstract SearchBookResponse toSearchBookResponse(final BookEntity book);*/

    public List<BookModel> toBookModels(final List<BookEntity> Books) {
        if (CollectionUtils.isEmpty(Books)) {
            return new ArrayList<>();
        }
        final List<BookModel> models = new ArrayList<>();
        Books.forEach(BookEntity -> {
            models.add(this.toBookModel(BookEntity));
        });
        return models;
    }

    public List<BookEntity> toBookEntities(final List<BookModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookEntity> books = new ArrayList<>();
        models.forEach(model -> {
            books.add(this.toBookEntity(model));
        });
        return books;
    }

    /*public List<SearchBookResponse> toSearchBookResponses(final List<BookEntity> Books) {
        if (CollectionUtils.isEmpty(Books)) {
            return new ArrayList<>();
        }
        final List<SearchBookResponse> searchBookResponses = new ArrayList<>();
        Books.forEach(BookEntity -> {
            searchBookResponses.add(this.toSearchBookResponse(BookEntity));
        });
        return searchBookResponses;
    }*/

    public List<VolumeModel> toVolumeModels(final List<VolumeEntity> volumes) {
        return volumeMapper.toVolumeModels(volumes);
    }

    public List<VolumeEntity> toVolumeEntities(final List<VolumeModel> models) {
        return volumeMapper.toVolumeEntities(models);
    }
}
