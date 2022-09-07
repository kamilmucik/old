package pl.estrix.app.backend.send.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.executor.CreateSendProductCommandExecutor;
import pl.estrix.app.backend.send.executor.DeleteSendProductCommandExecutor;
import pl.estrix.app.backend.send.executor.ReadSendProductCommandExecutor;
import pl.estrix.app.backend.send.executor.UpdateSendProductCommandExecutor;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;

import java.math.BigDecimal;

@Service
@Primary
public class SendProductServiceImpl implements SendProductService {

    @Autowired
    private DeleteSendProductCommandExecutor deleteExecutor;
    @Autowired
    private ReadSendProductCommandExecutor readExecutor;
    @Autowired
    private CreateSendProductCommandExecutor createExecutor;
    @Autowired
    private UpdateSendProductCommandExecutor updateExecutor;

    @Override
    public SendProductDto get(Long id) {
        return readExecutor.findById(id);
    }

    @Override
    public SendProductDto create(SendProductDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public SendProductDto update(SendProductDto dto) {
        return updateExecutor.update(dto);
    }

    @Override
    public void delete(Long itemId) {
        deleteExecutor.delete(itemId);
    }

    @Override
    public ListResponseDto<SendProductDto> getItems(SendProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        return readExecutor.find(searchCriteria,pagingCriteria);
    }

    @Override
    public SendProductDto getLastProductToSend() {
        return readExecutor.getLastProductToSend();
    }

    @Override
    public int upgradeProductInShop(Boolean processed, BigDecimal price, Integer stock, String code) {
        return updateExecutor.upgradeProductInShop(processed,price,stock,code);
    }
}
