package pl.estrix.app.backend.shop.dao;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;

import java.util.List;

public interface ShopCategoryRepositoryCustom {

    List<ShopCategoryDto> find(ShopCategorySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ShopCategorySearchCriteriaDto searchCriteria);
}
