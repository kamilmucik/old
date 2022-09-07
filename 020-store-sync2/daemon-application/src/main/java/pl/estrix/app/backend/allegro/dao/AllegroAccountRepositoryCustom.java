package pl.estrix.app.backend.allegro.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

import java.util.List;

public interface AllegroAccountRepositoryCustom {

    List<AllegroAccountDto> find(AllegroAccountSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    long findCount(AllegroAccountSearchCriteriaDto searchCriteria);
}
