package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.Account;
import com.theliems.sport_booking.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.accountRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
    }

    public Account create(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        if (account.getPassword() != null && !account.getPassword().isBlank()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        } else {
            account.setPassword(null);
        }

        return accountRepository.save(account);
    }

    public Account update(Integer id, Account account) {
        Account existing = getById(id);

        if (account.getEmail() != null) existing.setEmail(account.getEmail());
        if (account.getRole() != null) existing.setRole(account.getRole());

        if (account.getPassword() != null && !account.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        return accountRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tài khoản");
        }
        accountRepository.deleteById(id);
    }

    public void changePassword(Map<String, String> body) {

        String oldPassword = body.getOrDefault("oldPassword", "").trim();
        String newPassword = body.getOrDefault("newPassword", "").trim();
        String confirmPassword = body.getOrDefault("confirmPassword", "").trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập newPassword và confirmPassword");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới và xác nhận mật khẩu không khớp");
        }

        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới tối thiểu 6 ký tự");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || auth.getName().isBlank()) {
            throw new IllegalArgumentException("Chưa đăng nhập");
        }

        String email = auth.getName();

        Account acc = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        boolean hasLocalPassword = acc.getPassword() != null && !acc.getPassword().isBlank();

        if (hasLocalPassword) {
            if (oldPassword.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập oldPassword");
            }
            if (!passwordEncoder.matches(oldPassword, acc.getPassword())) {
                throw new IllegalArgumentException("Mật khẩu cũ không đúng");
            }
        }

        acc.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(acc);
    }

    public boolean hasPassword(String email) {
        Account acc = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
        return acc.getPassword() != null && !acc.getPassword().isBlank();
    }
}
