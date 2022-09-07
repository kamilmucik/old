package pl.estrix.app.backend.stock.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.stock.dao.StockRepository;
import pl.estrix.app.backend.stock.model.Stock;
import pl.estrix.app.common.dto.model.StockDto;

import java.time.LocalDateTime;

public class BaseStockCommandExecutor extends BaseCommandExecutor<Stock, StockDto> {
    @Autowired
    protected StockRepository repository;

    @Override
    protected Class<StockDto> getDtoClass() {
        return StockDto.class;
    }

    protected StockDto mapToDto(Stock entity) {
        if (entity == null){
            return null;
        }
        StockDto dto = new StockDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUpdateHour(entity.getUpdateHour());
        dto.setUrl(entity.getUrl());
        dto.setApiUrl(entity.getApiUrl());
        dto.setProductCounter(entity.getProductCounter());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setStatus(entity.getStatus());
        dto.setSiteScratch(entity.getSiteScratch());

        return dto;
    }

    protected Stock mapToEntity(StockDto dto){
        if (dto == null){
            return null;
        }
        Stock entity = new Stock();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUpdateHour(dto.getUpdateHour());
        entity.setUrl(dto.getUrl());
        entity.setApiUrl(dto.getApiUrl());
        entity.setProductCounter(dto.getProductCounter());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setStatus(dto.getStatus());
        entity.setSiteScratch(dto.getSiteScratch());

        return entity;
    }

    protected Stock getOne(Long id) {
        return repository.findById(id);
    }
}
