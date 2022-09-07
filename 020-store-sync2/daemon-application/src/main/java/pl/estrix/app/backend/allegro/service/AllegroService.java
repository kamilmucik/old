package pl.estrix.app.backend.allegro.service;


import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

public interface AllegroService {

    ListResponseDto<AllegroAccountDto> getItems(AllegroAccountSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria);

    AllegroAccountDto create(AllegroAccountDto dto);

    void delete(Long itemId);

    AllegroAccountDto get(Long id);
}
