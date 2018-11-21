package io.spronq.reactiveresourceserver;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class AzureADAuthoritiesConverter extends JwtAuthenticationConverter {

    public AzureADAuthoritiesConverter() {
        super();
    }

    @Override
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        List<String> rolesClaim = jwt.getClaimAsStringList("roles");

        rolesClaim.forEach(r -> authorities.addAll(mapRoleToAuthorities(r)));

        return authorities;

    }



    private List<SimpleGrantedAuthority> mapRoleToAuthorities(String role) {

        // Add a case statement for each Azure AD role
        List<SimpleGrantedAuthority> auths = new ArrayList<>();

        switch (role) {
            case "WGP_MT_Inzender":
                //Set authorities based on role
                Stream.of("create:employee", "update:employee", "update:employer")
                        .forEach(a -> auths.add(new SimpleGrantedAuthority(a)));
                break;
            case "WGP_MT_Lezer":
                //Set authorities based on role
                Stream.of("read:employee", "read:employer", "read:PMT")
                        .forEach(a -> auths.add(new SimpleGrantedAuthority(a)));
                break;
            case "WGP_Beheerder":
                Stream.of(
                        "admin:employee",
                        "admin:employer",
                        "read:rejections",
                        "update:rejections"
                )
                        .forEach(a -> auths.add(new SimpleGrantedAuthority(a)));
                break;
            default:
                return Collections.emptyList();
        }

        return auths;
    }
}
