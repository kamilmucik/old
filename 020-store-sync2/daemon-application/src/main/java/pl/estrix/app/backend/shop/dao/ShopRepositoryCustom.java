package pl.estrix.app.backend.shop.dao;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopDto;

import java.util.List;

public interface ShopRepositoryCustom {

    List<ShopDto> find(ShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShopSearchCriteriaDto searchCriteria);
}
