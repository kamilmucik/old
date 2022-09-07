package pl.estrix.app.lib.allegro.model;

import java.util.ArrayList;
import java.util.List;

public class AllegroOffertRequest {

    private String id;
    private String name;
    private String ean;
    private AllegroCategoryDto category;
    private List<AllegroOffertParameterDto> parameters;
    private AllegroOffertDescriptionDto description;
    private List<AllegroOffertImageDto> images;
    private AllegroOffertSellingModeDto sellingMode;
    private AllegroOffertStockDto stock;
    private AllegroOffertPublicationDto publication;
    private AllegroOffertDeliveryDto delivery;
    private AllegroOffertPaymentsDto payments;
    private AllegroOffertLocationDto location;
    private AllegroOffertAfterSalesServicesDto afterSalesServices;

    public AllegroOffertRequest() {
    }

    public AllegroOffertRequest(String id, String name, AllegroCategoryDto category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    private AllegroOffertRequest(Builder builder) {
        this.id = builder.get_id();
        this.name = builder.get_name();
        this.ean = builder.get_ean();
        this.category = new AllegroCategoryDto(builder.get_category());

        if (parameters == null){
            parameters = new ArrayList<>();
        }
        for (String param : builder.get_paramas()){
            String parId = param.substring(0, param.indexOf("_"));
            AllegroOffertParameterDto paramDto = new AllegroOffertParameterDto();
            paramDto.setId(parId);
            paramDto.addValuesId(param);
            parameters.add(paramDto);
        }

        description = new AllegroOffertDescriptionDto();
        AllegroOffertDescriptionSectionDto sectionDto = new AllegroOffertDescriptionSectionDto();
        int itemIndx = 0;
        for (String item : builder.get_description_items()){
            String itemType = item.substring(0, item.indexOf("_"));
            String itemVal = item.substring(itemType.length()+1);

            if (itemIndx == 0 || itemIndx%2 ==0){
                if (sectionDto!=null && sectionDto.getItems() != null && sectionDto.getItems().size() > 0) {
                    description.addSection(sectionDto);
                }
                sectionDto = new AllegroOffertDescriptionSectionDto();
            }
            if (sectionDto!= null){
                if ("TEXT".equals(itemType)){
                    sectionDto.addContent(itemType,itemVal);
                }else{
                    sectionDto.addImage(itemType,itemVal);
                }
            }
            itemIndx++;
        }
        description.addSection(sectionDto);

        if (images == null){
            images = new ArrayList<>();
        }
        for (String iamgeUrl : builder.get_images()){
            images.add(new AllegroOffertImageDto(iamgeUrl));
        }

        sellingMode = new AllegroOffertSellingModeDto(builder.get_sellingMode(),builder.get_price());
        stock = new AllegroOffertStockDto(builder.get_available(),builder.get_unit());
        publication = new AllegroOffertPublicationDto(builder.get_duration(),builder.get_status());
        delivery = new AllegroOffertDeliveryDto(builder.get_shippingId(),builder.get_handlingTime(),builder.get_additionalInfo());
        payments = new AllegroOffertPaymentsDto(builder.get_payments());
        location = new AllegroOffertLocationDto(builder.get_countryCode(),builder.get_province(),builder.get_city(),builder.get_postCode());
        afterSalesServices = new AllegroOffertAfterSalesServicesDto(builder.get_impliedWarranty(),builder.get_returnPolicy());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AllegroCategoryDto getCategory() {
        return category;
    }

    public void setCategory(AllegroCategoryDto category) {
        this.category = category;
    }

    public static class Builder {

        private String _id;
        private String _name;
        private String _category;
        private String _sellingMode;
        private String _price;
        private String _ean;
        private List<String> _paramas = new ArrayList<>();
        private List<String> _description_items = new ArrayList<>();
        private List<String> _images = new ArrayList<>();
        private Integer _available;
        private String _unit;
        private String _duration;
        private String _status;
        private String _shippingId;
        private String _handlingTime;
        private String _additionalInfo;
        private String _payments;
        private String _countryCode;
        private String _province;
        private String _city;
        private String _postCode;
        private String _impliedWarranty;
        private String _returnPolicy;

        public Builder() {
        }

        public Builder id(String id){
            this._id = id;
            return this;
        }
        public Builder name(String name){
            this._name = name;
            return this;
        }
        public Builder category(String category){
            this._category = category;
            return this;
        }
        public Builder ean(String ean){
            this._ean = ean;
            return this;
        }
        public Builder addSellinMode(String sellingMode, String price){
            this._sellingMode = sellingMode;
            this._price = price;
            return this;
        }
        public Builder addStock(Integer available, String unit){
            this._available = available;
            this._unit = unit;
            return this;
        }

//         -- wymagane, czas trwania oferty, dostępne
//        wartości: null (do wyczerpania zapasów),
//        P3D (3 dni); P5D (5 dni); P7D (7 dni);
//        P10D (10 dni); P20D (20 dni); P30D (30 dni), czas
//        możesz podać też w godzinach - np.: P72H (3 dni).
//        Dla oferty typu AUCTION (licytacja) nie możesz
//        podać wartości null i dostępne są wartości:
//        P3D (3 dni); P5D (5 dni); P7D (7 dni); P10D (10 dni);
//        Natomiast dla ofert typu ADVERTISMENT (ogłoszenia)
//        dostępne są wartości: null (na czas nieokreślony -
//        opłaty naliczane co 10 dni); P10D (10 dni);
//        P20D (20 dni); P30D (30 dni)
        public Builder addPublication(String duration, String status){
            this._duration = duration;
            this._status = status;
            return this;
        }
//        -- czas wysyłki, obecnie dostępne są
//        wartości: PT0S (natychmiast), PT24H (24 godziny),
//        P2D (2 dni), P3D (3 dni), P4D (4 dni), P5D (5 dni),
//        P7D (7 dni), P10D (10 dni), P14D (14 dni),
//        P21D (21 dni), P30D (30 dni), P60D (60 dni).
//        Można również podać te wartości
//        w godzinach, np. PT72H (3 dni).
//        Niewymagane dla formatu sprzedaży
//        typu ADVERTISEMENT (ogłoszenie).
        public Builder addDelivery(String shippingId, String handlingTime, String additionalInfo){
            this._shippingId = shippingId;
            this._handlingTime = handlingTime;
            this._additionalInfo = additionalInfo;
            return this;
        }
//        -- wymagane, informacja o fakturze;
//        obecnie dostępne są 4 wartości: VAT (faktura VAT);
//        VAT_MARGIN (faktura VAT marża);
//        WITHOUT_VAT (faktura bez VAT);
//        NO_INVOICE (nie wystawiam faktury)
        public Builder addPayments(String payments){
            this._payments = payments;
            return this;
        }
//"countryCode": "PL",                    -- wymagane, kod kraju zgodny ze
//        standardem ISO 3166-1 alpha-2
//                "province": "WIELKOPOLSKIE",            -- wymagane dla countryCode “PL”, województwo,
//        dostępne wartości: DOLNOSLASKIE; KUJAWSKO_POMORSKIE;
//        LUBELSKIE; LUBUSKIE; LODZKIE; MALOPOLSKIE; MAZOWIECKIE;
//        OPOLSKIE; PODKARPACKIE; PODLASKIE; POMORSKIE; SLASKIE;
//        SWIETOKRZYSKIE; WARMINSKO_MAZURSKIE;
//        WIELKOPOLSKIE; ZACHODNIOPOMORSKIE.
//        Dla innych krajów nie należy podawać tej wartości.
//                "city": "Poznań",                       -- wymagane, miejscowość
//        "postCode": "60-166"                    -- wymagane dla adresów w Polsce, kod pocztowy
        public Builder addLocation(String countryCode, String province, String city, String postCode){
            this._countryCode = countryCode;
            this._province = province;
            this._city = city;
            this._postCode = postCode;
            return this;
        }
        public Builder addAfterSellService(String impliedWarranty, String returnPolicy){
            this._impliedWarranty = impliedWarranty;
            this._returnPolicy = returnPolicy;
            return this;
        }
        public Builder addParams(List<String> params){

            if (_paramas == null){
                _paramas = new ArrayList<>();
            }

            for (String param: params) {
                this._paramas.add(param);
            }
            return this;
        }
        public Builder addParam(String param){

            if (_paramas == null){
                _paramas = new ArrayList<>();
            }

            this._paramas.add(param);
            return this;
        }
        public Builder addImage(String imageUrl){

            if (_images == null){
                _images = new ArrayList<>();
            }

            this._images.add(imageUrl);
            return this;
        }
        public Builder addImage(List<String> imageUrls){

            if (_images == null){
                _images = new ArrayList<>();
            }

            for (String imageUrl : imageUrls) {
                this._images.add(imageUrl);
            }
            return this;
        }
        public Builder addDescriptionSection(String type, String val){
            if (_description_items == null){
                _description_items = new ArrayList<>();
            }
            this._description_items.add(type+"_"+val);
            return this;
        }
        public Builder addDescriptionSection(String type, List<String> vals){
            if (_description_items == null){
                _description_items = new ArrayList<>();
            }
            for (String val : vals) {
                this._description_items.add(type + "_" + val);
            }
            return this;
        }

        public AllegroOffertRequest build(){
            return new AllegroOffertRequest(this);
        }

        public String get_id() {
            return _id;
        }

        public String get_name() {
            return _name;
        }
        public String get_ean() {
            return _ean;
        }

        public String get_category() {
            return _category;
        }

        public List<String> get_paramas() {
            return _paramas;
        }
        public List<String> get_description_items() {
            return _description_items;
        }
        public List<String> get_images() {
            return _images;
        }

        public String get_sellingMode() {
            return _sellingMode;
        }

        public String get_price() {
            return _price;
        }

        public Integer get_available() {
            return _available;
        }

        public String get_unit() {
            return _unit;
        }

        public String get_duration() {
            return _duration;
        }

        public String get_status() {
            return _status;
        }

        public String get_shippingId() {
            return _shippingId;
        }

        public String get_handlingTime() {
            return _handlingTime;
        }

        public String get_additionalInfo() {
            return _additionalInfo;
        }

        public String get_payments() {
            return _payments;
        }

        public String get_countryCode() {
            return _countryCode;
        }

        public String get_province() {
            return _province;
        }

        public String get_city() {
            return _city;
        }

        public String get_postCode() {
            return _postCode;
        }

        public String get_impliedWarranty() {
            return _impliedWarranty;
        }

        public String get_returnPolicy() {
            return _returnPolicy;
        }
    }
}
