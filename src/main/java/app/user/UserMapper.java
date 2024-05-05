package app.user;

import app.user.models.User;
import app.user.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> users);
}
