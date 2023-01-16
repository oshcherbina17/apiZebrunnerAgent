package api.utils;

import api.enums.HTTPStatusCode;
import api.enums.JsonValues;
import api.postMethods.Authentication;

import io.restassured.path.json.JsonPath;

public class AuthTokenService {
    static ApiExecution apiExecution = new ApiExecution();

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = JsonPath.from(authToken).get(JsonValues.AUTH_TOKEN.getValue()).toString();
    }

    public static void refreshAuthToken() {
        Authentication authentication = new Authentication();
        apiExecution.expectStatus(authentication, HTTPStatusCode.OK);
        AuthTokenService authTokenService = new AuthTokenService();
        authTokenService.setAuthToken(apiExecution.callApiMethod(authentication));
    }
}
