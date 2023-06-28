package com.cms.payment;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public class PaypalService {
	
		
	public Payment createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		
//		Payment payment = new Payment();
//        payment.setIntent(intent);
//
//        Payer payer = new Payer();
//        payer.setPaymentMethod(method);
//        payment.setPayer(payer);
//
//        Amount amount = new Amount();
//        amount.setCurrency(currency);
//        amount.setTotal(String.format("%.2f", total));
//        payment.setAmount(amount);
//
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(cancelUrl);
//        redirectUrls.setReturnUrl(successUrl);
//        payment.setRedirectUrls(redirectUrls);
//
//        payment.setDescription(description);
//
//        return payment.create(apiContext());
		return null;
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
//		Payment payment = new Payment();
//        payment.setId(paymentId);
//        PaymentExecution paymentExecution = new PaymentExecution();
//        paymentExecution.setPayerId(payerId);
//        return payment.execute(apiContext(), paymentExecution);
		return null;
	}

}
