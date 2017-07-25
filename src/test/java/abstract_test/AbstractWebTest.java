package abstract_test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Rostislav Dudka
 */
@RunWith(SpringRunner.class)
@Profile("no-security")
abstract class AbstractWebTest {
    protected static final String RESPONSE_HTML_CONTENT_TYPE = "text/html;charset=UTF-8";

    @Autowired
    protected MockMvc mockMvc;
}
