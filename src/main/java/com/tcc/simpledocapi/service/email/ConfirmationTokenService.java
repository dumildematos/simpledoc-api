package com.tcc.simpledocapi.service.email;

import com.tcc.simpledocapi.entity.ConfirmationToken;

public interface ConfirmationTokenService {

    void create(ConfirmationToken confirmationToken);

}
