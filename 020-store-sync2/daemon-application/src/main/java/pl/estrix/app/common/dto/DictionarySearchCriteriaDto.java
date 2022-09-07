package pl.estrix.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionarySearchCriteriaDto {

    private String name;

    private String userId;

    private String dictionaryName;

    private Long parameterId;
}
