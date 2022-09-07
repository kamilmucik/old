package pl.estrix.app.frontend.web.dashboard.event;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.event.service.EventService;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.EventSearchCriteriaDto;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.model.EventDto;
import pl.estrix.app.common.dto.model.SettingDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class EventLazyDataModel extends LazyDataModel<EventDto> {

    private List<EventDto> datasource = new ArrayList<>();

    private String tableSearch;

    @Autowired
    private EventService service;

    public EventLazyDataModel(EventService service, String tableSearch) {
        this.service = service;
        this.tableSearch = tableSearch;
    }

    @Override
    public EventDto getRowData(String rowKey) {
        if (datasource != null) {
            Long selectedId = Long.parseLong(rowKey);
            for (EventDto dto : datasource) {
                if (dto.getId().equals(selectedId))
                    return dto;
            }
        }else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("details.html?id=" + rowKey + "&table_page=0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(EventDto dto) {
        return dto.getId();
    }

    @Override
    public List<EventDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        EventSearchCriteriaDto searchCriteria = new EventSearchCriteriaDto();

        searchCriteria.setTableSearch(tableSearch);

        if (sortField != null) {
            searchCriteria.setSortField(sortField);
            searchCriteria.setSortOrder(sortOrder);
        }

        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(first)
                .page(0)
                .limit(pageSize)
                .build();
        ListResponseDto<EventDto> responseDto = service.getItems(searchCriteria,pagingCriteria);

        datasource.clear();
        datasource.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return datasource;
    }
}
