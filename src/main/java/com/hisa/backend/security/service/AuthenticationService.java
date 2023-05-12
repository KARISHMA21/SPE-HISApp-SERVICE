package com.hisa.backend.security.service;



import com.hisa.backend.bean.entity.EntityCredentials;
import com.hisa.backend.bean.entity.Role;
import com.hisa.backend.repository.EntityCredentialRepository;
import com.hisa.backend.security.entity.Token;
import com.hisa.backend.security.entity.TokenType;
import com.hisa.backend.security.model.AuthRequest;
import com.hisa.backend.security.repository.TokenRepository;
import com.hisa.backend.security.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EntityCredentialRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//    public AuthenticationResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//        var savedUser = repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        saveUserToken(savedUser, jwtToken);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    public AuthenticationResponse authenticate (AuthRequest request) throws AccessDeniedException {

try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

//        System.out.println(user.getRole());
//        if (!(user.getRole() == Role.DOCTOR)) {
//            throw new AccessDeniedException("Access is denied");
//        } else
//            System.out.println(" GRANTED ------------------");


//        var role = repository.findRoleByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        tokenRepository.deleteTokenByExpiredAndRevoked();
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getDoctorEntity().getDid())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }catch ( AccessDeniedException e) {
        System.out.println("Error while login: " + e.getMessage());
        throw new AccessDeniedException("Access is denied");

    }
    }

    private void saveUserToken(EntityCredentials user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);



    }

    private void revokeAllUserTokens(EntityCredentials user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getDoctorEntity().getDid());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}