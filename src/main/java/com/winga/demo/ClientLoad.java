package com.winga.demo;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

public class ClientLoad {

	private static Logger LOGGER = LoggerFactory.getLogger(ClientLoad.class);
	private static long INITIAL_INTERVAL = 1000;
	private static double MULTIPLIER = 3;
	private static int MAX_RETRIES = 5;
	private static int NUM_CONCURRENT_CLIENTS = 30;

	public void runFixedRate() {
		LOGGER.info("ClientLoad run");
		// Install the retry
		IntervalFunction intervalFn = IntervalFunction.of(INITIAL_INTERVAL);
		RetryConfig retryConfig = RetryConfig.custom().maxAttempts(MAX_RETRIES).intervalFunction(intervalFn).build();
		Retry retry = Retry.of("callApi", retryConfig);
		callAPIandRetry(retry);
	}

	private void callAPIandRetry(Retry retry) {
		APICaller apiCaller = new APICaller();
		Function<String, Integer> callApiFn = Retry.decorateFunction(retry, t -> apiCaller.callApi());
		ExecutorService executors = Executors.newFixedThreadPool(NUM_CONCURRENT_CLIENTS);
		List<Callable<Object>> tasks = Collections.nCopies(NUM_CONCURRENT_CLIENTS, () -> callApiFn.apply("Hello"));
		try {
			LOGGER.info("executors.invokeAll(tasks)");
			executors.invokeAll(tasks);
		} catch (InterruptedException e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		executors.shutdown();
	}

	public void runExponentialBackoff() {
		LOGGER.info("ClientLoad run");
		// Install the retry
		IntervalFunction intervalFn = IntervalFunction.ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
		RetryConfig retryConfig = RetryConfig.custom().maxAttempts(MAX_RETRIES).intervalFunction(intervalFn).build();
		Retry retry = Retry.of("callApi", retryConfig);
		callAPIandRetry(retry);
	}

	public void runExponentialRandomBackoff() {
		LOGGER.info("ClientLoad run");
		IntervalFunction intervalFn = IntervalFunction.ofExponentialRandomBackoff(INITIAL_INTERVAL, MULTIPLIER);
		RetryConfig retryConfig = RetryConfig.custom().maxAttempts(MAX_RETRIES).intervalFunction(intervalFn).build();
		Retry retry = Retry.of("callApi", retryConfig);
		callAPIandRetry(retry);
	}
}