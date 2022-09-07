package pl.estrix.app.backend.send.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.send.dao.SendProductImageRepository;
import pl.estrix.app.backend.send.dao.SendProductRepository;
import pl.estrix.app.backend.send.model.SendProduct;
import pl.estrix.app.backend.send.model.SendProductImage;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.SendProductImageDto;

import java.time.LocalDateTime;

public class BaseSendProductCommandExecutor extends BaseCommandExecutor<SendProduct, SendProductDto> {
    @Autowired
    protected SendProductRepository repository;

    @Autowired
    protected SendProductImageRepository repositoryImage;

    @Override
    protected Class<SendProductDto> getDtoClass() {
        return SendProductDto.class;
    }

    protected SendProductDto mapToDto(SendProduct entity) {
        if (entity == null){
            return null;
        }
        SendProductDto dto = new SendProductDto();
        dto.setId(entity.getId());
        dto.setShopId(entity.getShopId());
        dto.setProductId(entity.getProductId());
        dto.setShopCategories(entity.getShopCategories());
        dto.setPrice(entity.getPrice());
        dto.setMargin(entity.getMargin());
        dto.setStock(entity.getStock());
        dto.setProcessed(entity.getProcessed());
        dto.setShopProductId(entity.getShopProductId());
        dto.setMessage(entity.getMessage());
        dto.setCode(entity.getCode());
        dto.setPermalink(entity.getPermalink());
        dto.setImage(entity.getImage());

        return dto;
    }

    protected SendProduct mapToEntity(SendProductDto dto){
        if (dto == null){
            return null;
        }
        SendProduct entity = new SendProduct();
        entity.setId(dto.getId());
        entity.setShopId(dto.getShopId());
        entity.setProductId(dto.getProductId());
        entity.setShopCategories(dto.getShopCategories());
        entity.setPrice(dto.getPrice());
        entity.setMargin(dto.getMargin());
        entity.setStock(dto.getStock());
        entity.setProcessed(dto.getProcessed());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setShopProductId(dto.getShopProductId());
        entity.setMessage(dto.getMessage());
        entity.setCode(dto.getCode());
        entity.setPermalink(dto.getPermalink());
        entity.setImage(dto.getImage());

        return entity;
    }

    protected SendProductImageDto mapToDto(SendProductImage entity) {
        if (entity == null){
            return null;
        }
        SendProductImageDto dto = new SendProductImageDto();
        dto.setId(entity.getId());
        dto.setSendProductId(entity.getSendProductId());
        dto.setPosition(entity.getPosition());
        dto.setSrc(entity.getSrc());

        return dto;
    }

    protected SendProductImage mapToEntity(SendProductImageDto dto){
        if (dto == null){
            return null;
        }
        SendProductImage entity = new SendProductImage();
        entity.setId(dto.getId());
        entity.setSendProductId(dto.getSendProductId());
        entity.setPosition(dto.getPosition());
        entity.setSrc(dto.getSrc());

        return entity;
    }

    protected SendProductImage getOneImage(Long id) {
        return repositoryImage.findById(id);
    }

    protected SendProduct getOne(Long id) {
        return repository.findById(id);
    }
//    protected SendProduct getLastProductToSend() {
//        return repository.findLastProductToSend();
//    }

}
