package pl.estrix.app.backend.stock.model;

import lombok.*;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Stock extends AuditableEntity {

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "update_hour", length = 250, nullable = false)
    private String updateHour;

    @Column(name = "url", length = 250, nullable = false)
    private String url;

    @Column(name = "api_url", length = 250, nullable = false)
    private String apiUrl;

    @Column(name = "product_counter")
    private Integer productCounter = 0;

    @Column(name = "status_progress")
    private String status;

    @Column(name = "site_scratch")
    private Boolean siteScratch;

//    @Column(name = "last_update")
//    @Convert(converter = LocalDateTimeToStringConverter.class)
//    private LocalDateTime lastUpdate;

}
