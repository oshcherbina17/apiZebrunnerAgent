package api;

import java.util.Properties;

import api.utils.AuthTokenService;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;

public abstract class ApiBase extends AbstractApiMethodV2 {

    public ApiBase(String rq, String rs, String prop) {
        super(rq, rs, prop);
    }

    public ApiBase(String rq, String rs, Properties properties) {
        super(rq, rs, properties);
        setAuth();
    }

    public ApiBase(String rq, String rs) {
        super(rq, rs);
        setAuth();
    }

    private void setAuth() {
        AuthTokenService.refreshAuthToken();
        setHeaders("Authorization=Bearer " + AuthTokenService.getAuthToken());
    }
}
