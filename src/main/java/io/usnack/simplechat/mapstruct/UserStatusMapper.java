package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.UserStatusDto;
import io.usnack.simplechat.entity.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStatusMapper {
    @Mapping(target = "userId", expression = "java(userStatus.getUser().getId())")
    UserStatusDto toDto(UserStatus userStatus);
}
