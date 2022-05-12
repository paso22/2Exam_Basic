package ge.paso.Exam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class GeneralController {

    @GetMapping("/login")
    public ResponseEntity<String> logIn() {
        return new ResponseEntity<>("You've logged in!", HttpStatus.OK);
    }

}
