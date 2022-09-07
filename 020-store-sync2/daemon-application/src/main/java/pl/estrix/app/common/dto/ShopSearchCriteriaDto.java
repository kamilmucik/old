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
public class ShopSearchCriteriaDto extends AbstractSearchCriteriaDto {

    private Boolean hand;

    private String tableSearch;

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
