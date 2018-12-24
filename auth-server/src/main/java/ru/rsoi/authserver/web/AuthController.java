package ru.rsoi.authserver.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class AuthController {
    @GetMapping("/login")
    public ResponseEntity<RedirectView> login()
    {
        return ResponseEntity.ok(new RedirectView("http://localhost:8086/login"));
    }
}
