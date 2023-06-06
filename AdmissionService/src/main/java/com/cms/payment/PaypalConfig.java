package com.cms.payment;


import java.util.Map;


import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


public class PaypalConfig {

	
	private String clientId;
	
	private String clientSecret;
	
	private String mode;

	
	public Map<String, String> paypalSdkConfig() {
		return null;
	}

	
	public OAuthTokenCredential oAuthTokenCredential() {
		return null;
	}

	
	public APIContext apiContext() throws PayPalRESTException {
		return null;
	}

}
