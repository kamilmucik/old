package pl.estrix.app.lib.allegro.model;

import java.util.List;

public class AllegroOffertListDto {

    private List<AllegroOffertDto> offers;

    private Integer count;

    private Integer totalCount;

    public AllegroOffertListDto() {
    }

    public AllegroOffertListDto(List<AllegroOffertDto> offers, Integer count, Integer totalCount) {
        this.offers = offers;
        this.count = count;
        this.totalCount = totalCount;
    }

    public List<AllegroOffertDto> getOffers() {
        return offers;
    }

    public void setOffers(List<AllegroOffertDto> offers) {
        this.offers = offers;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "AllegroOffertListDto{" +
                "offers=" + offers.size() +
                ", count=" + count +
                ", totalCount=" + totalCount +
                '}';
    }
}
