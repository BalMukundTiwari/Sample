package com.cms.payment;


import java.util.Map;


import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;

public class PaypalConfig {

	
	private String clientId;
	
	private String clientSecret;
	
	private String mode;

	
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
	}

	
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}

	
	public APIContext apiContext() throws PayPalRESTException {
        return new APIContext(oAuthTokenCredential().getAccessToken());
	}

}
