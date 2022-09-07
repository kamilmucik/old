package pl.estrix.app.backend.shop.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.shop.model.ShopCategory;

@Component
public class DeleteShopCategoryCommandExecutor extends BaseShopCategoryCommandExecutor{

    public void delete(Long itemId) {
        ShopCategory entity = getOne(itemId);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    public void deleteAll() {
            repository.deleteAll();
    }
}
