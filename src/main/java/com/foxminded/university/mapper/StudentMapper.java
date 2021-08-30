package com.foxminded.university.mapper;

import com.foxminded.university.models.Student;
import com.foxminded.university.models.StudentDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentDto dto, @MappingTarget Student entity);
}
