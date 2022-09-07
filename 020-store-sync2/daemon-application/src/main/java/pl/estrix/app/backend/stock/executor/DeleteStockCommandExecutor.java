package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.stock.model.Stock;

@Component
public class DeleteStockCommandExecutor extends BaseStockCommandExecutor {

    public void delete(Long id) {
        Stock entity = getOne(id);
        if (entity != null) {
            repository.delete(entity);
        }
    }
}
