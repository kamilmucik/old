package pl.estrix.app.backend.paramdic.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.app.backend.base.BaseCommandExecutor;
import pl.estrix.app.backend.paramdic.dao.ParameterRepository;
import pl.estrix.app.backend.paramdic.model.Parameter;
import pl.estrix.app.common.dto.model.ParameterDto;

public class BaseParameterCommandExecutor extends BaseCommandExecutor<Parameter, ParameterDto> {

    @Autowired
    protected ParameterRepository repository;

    @Override
    protected Class<ParameterDto> getDtoClass() {
        return ParameterDto.class;
    }

    protected ParameterDto mapToDto(Parameter entity) {
        if (entity == null){
            return null;
        }
        ParameterDto dto = new ParameterDto();
        dto.setId(entity.getId());
        dto.setAllegroID(entity.getAllegroID());
        dto.setName(entity.getName());
        dto.setRequired(entity.getRequired());
        dto.setType(entity.getType());
        dto.setUnit(entity.getUnit());
        dto.setCategoryId(entity.getCategoryId());
        dto.setAllegroCatId(entity.getAllegroCatId());

        return dto;
    }

    protected Parameter mapToEntity(ParameterDto dto){
        if (dto == null){
            return null;
        }
        Parameter entity = new Parameter();
        entity.setId(dto.getId());
        entity.setAllegroID(dto.getAllegroID());
        entity.setName(dto.getName());
        entity.setRequired(dto.getRequired());
        entity.setType(dto.getType());
        entity.setUnit(dto.getUnit());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAllegroCatId(dto.getAllegroCatId());

        return entity;
    }

    protected Parameter getOne(Long id) {
        return repository.findById(id);
    }

}
