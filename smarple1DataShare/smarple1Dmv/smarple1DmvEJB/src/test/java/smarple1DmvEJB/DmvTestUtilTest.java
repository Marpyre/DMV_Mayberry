package smarple1DmvEJB;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DmvTestUtilTest {
	private static final Logger logger = LoggerFactory
			.getLogger(DmvTestUtilTest.class);

	IDmvTestUtilRemote testUtil;

	@Before
	public void setUp() {
		testUtil = new DmvTestUtilEJB();
	}

	@Test
	public void testPing() {
		logger.info("*** testPing ***");
		testUtil.ping();
	}
}