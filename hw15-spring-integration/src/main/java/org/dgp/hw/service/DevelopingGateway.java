package org.dgp.hw.service;

import org.dgp.hw.domain.PhotoBundle;
import org.dgp.hw.domain.RawFilm;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface DevelopingGateway {

    @Gateway(requestChannel = "developingChannel", replyChannel = "photoChannel")
    PhotoBundle process(RawFilm films);
}
