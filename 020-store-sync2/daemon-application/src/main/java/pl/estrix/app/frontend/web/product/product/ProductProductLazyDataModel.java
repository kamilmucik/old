package pl.estrix.app.frontend.web.product.product;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.stock.service.StockService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.StockProductSearchCriteriaDto;
import pl.estrix.app.common.dto.StockSearchCriteriaDto;
import pl.estrix.app.common.dto.model.StockDto;
import pl.estrix.app.common.dto.model.StockProductDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ProductProductLazyDataModel extends LazyDataModel<StockProductDto> {

    private List<StockProductDto> datasource = new ArrayList<>();

    private String tableSearch;

    @Autowired
    private StockService service;
    private StockProductSearchCriteriaDto searchCriteriaDto;

    public ProductProductLazyDataModel(StockService service, StockProductSearchCriteriaDto searchCriteriaDto) {
        this.service = service;
        this.searchCriteriaDto = searchCriteriaDto;
    }
    public ProductProductLazyDataModel(StockService service, String tableSearch) {
        this.service = service;
        this.searchCriteriaDto = StockProductSearchCriteriaDto
                .builder()
                .tableSearch(tableSearch)
                .active(true)
                .build();
    }

    @Override
    public StockProductDto getRowData(String rowKey) {
        if (datasource != null) {
            Long selectedId = Long.parseLong(rowKey);
            for (StockProductDto dto : datasource) {
                if (dto.getId().equals(selectedId))
                    return dto;
            }
        } else {
            Integer selectedRow = (Integer.parseInt(rowKey)-1) % this.getPageSize();
            StockProductDto dto = this.getWrappedData().get(selectedRow);
            return dto;
        }
        return null;
    }

    @Override
    public Object getRowKey(StockProductDto dto) {
        return dto.getId();
    }

    @Override
    public List<StockProductDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        if (sortField != null) {
            searchCriteriaDto.setSortField(sortField);
            searchCriteriaDto.setSortOrder(sortOrder);
        }

        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(first)
                .page(0)
                .limit(pageSize)
                .build();
        ListResponseDto<StockProductDto> responseDto = service.getProductItems(searchCriteriaDto,pagingCriteria);

        datasource.clear();
        datasource.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return datasource;
    }
}
