package spring_project.mapper;

import org.mapstruct.Mapper;
import spring_project.dto.CoachRequets;
import spring_project.entity.Coach;

@Mapper(componentModel = "spring")
public interface CoachMapper {
    CoachRequets toDto(Coach coach);
    Coach toEntity(CoachRequets coachRequets);
}
