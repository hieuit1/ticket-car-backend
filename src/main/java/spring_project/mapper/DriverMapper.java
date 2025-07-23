package spring_project.mapper;

import org.mapstruct.Mapper;
import spring_project.dto.CoachRequets;
import spring_project.dto.DriverRequets;
import spring_project.entity.Coach;
import spring_project.entity.Driver;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverRequets toDto(Driver driver);
    Driver toEntity(DriverRequets driverRequets);
}

