package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.role.exception.RoleInUseException;
import com.tech_symfony.movie_booking.api.role.exception.RoleNotFoundException;
import com.tech_symfony.movie_booking.api.role.permission.Permission;
import com.tech_symfony.movie_booking.api.role.permission.PermissionRepository;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//@SpringBootTest
class DefaultRoleServiceTest extends BaseUnitTest {

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PermissionRepository permissionRepository;

	@InjectMocks
	private DefaultRoleService roleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void findAll() {
		when(roleRepository.findAll()).thenReturn(Collections.emptyList());
		assertTrue(roleService.findAll().isEmpty());
		verify(roleRepository, times(1)).findAll();
	}

	@Test
	void findById_whenRoleExists() {
		Role role = new Role();
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));

		Role result = roleService.findById(1);
		assertEquals(role, result);
		verify(roleRepository, times(1)).findById(1);
	}

	@Test
	void findById_whenRoleDoesNotExist() {
		when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(RoleNotFoundException.class, () -> roleService.findById(1));
		verify(roleRepository, times(1)).findById(1);
	}

	@Test
	void save() {
		Role role = new Role();
		when(roleRepository.save(role)).thenReturn(role);

		Role result = roleService.save(role);
		assertEquals(role, result);
		verify(roleRepository, times(1)).save(role);
	}

	@Test
	void update_whenRoleExists() {
		Role oldRole = new Role();

		Permission permission1 = new Permission();
		permission1.setId(1);
		permission1.setName("permission 1");
		permission1.setDescription("");

		Permission permission2 = new Permission();
		permission2.setId(2);
		permission2.setName("permission 2");
		permission2.setDescription("");

		Permission permission3 = new Permission();
		permission3.setId(3);
		permission3.setName("permission 3");
		permission3.setDescription("");

		oldRole.setId(1);
		oldRole.setPermissions(Set.of(permission1, permission2));

		Role updatedRole = new Role();
		updatedRole.setId(1);
		updatedRole.setPermissions(Set.of(permission1, permission3));

		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(oldRole));
		when(permissionRepository.findById(1)).thenReturn(Optional.of(permission1));
		when(permissionRepository.findById(3)).thenReturn(Optional.of(permission3));

		Role result = roleService.update(1, updatedRole);

		assertEquals(Set.of(permission1, permission3), result.getPermissions());
		verify(roleRepository, times(1)).findById(1);
		verify(permissionRepository, times(1)).findById(1);
		verify(permissionRepository, times(1)).findById(3);
	}


	@Test
	void update_whenRoleDoesNotExist() {
		Role updatedRole = new Role();
		when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(RoleNotFoundException.class, () -> roleService.update(1, updatedRole));
		verify(roleRepository, times(1)).findById(1);
	}

	@Test
	void delete_whenRoleExistsAndNotInUse() {
		Role role = new Role();
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
		when(roleRepository.existsByUsersRole(anyInt())).thenReturn(false);

		Boolean result = roleService.delete(1);

		assertTrue(result);
		verify(roleRepository, times(1)).findById(1);
		verify(roleRepository, times(1)).existsByUsersRole(1);
		verify(roleRepository, times(1)).delete(role);
	}

	@Test
	void delete_whenRoleExistsAndInUse() {
		Role role = new Role();
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
		when(roleRepository.existsByUsersRole(anyInt())).thenReturn(true);

		assertThrows(RoleInUseException.class, () -> roleService.delete(1));
		verify(roleRepository, times(1)).findById(1);
		verify(roleRepository, times(1)).existsByUsersRole(1);
		verify(roleRepository, never()).delete(role);
	}

	@Test
	void delete_whenRoleDoesNotExist() {
		when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(RoleNotFoundException.class, () -> roleService.delete(1));
		verify(roleRepository, times(1)).findById(1);
	}
}
