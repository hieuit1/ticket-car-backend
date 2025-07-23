package spring_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import spring_project.dto.RickShawRequets;
import spring_project.entity.Rickshaw;

@Mapper(componentModel = "spring")
public interface RickShawMapper {
    @Mappings({
            @Mapping(source = "rickshawId", target = "rickShawId"),
            @Mapping(source = "fullName", target = "rickShawfullName"),
            @Mapping(source = "phoneNumber", target = "rickShawphoneNumber"),
            @Mapping(source = "yearOfBirth", target = "rickShawyearOfBirth"),
            @Mapping(source = "descriptions", target = "rickShawdescriptions"),
            @Mapping(source = "gender", target = "rickShawgender")
    })
    RickShawRequets toDTO(Rickshaw rickshaw);

    @Mappings({
            @Mapping(source = "rickShawId", target = "rickshawId"),
            @Mapping(source = "rickShawfullName", target = "fullName"),
            @Mapping(source = "rickShawphoneNumber", target = "phoneNumber"),
            @Mapping(source = "rickShawyearOfBirth", target = "yearOfBirth"),
            @Mapping(source = "rickShawdescriptions", target = "descriptions"),
            @Mapping(source = "rickShawgender", target = "gender")
    })
    Rickshaw toEntity(RickShawRequets rickshawRequets);
}
