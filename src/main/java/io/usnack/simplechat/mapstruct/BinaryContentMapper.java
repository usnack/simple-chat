package io.usnack.simplechat.mapstruct;

import io.usnack.simplechat.dto.data.BinaryContentDto;
import io.usnack.simplechat.dto.data.BinaryContentInputStreamDto;
import io.usnack.simplechat.entity.BinaryContent;
import io.usnack.simplechat.util.binary.BinaryStorage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BinaryContentMapper {
    @Autowired
    protected BinaryStorage binaryStorage;

    @Mapping(target = "path", expression = "java(binaryStorage.resolvePath(binaryContent.getId()))")
    public abstract BinaryContentDto toDto(BinaryContent binaryContent);
    @Mapping(target = "path", expression = "java(binaryStorage.resolvePath(binaryContent.getId()))")
    @Mapping(target = "inputStream", expression = "java(binaryStorage.loadBinary(binaryStorage.resolvePath(binaryContent.getId())))")
    public abstract BinaryContentInputStreamDto toInputStreamDto(BinaryContent binaryContent);
}
