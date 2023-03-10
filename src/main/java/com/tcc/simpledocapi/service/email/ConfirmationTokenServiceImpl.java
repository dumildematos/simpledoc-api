package com.tcc.simpledocapi.service.email;

import com.tcc.simpledocapi.entity.ConfirmationToken;
import com.tcc.simpledocapi.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void create(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> verify(String token) {
        return Optional.ofNullable(confirmationTokenRepository.findByConfirmationToken(token));
    }
}
