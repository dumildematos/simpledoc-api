package com.tcc.simpledocapi.service.email;

import com.tcc.simpledocapi.entity.ConfirmationToken;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;

public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void create(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}
