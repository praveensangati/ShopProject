package com.kgovt.controllers;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Controller
@RequestMapping("/payments")
public class PaymentController {

	/*
	 * @Autowired private RazorpayClient client; private int amount = 500; private
	 * String apiKey; private String secretKey;
	 */
	// 
	@GetMapping("/makePayment")
	public String indexPage(Model model) {
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", 100); // amount in the smallest currency unit
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "order_rcptid_11");
		orderRequest.put("payment_capture", false);
		
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_live_iTSsQ1MOlh6wQm", "ANCdUkmsHT6HOh7rR1E5HSPV");
			Order order = razorpay.Orders.create(orderRequest);
			JSONObject jsonObj = new JSONObject(order.toString());
			model.addAttribute("order_id" , jsonObj.getString("id"));
			model.addAttribute("key" , "rzp_live_iTSsQ1MOlh6wQm");
			model.addAttribute("amount" , 100);
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "payment";
	}
}
