package pl.estrix.app.lib.allegro.model;

public class AllegroOffertPaymentsDto {

    private String invoice;

    public AllegroOffertPaymentsDto() {
    }

    public AllegroOffertPaymentsDto(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
