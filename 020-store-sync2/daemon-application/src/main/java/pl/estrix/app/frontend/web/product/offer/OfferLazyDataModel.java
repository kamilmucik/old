package pl.estrix.app.frontend.web.product.offer;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.app.backend.base.PagingCriteria;
import pl.estrix.app.backend.send.service.SendProductService;
import pl.estrix.app.backend.shop.service.ShopService;
import pl.estrix.app.common.base.ListResponseDto;
import pl.estrix.app.common.dto.SendProductSearchCriteriaDto;
import pl.estrix.app.common.dto.ShopSearchCriteriaDto;
import pl.estrix.app.common.dto.model.SendProductDto;
import pl.estrix.app.common.dto.model.ShopDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class OfferLazyDataModel extends LazyDataModel<SendProductDto> {

    private List<SendProductDto> datasource = new ArrayList<>();

    private String tableSearch;

    @Autowired
    private SendProductService service;

    public OfferLazyDataModel(SendProductService shopService, String tableSearch) {
        this.service = shopService;
        this.tableSearch = tableSearch;
    }

    @Override
    public SendProductDto getRowData(String rowKey) {
        if (datasource != null) {
            Long selectedId = Long.parseLong(rowKey);
            for (SendProductDto dto : datasource) {
                if (dto.getId().equals(selectedId))
                    return dto;
            }
        }else {
            Integer selectedRow = (Integer.parseInt(rowKey)-1) % this.getPageSize();
            SendProductDto dto = this.getWrappedData().get(selectedRow);
            return dto;
        }
        return null;
    }

    @Override
    public Object getRowKey(SendProductDto dto) {
        return dto.getId();
    }

    @Override
    public List<SendProductDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        SendProductSearchCriteriaDto searchCriteria = new SendProductSearchCriteriaDto();
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
        ListResponseDto<SendProductDto> responseDto = service.getItems(searchCriteria,pagingCriteria);

        datasource.clear();
        datasource.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return datasource;
    }
}
