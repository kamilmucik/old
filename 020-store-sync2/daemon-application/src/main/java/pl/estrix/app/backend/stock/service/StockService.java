package pl.estrix.app.backend.stock.service;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;
import pl.estrix.app.common.dto.model.StockProductDto;

public interface StockService {

    StockDto get(Long id);

    StockDto create(StockDto dto);

    StockDto update(StockDto dto);

    void delete(Long id);

    void setDisableProductForStock(Long id);

    ListResponseDto<StockDto> getItems(StockSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    StockProductDto getProduct(Long id);
    StockProductImage getImage(Long id);

    StockProductDto createProduct(StockProductDto dto);
    StockProductDto updateProduct(StockProductDto dto);

    ListResponseDto<StockProductDto> getProductItems(StockProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    void downloadStockProduct(StockDto selected);
    void downloadStockProductImage();

    StockProductDto findByStockIdAndExternalId(Long shopId, String externalId);

    StockProductDto getProductByShopIdAndProductCode(Long shopId, String code);
    StockProductDto getProductByStockIdAndProductCode(Long shopId, String code);
}
