package api.putMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestRunExecutionFinish extends ApiBase {
    public TestRunExecutionFinish(String testRunId) {
        super("api/test_run/put/rq.json", "api/test_run/put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("id", testRunId);
    }
}
