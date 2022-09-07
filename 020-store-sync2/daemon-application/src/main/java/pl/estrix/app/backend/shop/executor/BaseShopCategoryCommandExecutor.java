package pl.estrix.app.backend.shop.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.shop.dao.ShopCategoryRepository;
import pl.estrix.app.backend.shop.model.ShopCategory;
import pl.estrix.app.common.dto.model.ShopCategoryDto;

public class BaseShopCategoryCommandExecutor extends BaseCommandExecutor<ShopCategory, ShopCategoryDto> {
    @Autowired
    protected ShopCategoryRepository repository;

    @Override
    protected Class<ShopCategoryDto> getDtoClass() {
        return ShopCategoryDto.class;
    }

    protected ShopCategoryDto mapToDto(ShopCategory entity) {
        if (entity == null){
            return null;
        }
        ShopCategoryDto dto = new ShopCategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setShopId(entity.getShopId());
        dto.setParentId(entity.getParentId());
        dto.setExternalId(entity.getExternalId());
        dto.setAllegroId(entity.getAllegroId());
        dto.setAllegroParentId(entity.getAllegroParentId());
        dto.setAllegroLeaf(entity.getAllegroLeaf());
        dto.setAllegroChildren(entity.getAllegroChildren());
        dto.setType(entity.getType());

        return dto;
    }

    protected ShopCategory mapToEntity(ShopCategoryDto dto){
        if (dto == null){
            return null;
        }
        ShopCategory entity = new ShopCategory();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setShopId(dto.getShopId());
        entity.setParentId(dto.getParentId());
        entity.setExternalId(dto.getExternalId());
        entity.setAllegroId(dto.getAllegroId());
        entity.setAllegroParentId(dto.getAllegroParentId());
        entity.setAllegroLeaf(dto.getAllegroLeaf());
        entity.setAllegroChildren(dto.getAllegroChildren());
        entity.setType(dto.getType());

        return entity;
    }

    protected ShopCategory getOne(Long id) {
        return repository.findById(id);
    }
    protected ShopCategory getExtOne(Long shopId,Long id) {
        return repository.findByExternalIdAndShopId(id.intValue(),shopId.intValue());
    }
}
