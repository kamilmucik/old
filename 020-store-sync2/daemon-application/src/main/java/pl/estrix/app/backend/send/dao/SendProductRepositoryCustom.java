package pl.estrix.app.backend.send.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;

import java.util.List;

public interface SendProductRepositoryCustom {

    List<SendProductDto> find(SendProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(SendProductSearchCriteriaDto searchCriteria);

}
