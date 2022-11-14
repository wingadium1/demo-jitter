package com.winga.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRun {
	private static Logger LOGGER = LoggerFactory.getLogger(TestRun.class);

	public static void main(String[] args) {
		LOGGER.info("Running the main method");
		ClientLoad cl = new ClientLoad();
		cl.runExponentialRandomBackoff();
	}
}
