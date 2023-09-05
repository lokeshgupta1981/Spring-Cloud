package com.howtodoinjava.feign.config;

import static java.util.Objects.isNull;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

public class OAuthClientCredentialsFeignManager {

	private final OAuth2AuthorizedClientManager manager;
	private final ClientRegistration clientRegistration;

	public OAuthClientCredentialsFeignManager(OAuth2AuthorizedClientManager manager,
			ClientRegistration clientRegistration) {
		this.manager = manager;
		this.clientRegistration = clientRegistration;
	}


	public String getAccessToken() {
		try {
			OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
					.withClientRegistrationId(clientRegistration.getRegistrationId()).build();
			OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
			if (isNull(client)) {
				throw new IllegalStateException("client credentials flow on " + clientRegistration.getRegistrationId()
						+ " failed, client is null");
			}
			return client.getAccessToken().getTokenValue();
		} catch (Exception exp) {
		}
		return null;
	}
}
