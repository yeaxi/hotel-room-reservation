package abstract_test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Rostislav Dudka
 */

@RunWith(SpringRunner.class)
@DataMongoTest
public abstract class AbstractRepositoryTest {
}
