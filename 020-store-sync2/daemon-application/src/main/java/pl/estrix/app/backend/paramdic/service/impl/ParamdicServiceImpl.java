package pl.estrix.app.backend.paramdic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.paramdic.executor.*;
import pl.estrix.app.backend.paramdic.service.ParamdicService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.DictionarySearchCriteriaDto;
import pl.estrix.app.common.dto.ParameterSearchCriteriaDto;
import pl.estrix.app.common.dto.model.DictionaryDto;
import pl.estrix.app.common.dto.model.ParameterDto;

@Service
@Primary
public class ParamdicServiceImpl implements ParamdicService {

    @Autowired
    private ReadDictionaryCommandExecutor readDictionaryExecutor;
    @Autowired
    private CreateDictionaryCommandExecutor createDictionaryExecutor;
    @Autowired
    private DeleteDictionaryCommandExecutor deleteDictionaryExecutor;
    @Autowired
    private ReadParameterCommandExecutor readParameterExecutor;
    @Autowired
    private CreateParameterCommandExecutor createParameterExecutor;
    @Autowired
    private DeleteParameterCommandExecutor deleteParameterExecutor;


    @Override
    public DictionaryDto getDictionary(Long id) {
        return readDictionaryExecutor.findById(id);
    }

    @Override
    public DictionaryDto createDictionary(DictionaryDto dto) {
        return createDictionaryExecutor.create(dto);
    }

    @Override
    public ListResponseDto<DictionaryDto> getItems(DictionarySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<DictionaryDto> listResponseDto = readDictionaryExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public ParameterDto getParameter(Long id) {
        return readParameterExecutor.findById(id);
    }

    @Override
    public ParameterDto createParameter(ParameterDto dto) {
        return createParameterExecutor.create(dto);
    }

    @Override
    public ListResponseDto<ParameterDto> getItems(ParameterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<ParameterDto> listResponseDto = readParameterExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public void deleteAllDictionary(String sellerId) {
        deleteDictionaryExecutor.deleteAllBySellerId(sellerId);
        deleteParameterExecutor.deleteAllBySellerId(sellerId);
    }
}
