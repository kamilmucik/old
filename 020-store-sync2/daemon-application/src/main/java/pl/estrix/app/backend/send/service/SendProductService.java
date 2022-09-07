package pl.estrix.app.backend.send.service;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;

import java.math.BigDecimal;

public interface SendProductService {

    SendProductDto get(Long id);
    SendProductDto create(SendProductDto dto);
    SendProductDto update(SendProductDto dto);
    void delete(Long itemId);

    ListResponseDto<SendProductDto> getItems(SendProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    SendProductDto getLastProductToSend();

    int upgradeProductInShop(Boolean processed, BigDecimal price, Integer stock, String code);
}
