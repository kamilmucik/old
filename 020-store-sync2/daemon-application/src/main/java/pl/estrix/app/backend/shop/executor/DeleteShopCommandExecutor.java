package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.shop.model.Shop;

@Component
public class DeleteShopCommandExecutor extends BaseShopCommandExecutor {

    public void delete(Long itemId) {
        Shop entity = getOne(itemId);
        if (entity != null) {
            repository.delete(entity);
        }
    }
}
