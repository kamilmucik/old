package pl.estrix.app.backend.stock.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;

import java.util.List;

public interface StockRepositoryCustom {

    List<StockDto> find(StockSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(StockSearchCriteriaDto searchCriteria);
}
