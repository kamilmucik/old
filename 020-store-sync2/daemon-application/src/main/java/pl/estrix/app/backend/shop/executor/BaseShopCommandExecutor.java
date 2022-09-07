package pl.estrix.app.backend.shop.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.shop.dao.ShopRepository;
import pl.estrix.app.backend.shop.model.Shop;
import pl.estrix.app.common.dto.model.ShopDto;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseShopCommandExecutor extends BaseCommandExecutor<Shop, ShopDto> {
    @Autowired
    protected ShopRepository repository;

    @Override
    protected Class<ShopDto> getDtoClass() {
        return ShopDto.class;
    }

    protected ShopDto mapToDto(Shop entity) {
        if (entity == null){
            return null;
        }
        ShopDto dto = new ShopDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        dto.setUrl(entity.getUrl());
        dto.setApiUrl(entity.getApiUrl());
        dto.setConsumerKey(entity.getConsumerKey());
        dto.setConsumerSecret(entity.getConsumerSecret());
        dto.setStatus(entity.getStatus());
        dto.setLastUpdated(entity.getLastUpdate());

        if (entity.getStockIds() != null) {
            String citiesCommaSeparated = entity.getStockIds();
            if (citiesCommaSeparated.startsWith(",")){
                citiesCommaSeparated = citiesCommaSeparated.substring(1,citiesCommaSeparated.length());
            }
            ArrayList<String> list =
                    Stream.of(citiesCommaSeparated.split(","))
                            .collect(Collectors.toCollection(ArrayList::new));
            dto.getStockIds().addAll(list);
        }

        return dto;
    }

    protected Shop mapToEntity(ShopDto dto){
        if (dto == null){
            return null;
        }
        Shop entity = new Shop();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setApiUrl(dto.getApiUrl());
        entity.setConsumerKey(dto.getConsumerKey());
        entity.setConsumerSecret(dto.getConsumerSecret());
        entity.setStatus(dto.getStatus());
        entity.setLastUpdate(dto.getLastUpdated());

        String citiesCommaSeparated ="";
        if (dto.getId()!=null && dto.getStockIds()!=null) {
            citiesCommaSeparated = String.join(",", dto.getStockIds());
            if (citiesCommaSeparated.startsWith(",")) {
                citiesCommaSeparated = citiesCommaSeparated.substring(1, citiesCommaSeparated.length());
            }
        }
        entity.setStockIds(citiesCommaSeparated);

        return entity;
    }

    protected Shop getOne(Long id) {
        return repository.findById(id);
    }
}
