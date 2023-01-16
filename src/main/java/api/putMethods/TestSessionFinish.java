package api.putMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestSessionFinish extends ApiBase {
    public TestSessionFinish(String testRunId, String testSessionId, String testId) {
        super("api/test_session/put/rq.json", "api/test_session/put/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testSessionId", testSessionId);
        addProperty("testIds", testId);
    }
}
