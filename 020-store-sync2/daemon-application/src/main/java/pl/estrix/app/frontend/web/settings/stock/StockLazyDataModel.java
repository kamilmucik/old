package pl.estrix.app.frontend.web.settings.stock;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class StockLazyDataModel extends LazyDataModel<StockDto> {

    private List<StockDto> datasource;

    private String tableSearch;

    @Autowired
    private StockService service;

    public StockLazyDataModel(StockService service, String tableSearch) {
        this.service = service;
        this.tableSearch = tableSearch;
    }

    @Override
    public StockDto getRowData(String rowKey) {
        if (datasource != null) {
            for (StockDto dto : datasource) {
                if (dto.getId().equals(rowKey))
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
    public Object getRowKey(StockDto dto) {
        return dto.getId();
    }

    @Override
    public List<StockDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<StockDto> data = new ArrayList<>();

        StockSearchCriteriaDto searchCriteria = new StockSearchCriteriaDto();

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
        ListResponseDto<StockDto> responseDto = service.getItems(searchCriteria,pagingCriteria);

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
