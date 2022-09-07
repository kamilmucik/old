package pl.estrix.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.primefaces.model.SortOrder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockSearchCriteriaDto extends AbstractSearchCriteriaDto {

    private String tableSearch;
    private Boolean active;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
