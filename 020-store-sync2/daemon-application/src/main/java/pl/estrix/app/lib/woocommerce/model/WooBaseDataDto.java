package pl.estrix.app.lib.woocommerce.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WooBaseDataDto implements Serializable {

    private Integer status;

}
