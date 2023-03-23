package com.s3procore.web;

import com.s3procore.core.security.AuthenticationHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class PingController {

    @GetMapping("/api/ping/me")
    Object pingMe() {
//        AuthenticationHelper.getAuthenticationDetails();

//        var tokenDetails = ((BearerTokenAuthentication) auth).getTokenAttributes();
//        var pingEcho = "Hello, " + tokenDetails.get(StandardClaimNames.PREFERRED_USERNAME) + " Ping successful.";
//        return Map.of("ping_echo", pingEcho);
        return "Hi";
    }

    @GetMapping("/user/info")
    public String getUserInfo(Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) auth.getPrincipal();
            String sessionId = jwt.getClaimAsString("sessionid");
            int userId = Integer.parseInt(jwt.getClaimAsString("userid"));
        } else {
            // log an error and throw an exception?
        }
//    }
        return "Hi";
    }
}
