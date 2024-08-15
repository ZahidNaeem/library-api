package com.alabtaal.library.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.mapper.qualifier.BookQualifier;
import com.alabtaal.library.model.BookExportToExcel;
import com.alabtaal.library.model.BookModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {BookQualifier.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
        )
public interface BookMapper {

    @Mapping(target = "author", expression = "java(book != null && book.getAuthor() != null ? book.getAuthor().getAuthorId() : null)")
    @Mapping(target = "subject", expression = "java(book != null && book.getSubject() != null ? book.getSubject().getSubjectId() : null)")
    @Mapping(target = "publisher", expression = "java(book != null && book.getPublisher() != null ? book.getPublisher().getPublisherId() : null)")
    @Mapping(target = "researcher", expression = "java(book != null && book.getResearcher() != null ? book.getResearcher().getResearcherId() : null)")
    @Mapping(target = "shelf", expression = "java(book != null && book.getShelf() != null ? book.getShelf().getShelfId() : null)")
    @Mapping(target = "volumes", qualifiedByName = "volumeModels")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineModels")
    BookModel toModel(final BookEntity book);

    @Mapping(target = "author", qualifiedByName = "author")
    @Mapping(target = "subject", qualifiedByName = "subject")
    @Mapping(target = "publisher", qualifiedByName = "publisher")
    @Mapping(target = "researcher", qualifiedByName = "researcher")
    @Mapping(target = "shelf", qualifiedByName = "shelf")
    @Mapping(target = "volumes", qualifiedByName = "volumeEntities")
    @Mapping(target = "bookTransLines", qualifiedByName = "bookTransLineEntities")
    BookEntity toEntity(final BookModel model);

    @Mapping(target = "author", expression = "java(model != null && model.getAuthor() != null ? bookQualifier.getAuthorService().findById(model.getAuthor()).getAuthorName() : null)")
    @Mapping(target = "subject", expression = "java(model != null && model.getSubject() != null ? bookQualifier.getSubjectService().findById(model.getSubject()).getSubjectName() : null)")
    @Mapping(target = "publisher", expression = "java(model != null && model.getPublisher() != null ? bookQualifier.getPublisherService().findById(model.getPublisher()).getPublisherName() : null)")
    @Mapping(target = "researcher", expression = "java(model != null && model.getResearcher() != null ? bookQualifier.getResearcherService().findById(model.getResearcher()).getResearcherName() : null)")
    @Mapping(target = "purchased", expression = "java(model != null ? model.getPurchased() == 1 ? \"purchased\" : model.getPurchased() == 0 ? \"Gifted\" : \"Other\" : null)")
    @Mapping(target = "volumes", expression = "java(model != null && model.getVolumes() != null ? model.getVolumes().size() : 0)")
    BookExportToExcel toExcel(final BookModel model);

    default List<BookModel> toModels(final List<BookEntity> Books) {
        if (CollectionUtils.isEmpty(Books)) {
            return new ArrayList<>();
        }
        final List<BookModel> models = new ArrayList<>();
        Books.forEach(BookEntity -> {
            models.add(this.toModel(BookEntity));
        });
        return models;
    }

    default List<BookEntity> toEntities(final List<BookModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookEntity> books = new ArrayList<>();
        models.forEach(model -> {
            books.add(this.toEntity(model));
        });
        return books;
    }

    default List<BookExportToExcel> toExcel(final List<BookModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<BookExportToExcel> books = new ArrayList<>();
        models.forEach(model -> {
            books.add(this.toExcel(model));
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
}
