package pl.estrix.app.lib.allegro.model;

public class AllegroPriceDto {

    private String amount;

    private String currency;

    public AllegroPriceDto() {
    }

    public AllegroPriceDto(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "AllegroPriceDto{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
