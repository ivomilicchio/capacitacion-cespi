package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.CurrentAccountBalanceResponse;
import com.cespi.capacitacion.backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private final UserService userService;

    public CurrentAccountServiceImpl(UserService userService) {
        this.userService = userService;
    }


    public CurrentAccountBalanceResponse getCurrentAccountBalance(String authHeader) {
        User user = userService.getUserFromAuthHeader(authHeader);
        return new CurrentAccountBalanceResponse(user.getCurrentAccount().getBalance());
    }
}
