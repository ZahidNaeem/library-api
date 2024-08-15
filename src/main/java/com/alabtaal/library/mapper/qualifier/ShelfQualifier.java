package com.alabtaal.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.mapper.BookMapper;
import com.alabtaal.library.mapper.RackMapper;
import com.alabtaal.library.model.BookModel;
import com.alabtaal.library.model.RackModel;

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
