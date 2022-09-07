package pl.estrix.app.backend.stock.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockProductDto;

import java.util.List;

public interface StockProductRepositoryCustom {

    List<StockProductDto> find(StockProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(StockProductSearchCriteriaDto searchCriteria);
}
