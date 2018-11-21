package io.spronq.reactiveresourceserver;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SimpleController {

    @GetMapping("/")
    @PreAuthorize("hasAuthority('read:employee')")
    public Mono<String> index(@AuthenticationPrincipal Jwt jwt) {

        return Mono.just("Hello " + jwt.getSubject());
    }

    @GetMapping("/message")
    @PreAuthorize("hasAuthority('admin:employee')")
    public Mono<String> message() {
        return Mono.just("Very secret message");
    }
}
