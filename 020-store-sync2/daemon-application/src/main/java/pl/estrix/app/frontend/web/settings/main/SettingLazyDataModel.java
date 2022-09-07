package pl.estrix.app.frontend.web.settings.main;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.settings.service.SettingService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SettingSearchCriteriaDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.SettingDto;
import pl.estrix.app.common.dto.model.ShopDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class SettingLazyDataModel extends LazyDataModel<SettingDto> {

    private List<SettingDto> datasource = new ArrayList<>();

    private String tableSearch;

    @Autowired
    private SettingService service;

    public SettingLazyDataModel(SettingService shopService, String tableSearch) {
        this.service = shopService;
        this.tableSearch = tableSearch;
    }

    @Override
    public SettingDto getRowData(String rowKey) {
        if (datasource != null) {
            Long selectedId = Long.parseLong(rowKey);
            for (SettingDto dto : datasource) {
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
    public Object getRowKey(SettingDto dto) {
        return dto.getId();
    }

    @Override
    public List<SettingDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        SettingSearchCriteriaDto searchCriteria = new SettingSearchCriteriaDto();

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
        ListResponseDto<SettingDto> responseDto = service.getItems(searchCriteria,pagingCriteria);

        datasource.clear();
        datasource.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return datasource;
    }
}
