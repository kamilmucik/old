package pl.estrix.app.lib.woocommerce.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WooProductDto extends WooBaseDto {

    private Long id;
    private String name;
    private String slug;
    private String permalink;
    private String date_created;
//    private String date_created_gmt;
//    private String date_modified;
//    private String date_modifieed_gmt;
    private String type;
    private String status;
//    private Boolean featured;
//    private String catalog_visibility;
    private String description;
//    private String short_description;
    private String sku;
    private String price;
    private String regular_price;
//    private Float sale_price;
//    private String date_on_sale_from;
//    private String date_on_sale_from_gmt;
//    private String date_on_sale_to;
//    private String date_on_sale_to_gmt;
//    private String price_html;
//    private Boolean on_sale;
//    private Boolean purchasable;
//    private Integer total_sales;
//    private Boolean virtual;
//    private Boolean downloadable;
//    private String downloads;
//    private Integer download_limit;
//    private Integer download_expiry;
//    private String external_url;
//    private String button_text;
//    private String tax_status;
//    private String tax_class;
    private Boolean manage_stock;
    private Integer stock_quantity;
    private Boolean in_stock;
//    private String backorders;
//    private Boolean backorders_allowed;
//    private Boolean sold_individually;
//    private String weight;
//    private String dimensions;
//    private Boolean shipping_required;
//    private Boolean shipping_taxable;
//    private String shipping_class;
//    private Integer shipping_class_id;
//    private Boolean reviews_allowed;
//    private BigDecimal average_rating;
//    private Integer rating_count;
//    private String related_ids;
//    private String upsell_ids;
//    private String cross_sell_ids;
//    private Integer parent_id;
//    private String purchase_note;
    private List<WooCategoryDto> categories;
//    private String categories;
//    private String tags;
//    private String images;
    private List<WooImageDto> images;
//    private String attributes;
//    private String default_attributes;
//    private String variations;
//    private String grouped_products;
//    private Integer menu_order;
//    private String meta_data;
//    private String jetpack_publicize_connections;
//    private String _links;
//    private String code;
//    private String message;

    @Builder
    public WooProductDto(String code, String message,Long id, WooBaseDataDto data, String name, String slug, String permalink, String date_created, String type, String status, String description, String sku, String price, String regular_price, Boolean manage_stock, Integer stock_quantity, Boolean in_stock, List<WooCategoryDto> categories, List<WooImageDto> images) {
        super(code, message, data);
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.permalink = permalink;
        this.date_created = date_created;
        this.type = type;
        this.status = status;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.regular_price = regular_price;
        this.manage_stock = manage_stock;
        this.stock_quantity = stock_quantity;
        this.in_stock = in_stock;
        this.categories = categories;
        this.images = images;
//        this.code = code1;
//        this.message = message1;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public WooBaseDataDto getData() {
        return data;
    }

    @Override
    public String toString() {
        return "WooProductDto{" +
                ", slug='" + slug + '\'' +
                ", status='" + status + '\'' +
                ", date_created='" + date_created + '\'' +
//                ", images='" + images.size() + '\'' +
                '}';
    }
}
