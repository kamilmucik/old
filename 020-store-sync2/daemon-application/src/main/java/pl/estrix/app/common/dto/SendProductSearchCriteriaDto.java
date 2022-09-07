package pl.estrix.app.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SendProductSearchCriteriaDto extends AbstractSearchCriteriaDto{

    private String tableSearch;

    private Long productId;

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
