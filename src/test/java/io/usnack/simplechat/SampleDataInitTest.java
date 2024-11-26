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

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Path profileDir = Paths.get("src", "test", "resources", "profiles");
        Map<String, byte[]> profileBinaries = new HashMap<>();
        List.of("woody", "buzz", "jessie").forEach(key -> {
            try (
                    FileInputStream fis = new FileInputStream(profileDir.resolve(key + ".jpeg").toFile())
            ) {
                byte[] bytes = fis.readAllBytes();
                profileBinaries.put(key, bytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        UserCreateRequest woody = new UserCreateRequest("woody", "woody@toystory.com", "1234", null);
        UserCreateRequest buzz = new UserCreateRequest("buzz", "buzz@toystory.com", "1234", null);
        UserCreateRequest jessie = new UserCreateRequest("jessie", "jessie@toystory.com", "1234", null);
        UserDto createdWoody = userService.createUser(woody);
        UserDto createdBuzz = userService.createUser(buzz);
        UserDto createdJessie = userService.createUser(jessie);

        ChannelCreateRequest channelCreateRequest1 = new ChannelCreateRequest(ChannelType.GROUP, "해외축구", "해외축구 토론방");
        ChannelCreateRequest channelCreateRequest2 = new ChannelCreateRequest(ChannelType.DIRECT, "zz", "zzz");
        ChannelDto channel1 = channelService.createChannel(channelCreateRequest1);
        ChannelDto channel2 = channelService.createChannel(channelCreateRequest2);
    }
}
