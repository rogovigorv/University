package com.foxminded.university.mapper;

import com.foxminded.university.models.Teacher;
import com.foxminded.university.models.TeacherDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(TeacherDto dto, @MappingTarget Teacher entity);
}
