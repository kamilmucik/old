package pl.estrix.app.frontend.web.settings.shop;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.ShopDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ShopLazyDataModel extends LazyDataModel<ShopDto> {

    private List<ShopDto> datasource;

    private String tableSearch;

    @Autowired
    private ShopService service;

    public ShopLazyDataModel(ShopService shopService, String tableSearch) {
        this.service = shopService;
        this.tableSearch = tableSearch;
    }

    @Override
    public ShopDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ShopDto dto : datasource) {
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
    public Object getRowKey(ShopDto dto) {
        return dto.getId();
    }

    @Override
    public List<ShopDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ShopDto> data = new ArrayList<>();

        ShopSearchCriteriaDto searchCriteria = new ShopSearchCriteriaDto();

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
        ListResponseDto<ShopDto> responseDto = service.getItems(searchCriteria,pagingCriteria);

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
