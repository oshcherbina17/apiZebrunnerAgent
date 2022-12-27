package api.postMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class Authentication extends ApiBase {
    public Authentication() {
        super("api/auth/post/rq.json", "api/auth/post/rs.json", "api/auth/post/access_token.properties");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
    }
}
