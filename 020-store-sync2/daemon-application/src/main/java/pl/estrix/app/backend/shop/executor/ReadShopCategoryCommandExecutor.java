package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.shop.model.ShopCategory;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopCategorySearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReadShopCategoryCommandExecutor extends BaseShopCategoryCommandExecutor{

    public ShopCategoryDto findById(Long id) {
        return mapToDto(getOne(id));
    }
    public ShopCategoryDto findByExtId(Long shopId,Long id) {
        return mapToDto(getExtOne(shopId, id));
    }

    public List<ShopCategoryDto> findByallegroId(Long shopId){
        List<ShopCategoryDto> result = new ArrayList<>();

        try{
//            System.out.println("ReadShopCategoryCommandExecutor.findByallegroId: " + repository.findById(shopId));
            for (ShopCategory cat: repository.findAll()){
                if (cat.getShopId() != null && cat.getShopId() == shopId.intValue()) {
//                if (cat.getAllegroId() != null && !cat.getAllegroId().isEmpty()) {
                    result.add(mapToDto(cat));
                }
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
//        System.out.println("ReadShopCategoryCommandExecutor.findByallegroId: " + shopId);


        return result;
    }

    public ListResponseDto<ShopCategoryDto> find(ShopCategorySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {

        List<ShopCategoryDto> result = repository.find(searchCriteria, pagingCriteria);

        return createListResponseDto(
                pagingCriteria,
                () -> result,
                () -> (int) repository.findCount(searchCriteria));
    }

}
