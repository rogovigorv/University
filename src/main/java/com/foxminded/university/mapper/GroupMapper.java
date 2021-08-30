package com.foxminded.university.mapper;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.GroupDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGroupFromDto(GroupDto dto, @MappingTarget Group entity);
}
