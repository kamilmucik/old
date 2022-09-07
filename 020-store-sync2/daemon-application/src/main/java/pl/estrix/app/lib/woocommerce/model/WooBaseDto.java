package pl.estrix.app.lib.woocommerce.model;


import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class WooBaseDto implements Serializable {

    protected String code;

    protected String message;

    protected WooBaseDataDto data;


}
