package api.postMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestRunStart extends ApiBase {
    public TestRunStart() {
        super("api/test_run/post/rq.json", "api/test_run/post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        addUrlParameter("projectKey", "CR1");
    }
}
