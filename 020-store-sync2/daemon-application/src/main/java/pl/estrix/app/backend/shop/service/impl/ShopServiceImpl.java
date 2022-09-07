package pl.estrix.app.backend.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.shop.executor.*;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;
import pl.estrix.app.common.dto.model.ShopDto;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class ShopServiceImpl implements ShopService {

    @Autowired
    private DeleteShopCommandExecutor deleteExecutor;
    @Autowired
    private ReadShopCommandExecutor readExecutor;
    @Autowired
    private CreateShopCommandExecutor createExecutor;
    @Autowired
    private UpdateShopCommandExecutor updateExecutor;
    @Autowired
    private DeleteShopCategoryCommandExecutor deleteCategoryExecutor;
    @Autowired
    private ReadShopCategoryCommandExecutor readCategoryExecutor;
    @Autowired
    private CreateShopCategoryCommandExecutor createCategoryExecutor;

    @Override
    public ShopDto get(Long id) {
        return readExecutor.findById(id);
    }

    @Override
    public ShopDto create(ShopDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public ShopDto update(ShopDto dto) {
        return updateExecutor.update(dto);
    }

    @Override
    public void delete(Long itemId) {
        deleteExecutor.delete(itemId);
    }

    @Override
    public ListResponseDto<ShopDto> getItems(ShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<ShopDto> listResponseDto = readExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public ShopCategoryDto getCategory(Long id) {
        return null;
    }

    @Override
    public List<Integer> getCategories(Long shopId, Long categoryId) {
        ArrayList<Integer> result = new ArrayList<>();
        ShopCategoryDto cat = readCategoryExecutor.findByExtId(shopId,categoryId);
        result.add(cat.getExternalId());

        Integer catParentId = cat.getParentId();
        while(catParentId != 0) {
            cat = readCategoryExecutor.findByExtId(shopId,catParentId.longValue());
            if (cat != null) {
                catParentId = cat.getParentId();
                result.add(cat.getExternalId());
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public ShopCategoryDto createCategory(ShopCategoryDto dto) {
        return createCategoryExecutor.create(dto);
    }

    @Override
    public void deleteCategory(Long itemId) {
        deleteCategoryExecutor.delete(itemId);
    }

    @Override
    public void deleteAllCategory(Long shopId) {
        for (ShopCategoryDto cat: readCategoryExecutor.findByallegroId(shopId)){
            deleteCategoryExecutor.delete(cat.getId());
        }
    }

    @Override
    public ListResponseDto<ShopCategoryDto> getCategoryItems(ShopCategorySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {


        ListResponseDto<ShopCategoryDto> listResponseDto = readCategoryExecutor.find(searchCriteria,pagingCriteria);

//        listResponseDto.getData().stream().forEach( o ->{
//            Long result = readProductExecutor.countProducts(StockProductSearchCriteriaDto.builder().stockId(o.getId()).build());
//            o.setProducts(result.toString());
//        });

        return listResponseDto;
    }
}
