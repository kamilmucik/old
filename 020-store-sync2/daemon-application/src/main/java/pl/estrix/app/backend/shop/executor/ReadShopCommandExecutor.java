package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopDto;

@Component
public class ReadShopCommandExecutor extends BaseShopCommandExecutor {

    public ShopDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<ShopDto> find(ShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
