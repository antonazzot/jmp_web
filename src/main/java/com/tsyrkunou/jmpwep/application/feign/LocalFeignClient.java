//package com.tsyrkunou.jmpwep.application.feign;
//
//import javax.validation.Valid;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.tsyrkunou.jmpwep.application.model.customer.CreateCustomerRequest;
//
//@FeignClient(name = "customer", url = "http://localhost:8089")
//public interface LocalFeignClient {
//    @PostMapping(value = "/jmp")
//    String createCustomer(@Valid @RequestBody CreateCustomerRequest request);
//}