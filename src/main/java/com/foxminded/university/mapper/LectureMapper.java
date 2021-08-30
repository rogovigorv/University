package com.foxminded.university.mapper;

import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.LectureDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLectureFromDto(LectureDto dto, @MappingTarget Lecture entity);
}
