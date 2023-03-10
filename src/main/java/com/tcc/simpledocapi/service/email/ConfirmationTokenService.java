package com.tcc.simpledocapi.service.email;

import com.tcc.simpledocapi.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {

    void create(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> verify(String token);

}
