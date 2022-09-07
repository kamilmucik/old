package pl.estrix.app.backend.stock.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.stock.dao.StockProductAttributeRepository;
import pl.estrix.app.backend.stock.dao.StockProductImageRepository;
import pl.estrix.app.backend.stock.dao.StockProductRepository;
import pl.estrix.app.backend.stock.dao.StockRepository;
import pl.estrix.app.backend.stock.model.StockProduct;
import pl.estrix.app.backend.stock.model.StockProductImage;
import pl.estrix.app.common.dto.model.StockProductDto;
import pl.estrix.app.common.dto.model.StockProductImageDto;

import java.time.LocalDateTime;

public class BaseStockProductCommandExecutor extends BaseCommandExecutor<StockProduct, StockProductDto> {
    @Autowired
    protected StockProductImageRepository repositoryImage;
    @Autowired
    protected StockProductRepository repository;
    @Autowired
    protected StockRepository repositoryStock;
    @Autowired
    protected StockProductAttributeRepository repositoryAttribute;

    @Override
    protected Class<StockProductDto> getDtoClass() {
        return StockProductDto.class;
    }

    protected StockProductDto mapToDto(StockProduct entity) {
        if (entity == null){
            return null;
        }
        StockProductDto dto = new StockProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setStockId(entity.getStockId());
        dto.setShopId(entity.getShopId());
        dto.setPrice(entity.getPrice());
        dto.setPriceBrutto(entity.getPriceBrutto());
        dto.setPriceRetail(entity.getPriceRetail());
        dto.setStock(entity.getStock());
        dto.setStockExt(entity.getStockExt());
        dto.setDescription(entity.getDescription());
        dto.setAvail(entity.getActive());
        dto.setExtId(entity.getExtId());
        dto.setExtUrl(entity.getExtUrl());
        dto.setShippingTime(entity.getShippingTime());
        dto.setMainThumb(entity.getMainThumb());

        return dto;
    }

    protected StockProduct mapToEntity(StockProductDto dto){
        if (dto == null){
            return null;
        }
        StockProduct entity = new StockProduct();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setStockId(dto.getStockId());
        entity.setShopId(dto.getShopId());
        entity.setPrice(dto.getPrice());
        entity.setPriceBrutto(dto.getPriceBrutto());
        entity.setPriceRetail(dto.getPriceRetail());
        entity.setStock(dto.getStock());
        entity.setStockExt(dto.getStockExt());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.getAvail());
        entity.setExtId(dto.getExtId());
        entity.setExtUrl(dto.getExtUrl());
        entity.setShippingTime(dto.getShippingTime());

        entity.setMainThumb(dto.getMainThumb());
        entity.setLastUpdate(LocalDateTime.now());

//        for (StockProductImageDto img : dto.getImages()){
//            StockProductImage image = new StockProductImage(entity,img.getUrl());
//            //image.setStockProductId(entity.getId());
//            entity.getImages().add(image);
//        }

        return entity;
    }

    protected StockProduct getOne(Long id) {
        return repository.findById(id);
    }
}
