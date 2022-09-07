package pl.estrix.app.common.dto.model;

import lombok.*;
import pl.estrix.app.common.base.BaseEntityDto;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AllegroAccountDto extends BaseEntityDto<Long> {

    private String name;

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private Integer expiresIn;

    private String scope;

    private String userCode;

    private String deviceCode;

    private String verificationUri;

    private String verificationUriComplete;

    private Integer interval;

    private String sellerId;

    private String clientId;

    private String jti;
    private String countryCode;
    private String province;
    private String city;
    private String postCode;

    private Boolean defaultAccount;

    @Override
    public String toString() {
        return "AllegroAccountDto{" +
                "name='" + name + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
