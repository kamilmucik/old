package pl.estrix.app.backend.paramdic.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.paramdic.dao.DictionaryRepository;
import pl.estrix.app.backend.paramdic.model.Dictionary;
import pl.estrix.app.common.dto.model.DictionaryDto;

public class BaseDictionaryCommandExecutor extends BaseCommandExecutor<Dictionary, DictionaryDto> {

    @Autowired
    protected DictionaryRepository repository;

    @Override
    protected Class<DictionaryDto> getDtoClass() {
        return DictionaryDto.class;
    }

    protected DictionaryDto mapToDto(Dictionary entity) {
        if (entity == null){
            return null;
        }
        DictionaryDto dto = new DictionaryDto();
        dto.setId(entity.getId());
        dto.setAllegroID(entity.getAllegroID());
        dto.setAllegroName(entity.getAllegroName());
        dto.setParameterId(entity.getParameterId());
        dto.setUserId(entity.getUserId());
        dto.setDictionaryName(entity.getDictionaryName());

        return dto;
    }

    protected Dictionary mapToEntity(DictionaryDto dto){
        if (dto == null){
            return null;
        }
        Dictionary entity = new Dictionary();
        entity.setId(dto.getId());
        entity.setAllegroID(dto.getAllegroID());
        entity.setAllegroName(dto.getAllegroName());
        entity.setParameterId(dto.getParameterId());
        entity.setUserId(dto.getUserId());
        entity.setDictionaryName(dto.getDictionaryName());

        return entity;
    }

    protected Dictionary getOne(Long id) {
        return repository.findById(id);
    }

}
