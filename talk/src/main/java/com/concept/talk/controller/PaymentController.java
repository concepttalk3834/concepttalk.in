package com.concept.talk.controller;

import com.concept.talk.dto.PaymentDTO;
import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import com.concept.talk.repository.PaymentRepository;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.PaymentService;
import com.concept.talk.service.AuthenticationService;
import com.concept.talk.util.EmailService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.mail.MessagingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class PaymentController {
	@Value("${key_id}")
	private String keyId;
	@Value("${key_secret}")
	private String secret;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private AuthenticationService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> data, Principal principle) throws RazorpayException {
		int amount = Integer.parseInt(data.get("amount").toString());
		User user = userRepository.findByEmail(principle.getName()).orElseThrow(() -> new RuntimeException("User not found"));
		
		RazorpayClient razorpayClient = new RazorpayClient(keyId,secret);
		
		System.out.println(data);
		JSONObject ob = new JSONObject();
		ob.put("amount",amount*100);
		ob.put("currency","INR");
		ob.put("receipt", "txn_" + System.currentTimeMillis());
		ob.put("payment_capture", true);
		Order order = razorpayClient.orders.create(ob);
		System.out.println(order);
		
		Payment payment = new Payment();
		payment.setAmount(order.get("amount")+"");
		payment.setPaymentId(order.get("id"));
		payment.setTransactionId(null);
		payment.setStatus("created");
		payment.setUser(user);
		paymentRepository.save(payment);
		
		return new ResponseEntity<>(order.toString(), HttpStatus.OK);
	}
	
	@PostMapping("/update_payment")
	public ResponseEntity<?> updatePayment(@RequestBody Map<String,Object> data,Principal principal){
		System.out.println(data);
		
		String order_id = data.get("order_id").toString();
		String paymentId = data.get("payment_id").toString();
        String status = data.get("status").toString();
		
		User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        
        Payment payment = paymentRepository.findByPaymentId(order_id);
        payment.setStatus(status);
		payment.setTransactionId(paymentId);
        paymentRepository.save(payment);
		
		try {
			if ("success".equalsIgnoreCase(status)) {
				emailService.sendPaymentInfo(user.getEmail(), payment, "Payment Successful", "Dear user,\n\nYour payment with ID " + paymentId +
						" has been successfully processed.\n\nThank you.\n\n In case you did not receive any mail please check spam or contact 7642010280");
			} else if ("failure".equalsIgnoreCase(status)) {
				emailService.sendPaymentInfo(user.getEmail(), payment, "Payment Failed", "Dear user,\n\nUnfortunately, your payment with ID " + paymentId +
						" has failed. Please try again.\n\nThank you." +
						"In case you are facing any issue, feel free to contact 7642010280");
				
			}
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment updated but failed to send email.");
		}
		
        return ResponseEntity.ok(payment);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable String email){
		Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        List<Payment> payments = paymentService.getPaymentsByUser(userOptional.get());
        return ResponseEntity.ok(payments);
	}
}
