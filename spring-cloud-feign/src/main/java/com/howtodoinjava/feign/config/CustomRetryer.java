package com.howtodoinjava.feign.config;

import feign.RetryableException;
import feign.Retryer;

public class CustomRetryer implements Retryer {

	private int maxRetries;
	private long retryInterval;
	private int attempt = 1;

	public CustomRetryer(int maxRetries, Long retryInterval) {
		this.maxRetries = maxRetries;
		this.retryInterval = retryInterval;
	}

	@Override
	public void continueOrPropagate(RetryableException e) {
		if (attempt++ >= maxRetries) {
			throw e;
		}
		try {
			Thread.sleep(retryInterval);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public Retryer clone() {
		return new CustomRetryer(1, 1000L);
	}
}
