package io.usnack.simplechat;

import io.usnack.simplechat.dto.request.PublicChannelCreateRequest;
import io.usnack.simplechat.service.ChannelService;
import io.usnack.simplechat.service.MessageService;
import io.usnack.simplechat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataInitTest {
    @Autowired
    ChannelService channelService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Test
    void init() {

        new PublicChannelCreateRequest("general", "일반 채널입니다.")
    }


}
