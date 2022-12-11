package api;

import api.enums.JsonValues;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;

import java.util.Properties;

public abstract class ApiBase extends AbstractApiMethodV2 {

    public ApiBase(String rq, String rs, String propPath) {
        super(rq, rs, propPath);
    }

    public ApiBase(String rq, String rs, Properties properties) {
        super(rq, rs, properties);
        setAuthToken();
    }

    public ApiBase(String rq, String rs) {
        super(rq, rs);
        setAuthToken();
    }

    private void setAuthToken() {
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted(JsonValues.AUTH_TOKEN.getValue()));
    }
}
