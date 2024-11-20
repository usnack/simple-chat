package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.response.UserDetailResponse;
import io.usnack.simplechat.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetailResponse toDetailResponse(User user);
}
