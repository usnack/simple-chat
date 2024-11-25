package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.UserDto;
import io.usnack.simplechat.dto.data.UserStatusDto;
import io.usnack.simplechat.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserStatusMapper.class})
public interface UserMapper {
    @Named("toDto")
    UserDto toDto(User user);
    @Named("toDtoWithoutStatus")
    @Mapping(target = "status", ignore = true)
    UserDto toDtoWithoutStatus(User user);
}
