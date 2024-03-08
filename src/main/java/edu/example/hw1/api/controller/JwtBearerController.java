package edu.example.hw1.api.controller;

import edu.example.hw1.api.dto.jwt.JwtEmailResponseDto;
import edu.example.hw1.api.dto.jwt.JwtRoleResponseDto;
import edu.example.hw1.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jwt")
public class JwtBearerController {
    private final JwtService jwtService;

    @GetMapping("/email")
    public JwtEmailResponseDto getEmailByJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        var jwtToken = bearerToken.substring("Bearer ".length());
        var email = jwtService.getEmailFromToken(jwtToken);

        return new JwtEmailResponseDto(email);
    }

    @GetMapping("/role")
    public JwtRoleResponseDto getRoleByJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        var jwtToken = bearerToken.substring("Bearer ".length());
        var role = jwtService.getRoleFromToken(jwtToken);

        return new JwtRoleResponseDto(role);
    }

}

