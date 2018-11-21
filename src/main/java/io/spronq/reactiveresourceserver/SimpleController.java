package io.spronq.reactiveresourceserver;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {

        return "Hello " + jwt.getSubject();
    }

    @GetMapping("/message")
    public String message() {
        return "Very secret message";
    }
}
