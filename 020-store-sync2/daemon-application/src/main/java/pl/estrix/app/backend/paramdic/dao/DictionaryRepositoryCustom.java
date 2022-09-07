package pl.estrix.app.backend.paramdic.dao;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.DictionarySearchCriteriaDto;
import pl.estrix.app.common.dto.model.DictionaryDto;

import java.util.List;

public interface DictionaryRepositoryCustom {

    List<DictionaryDto> find(DictionarySearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(DictionarySearchCriteriaDto searchCriteria);
}
