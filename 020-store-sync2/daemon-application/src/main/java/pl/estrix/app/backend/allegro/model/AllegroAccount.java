package pl.estrix.app.backend.allegro.model;

import lombok.*;
import org.json.JSONObject;
import pl.estrix.app.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "allegro_account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AllegroAccount extends AuditableEntity {

    @Column(name = "name", length = 250)
    private String name;

//    JSONObject jsonObject;
//    Map entities = new HashMap();

    @Column(name = "access_token", length = 700)
    private String accessToken;

    @Column(name = "token_type", length = 250)
    private String tokenType;

    @Column(name = "refresh_token", length = 700)
    private String refreshToken;

    @Column(name = "expires_in", length = 250)
    private Integer expiresIn;

    @Column(name = "scope", length = 250)
    private String scope;

    @Column(name = "user_code", length = 250)
    private String userCode;

    @Column(name = "device_code", length = 250)
    private String deviceCode;

    @Column(name = "verification_uri", length = 250)
    private String verificationUri;

    @Column(name = "verification_uri_complete", length = 250)
    private String verificationUriComplete;

    @Column(name = "refresh_interval", length = 250)
    private Integer interval;



    @Column(name = "seller_id", length = 250)
    private String sellerId;
    @Column(name = "client_id", length = 250)
    private String clientId;
    @Column(name = "jti", length = 250)
    private String jti;

    @Column(name = "country_code", length = 250)
    private String countryCode;
    @Column(name = "province", length = 250)
    private String province;
    @Column(name = "city", length = 250)
    private String city;
    @Column(name = "post_code", length = 250)
    private String postCode;

    @Column(name = "default_account")
    private Boolean defaultAccount;

}
