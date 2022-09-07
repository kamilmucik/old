package pl.estrix.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.primefaces.model.SortOrder;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockProductSearchCriteriaDto extends AbstractSearchCriteriaDto {

    private String tableSearch;
    private Integer selectedStock;
    private Boolean active;

    private BigDecimal priceFrom = new BigDecimal(0);
    private BigDecimal priceTo = new BigDecimal(0);
    private BigDecimal stockFrom = new BigDecimal(0);
    private BigDecimal stockTo = new BigDecimal(0);
    private BigDecimal stock = new BigDecimal(0);
    private BigDecimal stockExt = new BigDecimal(0);

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
