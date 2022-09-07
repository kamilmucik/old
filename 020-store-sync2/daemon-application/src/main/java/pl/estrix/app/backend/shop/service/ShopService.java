package pl.estrix.app.backend.shop.service;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;
import pl.estrix.app.common.dto.model.ShopDto;

import java.util.List;

public interface ShopService {

    ShopDto get(Long id);
    ShopDto create(ShopDto dto);
    ShopDto update(ShopDto dto);
    void delete(Long itemId);
    ListResponseDto<ShopDto> getItems(ShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    ShopCategoryDto getCategory(Long id);
    List<Integer> getCategories(Long shopId, Long id);
    ShopCategoryDto createCategory(ShopCategoryDto dto);
    void deleteCategory(Long itemId);
    void deleteAllCategory(Long shopId);
    ListResponseDto<ShopCategoryDto> getCategoryItems(ShopCategorySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);


}
