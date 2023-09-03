package com.howtodoinjava.feign.client;

import org.springframework.http.HttpStatusCode;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		if (HttpStatusCode.valueOf(response.status()).is5xxServerError()) {
			return new RetryableException(response.status(), "5xx exception", null, null, response.request());
		}
		return new Exception("Generic exception");
	}
}