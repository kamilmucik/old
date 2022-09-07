package pl.estrix.app.backend.settings.dao;

import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SettingDto;

import java.util.List;

public interface SettingRepositoryCustom {

    List<SettingDto> find(SettingSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);
//
    long findCount(SettingSearchCriteriaDto searchCriteria);

}
