package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.ShopDto;

@Component
public class UpdateShopCommandExecutor extends BaseShopCommandExecutor {

    public ShopDto update(ShopDto dto) {
        ShopDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
