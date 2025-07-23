package spring_project.mapper;

import org.mapstruct.Mapper;
import spring_project.dto.UserDTO;
import spring_project.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
}
