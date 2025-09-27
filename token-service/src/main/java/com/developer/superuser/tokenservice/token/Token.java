package com.developer.superuser.tokenservice.token;

import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.TokenScheme;
import com.developer.superuser.shared.openapi.contract.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Token implements Serializable {
    @Serial
    private static final long serialVersionUID = 3_458_815_117_668_012_404L;
    private String clientId;
    private String signature;
    private Instant timestamp;
    @JsonIgnore
    private TokenType tokenType;
    private String grantType;
    private String authCode;
    @JsonProperty("tokenType")
    private TokenScheme tokenScheme;
    private String accessToken;
    private Instant accessTokenExpiryTime;
    private String refreshToken;
    private Instant refreshTokenExpiryTime;
    private Integer expiresIn;
    private String additionalInfo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ErrorData error;
}