package api.deleteMethods;

import api.ApiBase;
import api.utils.AuthTokenService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestRegistrationDelete extends ApiBase {
    public TestRegistrationDelete(String testRunId, String testId) {
        super(null, null);
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
       // String testRunId= AuthTokenService.getTestRunId();
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
       // replaceUrlPlaceholder("testId", AuthTokenService.getTestId(testRunId));
    }
}
