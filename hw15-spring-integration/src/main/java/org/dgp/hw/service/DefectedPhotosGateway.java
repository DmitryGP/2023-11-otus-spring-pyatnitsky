package org.dgp.hw.service;

import org.dgp.hw.domain.PhotoBundle;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface DefectedPhotosGateway {

    @Gateway(replyChannel = "defectedPhotosChannel")
    PhotoBundle getDefectedPhotos();
}
