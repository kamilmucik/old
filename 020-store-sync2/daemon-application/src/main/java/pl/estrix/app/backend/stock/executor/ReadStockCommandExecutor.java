package pl.estrix.app.backend.stock.executor;

import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;

@Component
public class ReadStockCommandExecutor  extends BaseStockCommandExecutor {

    public StockDto findById(Long id) {
        return mapToDto(getOne(id));
    }

    public ListResponseDto<StockDto> find(StockSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return createListResponseDto(
                pagingCriteria,
                () -> repository.find(searchCriteria, pagingCriteria),
                () -> (int) repository.findCount(searchCriteria));
    }
}
