package pl.estrix.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterSearchCriteriaDto {

    private String name;
    private Long categoryId;
    private String allegroId;
    private Boolean required;

}
