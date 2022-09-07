package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.StockDto;

@Component
public class CreateStockCommandExecutor  extends BaseStockCommandExecutor {

    public StockDto create(StockDto dto) {
        StockDto result = this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));

        return result;
    }
}
