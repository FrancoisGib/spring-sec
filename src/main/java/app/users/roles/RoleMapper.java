package app.users.roles;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	RoleDTO toDto(Role role);

	List<RoleDTO> toDto(List<Role> roles);
}
