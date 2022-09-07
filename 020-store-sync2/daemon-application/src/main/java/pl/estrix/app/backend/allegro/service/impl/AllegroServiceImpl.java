package pl.estrix.app.backend.allegro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.app.backend.allegro.executor.CreateAllegroAccountCommandExecutor;
import pl.estrix.app.backend.allegro.executor.DeleteAllegroAccountCommandExecutor;
import pl.estrix.app.backend.allegro.executor.ReadAllegroAccountCommandExecutor;
import pl.estrix.app.backend.allegro.service.AllegroService;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.AllegroAccountSearchCriteriaDto;
import pl.estrix.app.common.dto.model.AllegroAccountDto;

@Service
@Primary
public class AllegroServiceImpl implements AllegroService {

    @Autowired
    private ReadAllegroAccountCommandExecutor readExecutor;
    @Autowired
    private DeleteAllegroAccountCommandExecutor deleteExecutor;
    @Autowired
    private CreateAllegroAccountCommandExecutor createExecutor;


    @Override
    public ListResponseDto<AllegroAccountDto> getItems(AllegroAccountSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        ListResponseDto<AllegroAccountDto> listResponseDto = readExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Override
    public AllegroAccountDto create(AllegroAccountDto dto) {
        return createExecutor.create(dto);
    }

    @Override
    public void delete(Long itemId) {
        deleteExecutor.delete(itemId);
    }

    @Override
    public AllegroAccountDto get(Long id) {
        return readExecutor.findById(id);
    }
}
