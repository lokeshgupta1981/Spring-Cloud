package com.howtodoinjava.feign.client.exception;

import feign.Response;

public class BadRequestException extends Exception {

	public BadRequestException(Response response) {
	}

}
