package pl.estrix.app.lib.allegro;

import pl.estrix.app.lib.allegro.oauth.OAuthConfig;

import java.util.Map;
import java.util.stream.Collectors;

public class AuthSignature {

    private static final String UTF_8 = "UTF-8";

    public static String getAsQueryString(OAuthConfig config, String endpoint, HttpMethod httpMethod, Map<String, String> params) {
        if (config == null || endpoint == null || httpMethod == null) {
            return "";
        }
//        Map<String, String> oauthParameters = getAsMap(config, endpoint, httpMethod, params);
//        String encodedSignature = oauthParameters.get(OAuthHeader.OAUTH_SIGNATURE.getValue())
//                .replace(SpecialSymbol.PLUS.getPlain(), SpecialSymbol.PLUS.getEncoded());
//        oauthParameters.put(OAuthHeader.OAUTH_SIGNATURE.getValue(), encodedSignature);
//        return mapToString(oauthParameters, SpecialSymbol.EQUAL.getPlain(), SpecialSymbol.AMP.getPlain());
        return null;
    }

    private static String mapToString(Map<String, String> paramsMap, String keyValueDelimiter, String paramsDelimiter) {
        return paramsMap.entrySet().stream()
                .map(entry -> entry.getKey() + keyValueDelimiter + entry.getValue())
                .collect(Collectors.joining(paramsDelimiter));
    }
}
