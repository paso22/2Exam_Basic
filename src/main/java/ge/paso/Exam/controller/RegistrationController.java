package ge.paso.Exam.controller;

import ge.paso.Exam.Service.Service;
import ge.paso.Exam.dto.RegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrationController {

    private Service service;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDto registrationDto, BindingResult b) {
        String res = service.registration(registrationDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam int userId ,@RequestParam String verificationCode) {
        service.verifyRegistration(userId,verificationCode);
        return new ResponseEntity<>("Your user is verified!", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }


}
