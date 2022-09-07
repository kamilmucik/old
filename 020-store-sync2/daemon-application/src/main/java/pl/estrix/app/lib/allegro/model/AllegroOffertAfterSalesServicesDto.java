package pl.estrix.app.lib.allegro.model;

public class AllegroOffertAfterSalesServicesDto {

    private AllegroOffertImpliedWarrantyDto impliedWarranty;
    private AllegroOffertReturnPolicyDto returnPolicy;
    private AllegroOffertWarrantyDto warranty;

    public AllegroOffertAfterSalesServicesDto() {
    }

    public AllegroOffertAfterSalesServicesDto(String impliedWarranty, String returnPolicy) {
        this.impliedWarranty = new AllegroOffertImpliedWarrantyDto(impliedWarranty);
        this.returnPolicy = new AllegroOffertReturnPolicyDto(returnPolicy);
        this.warranty = null;
    }
    public AllegroOffertAfterSalesServicesDto(String impliedWarranty, String returnPolicy, String warranty) {
        this.impliedWarranty = new AllegroOffertImpliedWarrantyDto(impliedWarranty);
        this.returnPolicy = new AllegroOffertReturnPolicyDto(returnPolicy);
        this.warranty = new AllegroOffertWarrantyDto(warranty);
    }
    public AllegroOffertAfterSalesServicesDto(AllegroOffertImpliedWarrantyDto impliedWarranty, AllegroOffertReturnPolicyDto returnPolicy, AllegroOffertWarrantyDto warranty) {
        this.impliedWarranty = impliedWarranty;
        this.returnPolicy = returnPolicy;
        this.warranty = warranty;
    }

    public AllegroOffertImpliedWarrantyDto getImpliedWarranty() {
        return impliedWarranty;
    }

    public void setImpliedWarranty(AllegroOffertImpliedWarrantyDto impliedWarranty) {
        this.impliedWarranty = impliedWarranty;
    }

    public AllegroOffertReturnPolicyDto getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(AllegroOffertReturnPolicyDto returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public AllegroOffertWarrantyDto getWarranty() {
        return warranty;
    }

    public void setWarranty(AllegroOffertWarrantyDto warranty) {
        this.warranty = warranty;
    }
}
