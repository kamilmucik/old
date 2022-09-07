package pl.estrix.app.backend.paramdic.service;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.DictionarySearchCriteriaDto;
import pl.estrix.app.common.dto.ParameterSearchCriteriaDto;
import pl.estrix.app.common.dto.model.DictionaryDto;
import pl.estrix.app.common.dto.model.ParameterDto;

public interface ParamdicService {

    DictionaryDto getDictionary(Long id);

    DictionaryDto createDictionary(DictionaryDto dto);

    ListResponseDto<DictionaryDto> getItems(DictionarySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    ParameterDto getParameter(Long id);

    ParameterDto createParameter(ParameterDto dto);

    ListResponseDto<ParameterDto> getItems(ParameterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    void deleteAllDictionary(String sellerId);
}
