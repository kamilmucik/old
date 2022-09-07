package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.common.dto.model.StockProductDto;

@Component
public class UpdateStockProductCommandExecutor extends BaseStockProductCommandExecutor{

    public StockProductDto update(StockProductDto dto) {
        return this.mapToDto(
                repository.save(this.mapToEntity(dto)
                ));
    }

    public void disableProductForStock(Long id) {
        repository.disableProductForStock(false,id);
    }
}
