package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.ShopDto;

@Component
public class CreateShopCommandExecutor extends BaseShopCommandExecutor {

    public ShopDto create(ShopDto dto) {
        ShopDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
