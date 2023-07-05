package com.paritosh.pranjal.service;

import com.paritosh.pranjal.entity.User;
import com.paritosh.pranjal.repository.UserRepository;
import com.paritosh.pranjal.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserDepartmentApplicationTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetUser() {
		// Mocking the repository
		List<User> userList = new ArrayList<>();
		userList.add(new User(1L, "John"));
		userList.add(new User(2L, "Jane"));
		when(userRepository.findAll()).thenReturn(userList);

		// Calling the service method
		List<User> result = userService.getUser();

		// Verifying the result
		assertEquals(2, result.size());
		assertEquals("John", result.get(0).getName());
		assertEquals("Jane", result.get(1).getName());
	}

	@Test
	void testSaveUser() {
		// Mocking the repository
		User user = new User(1L, "John");
		when(userRepository.save(user)).thenReturn(user);

		// Calling the service method
		User result = userService.saveUser(user);

		// Verifying the result
		assertEquals(user, result);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testGetSingleUser() {
		// Mocking the repository
		Long userId = 1L;
		User user = new User(userId, "John");
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Calling the service method
		User result = userService.getSingleUser(userId);

		// Verifying the result
		assertEquals(user, result);
	}

	@Test
	void testGetSingleUserNotFound() {
		// Mocking the repository
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Calling the service method and expecting an exception
		assertThrows(RuntimeException.class, () -> userService.getSingleUser(userId));
	}

	@Test
	void testDeleteUser() {
		// Calling the service method
		Long userId = 1L;
		userService.deleteUser(userId);

		// Verifying the deletion
		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	void testUpdateUser() {
		// Mocking the repository
		User user = new User(1L, "John");
		when(userRepository.save(user)).thenReturn(user);

		// Calling the service method
		User result = userService.updateUser(user);

		// Verifying the result
		assertEquals(user, result);
		verify(userRepository, times(1)).save(user);
	}
}
