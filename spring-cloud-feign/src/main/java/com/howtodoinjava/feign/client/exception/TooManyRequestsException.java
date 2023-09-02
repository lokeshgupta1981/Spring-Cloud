package com.howtodoinjava.feign.client.exception;

import feign.Response;

public class TooManyRequestsException extends Exception {

	public TooManyRequestsException(Response response) {
	}

}
