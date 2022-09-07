package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.ShopCategoryDto;

@Component
public class CreateShopCategoryCommandExecutor extends BaseShopCategoryCommandExecutor{

    public ShopCategoryDto create(ShopCategoryDto dto) {

        ShopCategoryDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
