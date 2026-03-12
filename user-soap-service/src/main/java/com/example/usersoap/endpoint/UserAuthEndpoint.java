package com.example.usersoap.endpoint;

import com.example.users.LoginUserRequest;
import com.example.users.LoginUserResponse;
import com.example.users.RegisterUserRequest;
import com.example.users.RegisterUserResponse;
import com.example.users.ValidateTokenRequest;
import com.example.users.ValidateTokenResponse;
import com.example.usersoap.model.AuthUser;
import com.example.usersoap.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.Optional;
import java.util.UUID;

@Endpoint
@RequiredArgsConstructor
public class UserAuthEndpoint {

    private static final String NAMESPACE = "http://example.com/users";
    private final AuthUserRepository repo;

    @PayloadRoot(namespace = NAMESPACE, localPart = "RegisterUserRequest")
    @ResponsePayload
    public RegisterUserResponse register(@RequestPayload RegisterUserRequest req) {
        RegisterUserResponse res = new RegisterUserResponse();

        if (repo.findByUsername(req.getUsername()).isPresent()) {
            res.setSuccess(false);
            res.setMessage("Username аль хэдийн бүртгэлтэй байна");
            return res;
        }

        AuthUser user = new AuthUser();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        repo.save(user);

        res.setSuccess(true);
        res.setMessage("Бүртгэл амжилттай");
        return res;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "LoginUserRequest")
    @ResponsePayload
    public LoginUserResponse login(@RequestPayload LoginUserRequest req) {
        LoginUserResponse res = new LoginUserResponse();
        Optional<AuthUser> opt = repo.findByUsername(req.getUsername());

        if (opt.isEmpty() || !opt.get().getPassword().equals(req.getPassword())) {
            res.setSuccess(false);
            res.setToken("");
            res.setMessage("Нэр эсвэл нууц үг буруу");
            return res;
        }

        String token = UUID.randomUUID().toString();
        AuthUser user = opt.get();
        user.setToken(token);
        repo.save(user);

        res.setSuccess(true);
        res.setToken(token);
        res.setMessage("Нэвтрэлт амжилттай");
        return res;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "ValidateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest req) {
        ValidateTokenResponse res = new ValidateTokenResponse();
        Optional<AuthUser> opt = repo.findByToken(req.getToken());

        if (opt.isPresent()) {
            res.setValid(true);
            res.setUsername(opt.get().getUsername());
        } else {
            res.setValid(false);
            res.setUsername("");
        }
        return res;
    }
}