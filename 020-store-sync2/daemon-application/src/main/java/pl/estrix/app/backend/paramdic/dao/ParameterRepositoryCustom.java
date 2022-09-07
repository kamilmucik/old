package pl.estrix.app.backend.paramdic.dao;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.ParameterSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ParameterDto;

import java.util.List;

public interface ParameterRepositoryCustom {

    List<ParameterDto> find(ParameterSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(ParameterSearchCriteriaDto searchCriteria);
}
