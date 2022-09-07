package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.stock.model.Stock;
import pl.estrix.app.common.dto.model.StockDto;

@Component
public class UpdateStockCommandExecutor extends BaseStockCommandExecutor {

    public StockDto update(StockDto dto) {
        return this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));
    }
}
