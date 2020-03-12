package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.BookEntity;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.mapper.BookMapper;
import org.zahid.apps.web.library.mapper.RackMapper;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.model.RackModel;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ShelfQualifier extends CommonQualifier {

    private final RackMapper rackMapper;

    @Autowired
    public ShelfQualifier(final BookMapper bookMapper, final RackMapper rackMapper) {
        super(bookMapper);
        this.rackMapper = rackMapper;
    }

    @Named("rackModels")
    public List<RackModel> rackModels(final List<RackEntity> racks) {
        return rackMapper.toModels(racks);
    }

    @Named("rackEntities")
    public List<RackEntity> rackEntities(final List<RackModel> racks) {
        return rackMapper.toEntities(racks);
    }
}
