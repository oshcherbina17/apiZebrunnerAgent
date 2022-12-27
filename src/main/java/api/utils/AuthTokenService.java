package api.utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import api.enums.HTTPStatusCode;
import api.enums.JsonValues;
import api.postMethods.Authentication;
import api.postMethods.TestExecutionStart;
import api.postMethods.TestRunStart;
import com.qaprosoft.carina.core.foundation.crypto.CryptoTool;

public class AuthTokenService {
    static ApiExecution apiExecution = new ApiExecution();

    static TestRunStart testRunStart = new TestRunStart();

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        AuthTokenService.authToken = authToken;
        //        this.authToken = JsonPath.from(response).get(JsonValues.AUTH_TOKEN.getValue()).toString();
    }

    public static void refreshAuthToken() throws IOException {
        Authentication authentication = new Authentication();
        apiExecution.expectStatus(authentication, HTTPStatusCode.OK);
        authToken = JsonService.readAuthToken(apiExecution.callApiMethod(authentication));
        authentication.validateResponse();
        Properties properties = new Properties();
        CryptoTool cryptoTool = new CryptoTool();
        String str = cryptoTool.encrypt(authToken);
        properties.put(JsonValues.AUTH_TOKEN.getValue(), "{crypt:" + str + "}");
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/_testdata.properties");
        properties.store(outputStream, null);
    }

    public static String getTestRunId() {///////
        apiExecution.expectStatus(testRunStart, HTTPStatusCode.OK);////////
        return JsonService.readId(apiExecution.callApiMethod(testRunStart));
    }

    public static String getTestId(String testRunId) {////////
        TestExecutionStart testExecutionStart = new TestExecutionStart(testRunId);
        apiExecution.expectStatus(testExecutionStart, HTTPStatusCode.OK);
        return JsonService.readId(apiExecution.callApiMethod(testExecutionStart));
    }
}
