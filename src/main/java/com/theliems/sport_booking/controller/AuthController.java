package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.model.Account;
import com.theliems.sport_booking.model.Club;
import com.theliems.sport_booking.repository.ClubRepository;
import com.theliems.sport_booking.repository.AccountClubRepository;
import com.theliems.sport_booking.service.AuthService;
import com.theliems.sport_booking.service.JwtService;
import com.theliems.sport_booking.service.AccountService;
import com.theliems.sport_booking.model.AccountClub;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AccountService accountService;
    private final AccountClubRepository accountClubRepo;
    private final ClubRepository clubRepo;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            AccountService accountService,
            AccountClubRepository accountClubRepo,
            ClubRepository clubRepo
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.accountClubRepo = accountClubRepo;
        this.clubRepo = clubRepo;
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        authService.register(email, password);
        return ResponseEntity.ok("OTP đã được gửi tới email");
    }

    // verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        authService.verifyOtp(email, otp);
        return ResponseEntity.ok("Xác thực OTP thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        Account account = authService.login(email, password);
        String token = jwtService.generateToken(account);

        List<Integer> clubIds = accountClubRepo
                .findByAccountIdAndIsDeletedFalse(account.getAccountId())
                .stream()
                .map(AccountClub::getClubId)
                .toList();

        List<Club> clubEntities = clubRepo.findAllById(clubIds);

        var clubs = clubEntities.stream()
                .map(c -> Map.of(
                        "id", c.getClubId(),
                        "name", c.getClubName()
                ))
                .toList();

        return ResponseEntity.ok(Map.of(
                "token", token,
                "account", Map.of(
                        "id", account.getAccountId(),
                        "email", account.getEmail(),
                        "role", account.getRole()
                ),
                "clubs", clubs
        ));
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");

        Account account = authService.loginWithGoogle(idToken);
        String jwt = jwtService.generateToken(account);

        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "account", Map.of(
                        "id", account.getAccountId(),
                        "email", account.getEmail(),
                        "role", account.getRole()
                )
        ));
    }

    // GET /api/auth/has-password
    @GetMapping("/has-password")
    public ResponseEntity<?> hasPassword(Authentication authentication) {
        String email = authentication.getName();
        boolean has = accountService.hasPassword(email);
        return ResponseEntity.ok(Map.of("hasPassword", has));
    }

    // change password
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        accountService.changePassword(body);
        return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công"));
    }
}
