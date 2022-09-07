package pl.estrix.app.backend.shop.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "shop",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Shop extends AuditableEntity {

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "url", length = 250, nullable = false)
    private String url;

    @Column(name = "api_url", length = 250, nullable = false)
    private String apiUrl;

    @Column(name = "stock_ids")
    private String stockIds;

    @Column(name = "consumer_key")
    private String consumerKey;

    @Column(name = "consumer_secret")
    private String consumerSecret;

    @Column(name = "status_progress")
    private String status;
}
