package api.utils;

import api.enums.HTTPStatusCode;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ApiExecution {
    public void expectStatus(AbstractApiMethodV2 method, HTTPStatusCode status) {
        method.getRequest().expect().statusCode(status.getStatus());
    }

    public String callApiMethod(AbstractApiMethodV2 method) {
        Response response = method.callAPI();
        return response.asString().isEmpty() ? null : response.asString();
    }


}
