package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserStatusMapper.class, BinaryContentMapper.class})
public interface UserMapper {
    UserDto toDto(User user);
}

