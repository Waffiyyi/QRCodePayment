package com.example.demo.service.serviceImpl;

import com.example.demo.api.paystackpaymentinit.InitializeTransactionResponse;
import com.example.demo.api.paystackpaymentverify.VerifyTransactionResponse;
import com.example.demo.dto.InitializeTransactionDTO;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.models.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Value("${PAYSTACK_API_KEY}")
    private String API_KEY;
    @Value("${BASE_URL}")
    private String BASE_URL;
    @Value("${CALLBACK_URL}")
    private String CALLBACK_URL;
private final WebClient webClient = WebClient.builder()
        .baseUrl("https://api.paystack.co")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer sk_test_e6e5c33035a78bc0c1c743c9d26588b346fc085b")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();


    private final TransactionRepository transactionRepository;



    public ResponseEntity<Object> initializeTransaction(InitializeTransactionDTO transactionDTO) {
        String reference = UUID.randomUUID().toString();
        Transaction transactionInfo = Transaction.builder()
                .amount(Double.valueOf(transactionDTO.getAmount()))
                .reference(reference)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        transactionDTO.setCurrency("NGN");
        transactionDTO.setReference(reference);
        transactionDTO.setChannels(List.of("card", "bank_transfer", "ussd"));

        InitializeTransactionResponse initiateTransaction = webClient
                .post()
                .uri("/transaction/initialize")
                .bodyValue(transactionDTO)
                .retrieve()
                .bodyToMono(InitializeTransactionResponse.class)
                .block();

        RedirectView redirectView = new RedirectView();

        if (initiateTransaction != null) {
            transactionRepository.save(transactionInfo);
            redirectView.setUrl(initiateTransaction.getData().getAuthorizationUrl());
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong, please try again", HttpStatus.EXPECTATION_FAILED);
    }


    @Override
    public ResponseEntity<Object> verifyTransaction(String reference) {
        Transaction transactionInfo = transactionRepository.findByReference(reference).orElseThrow(
        );
        log.info("i got hereee");
        log.info(reference);
        VerifyTransactionResponse response = webClient
                .get()
                .uri("/transaction/verify/" + reference)
                .retrieve()
                .bodyToMono(VerifyTransactionResponse.class).block();

        if (response != null) {
            if ("success".equals(response.getData().getStatus())) {
                transactionInfo.setPaymentStatus(PaymentStatus.SUCCESS);
                transactionRepository.save(transactionInfo);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Transaction was unsuccessful", HttpStatus.EXPECTATION_FAILED);
    }



}

