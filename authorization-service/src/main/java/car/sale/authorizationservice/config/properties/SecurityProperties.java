package car.sale.authorizationservice.config.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
//@RefreshScope
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {
    private ClientSettings clientSettings;
    private TokenSettings tokenSettings;
    private String dataProviderUri;

    @Setter
    static class ClientSettings {
        private String clientId;
        private String clientSecret;
        private String resaleLoginUri;
        private String resaleSuccessLoginUri;
    }

    @Setter
    static class TokenSettings {
        private Integer accessTokenLifetimeInMinutes;
        private Integer refreshTokenLifetimeInMinutes;
    }

    public String getClientId() {
        return clientSettings.clientId;
    }

    public String getClientSecret() {
        return clientSettings.clientSecret;
    }

    public String getResaleLoginUri() {
        return clientSettings.resaleLoginUri;
    }

    public String getDataProviderUri() {
        return dataProviderUri;
    }

    public String getResaleSuccessLoginUri() {
        return clientSettings.resaleSuccessLoginUri;
    }

    public Integer getAccessTokenLifetimeInMinutes() {
        return tokenSettings.accessTokenLifetimeInMinutes;
    }

    public Integer getRefreshTokenLifetimeInMinutes() {
        return tokenSettings.refreshTokenLifetimeInMinutes;
    }

}