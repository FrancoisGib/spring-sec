package app.users.roles;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
	PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

	PermissionDTO toDto(Permission permission);

	List<PermissionDTO> toDto(List<Permission> permissions);
}

