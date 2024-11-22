package io.usnack.simplechat;

import io.usnack.simplechat.dto.data.ChannelDto;
import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.dto.request.ChannelCreateRequest;
import io.usnack.simplechat.dto.request.UserCreateRequest;
import io.usnack.simplechat.entity.ChannelType;
import io.usnack.simplechat.service.ChannelService;
import io.usnack.simplechat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SampleDataInitTest {
    @Autowired
    UserService userService;
    @Autowired
    ChannelService channelService;

    @Rollback(value = false)
    @Transactional
    @Test
    void init() {
        UserCreateRequest woody = new UserCreateRequest("woody", "woody@toystory.com", "1234", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpX3WlcgO0PCqjeWjOwKkDFsLCy0QaDg7Arw&s");
        UserCreateRequest buzz = new UserCreateRequest("buzz", "buzz@toystory.com", "1234", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_LmKIb-v_c17dYbP7ifz9O2XkCnL0x_53Cw&s");
        UserCreateRequest jessie = new UserCreateRequest("jessie", "jessie@toystory.com", "1234", "https://lumiere-a.akamaihd.net/v1/images/open-uri20150422-20810-y1ys73_7dd5c0b7.jpeg?region=0,0,450,450");
        UserDto createdWoody = userService.createUser(woody);
        UserDto createdBuzz = userService.createUser(buzz);
        UserDto createdJessie = userService.createUser(jessie);

        ChannelCreateRequest channelCreateRequest1 = new ChannelCreateRequest(ChannelType.GROUP, "해외축구", "해외축구 토론방", createdWoody.id());
        ChannelCreateRequest channelCreateRequest2 = new ChannelCreateRequest(ChannelType.DIRECT, "zz", "zzz", createdWoody.id());
        ChannelDto channel1 = channelService.createChannel(channelCreateRequest1);
        ChannelDto channel2 = channelService.createChannel(channelCreateRequest2);
    }


}
