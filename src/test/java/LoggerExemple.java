import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Temporary here for test &example purpose remove in next commit
public class LoggerExemple {
	@Test
	public void exemple() {
		Logger logger = LoggerFactory.getLogger(LoggerExemple.class);
		String hw = "Hello World!";
		logger.debug(hw);
		logger.info(hw);
		logger.error(hw);
		logger.warn(hw);
		logger.trace(hw);
	}
}
