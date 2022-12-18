package api.postMethods;

import api.ApiBase;
import api.utils.Helper;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestSessionStart extends ApiBase {
    public TestSessionStart(String testRunId) {
        super("api/test_session/post/rq.json", "api/test_session/post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        addProperty("testIds", Helper.getHelper().getTestId());
    }
}
