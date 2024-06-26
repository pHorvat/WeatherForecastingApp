package com.phorvat.weatherforecastingapp;

import com.phorvat.weatherforecastingapp.configuration.AuditorConfig;
import com.phorvat.weatherforecastingapp.models.user.Role;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.user.UserController;
import com.phorvat.weatherforecastingapp.models.user.UserService;
import com.phorvat.weatherforecastingapp.models.user.request.UserUpdateRequest;
import com.phorvat.weatherforecastingapp.models.weather.Weather;
import com.phorvat.weatherforecastingapp.models.weather.WeatherController;
import com.phorvat.weatherforecastingapp.models.weather.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WeatherForecastingAppApplicationTests {

    @Mock
    private WeatherService weatherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WeatherController weatherController;

    @InjectMocks
    private UserController userController;

    // WeatherController Tests
    @Test
    void testGetAllWeathers() {
        Weather weather = new Weather();
        when(weatherService.getAllWeathers()).thenReturn(Collections.singletonList(weather));

        List<Weather> weathers = weatherController.getAllDWeathers();

        assertNotNull(weathers);
        assertEquals(1, weathers.size());
    }

    @Test
    void testGetWeatherByLocationId() {
        int locationId = 1;
        Weather weather = new Weather();
        when(weatherService.getWeatherByLocationId(locationId)).thenReturn(Collections.singletonList(weather));

        List<Weather> weathers = weatherController.getWeatherByLocationId(locationId);

        assertNotNull(weathers);
        assertEquals(1, weathers.size());
    }

    @Test
    void testGetLastWeatherByLocationId() {
        int locationId = 1;
        Weather weather = new Weather();
        when(weatherService.getLatestWeatherByLocationId(locationId)).thenReturn(Optional.of(weather));

        Optional<Weather> result = weatherController.getLastWeatherByLocationId(locationId);

        assertTrue(result.isPresent());
    }

    // UserController Tests
    @Test
    void testGetAllUsers() {
        User user = new User();
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        List<User> users = userController.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    void testGetUserById() {
        int userId = 1;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);

        User result = userController.getUserById(userId);

        assertNotNull(result);
    }

    @Test
    void testGetCurrentUserId() {
        String token = "test-token";
        int userId = 1;
        when(userService.getUserIdFromToken(token)).thenReturn(userId);

        Integer result = userController.getCurrentUserId(token);

        assertNotNull(result);
        assertEquals(userId, result);
    }

    @Test
    void testGetCurrentUser() {
        String token = "test-token";
        User user = new User();
        when(userService.getUserDataFromToken(token)).thenReturn(user);

        User result = userController.getCurrentUser(token);

        assertNotNull(result);
    }

    @Test
    void testGetCurrentUserRoles() {
        String token = "test-token";
        List<String> roles = Collections.singletonList("ROLE_USER");
        when(userService.getUserRolesFromToken(token)).thenReturn(roles);

        List<String> result = userController.getCurrentUserRoles(token);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ROLE_USER", result.getFirst());
    }

    @Test
    void testUpdateUserLocation() {
        String token = "test-token";
        int locationId = 1;
        int userId = 1;
        User user = new User();
        when(userService.getUserIdFromToken(token)).thenReturn(userId);
        Mockito.doNothing().when(userService).updateUserLocation(userId, locationId);
        when(userService.getUserDataFromToken(token)).thenReturn(user);

        User result = userController.updateUserLocation(token, locationId);

        assertNotNull(result);
    }

    @Test
    void testUpdateCurrentUserAdmin() {
        String token = "test-token";
        int userId = 1;
        UserUpdateRequest userRequest = new UserUpdateRequest();
        User user = new User();
        user.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        User updatedUser = new User();

        when(userService.getUserDataFromToken(token)).thenReturn(user);
        when(userService.updateCurrentUser(userId, userRequest)).thenReturn(updatedUser);

        ResponseEntity<?> response = userController.updateCurrentUser(token, userId, userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void testUpdateCurrentUserNonAdmin() {
        String token = "test-token";
        int userId = 1;
        UserUpdateRequest userRequest = new UserUpdateRequest();
        User user = new User();
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        when(userService.getUserDataFromToken(token)).thenReturn(user);

        ResponseEntity<?> response = userController.updateCurrentUser(token, userId, userRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("You do not have permission to update this user", response.getBody());
    }

    @Test
    void testDeleteUser() {
        int userId = 1;
        Mockito.doNothing().when(userService).deleteUser(userId);

        userController.deleteUser(userId);

        Mockito.verify(userService, Mockito.times(1)).deleteUser(userId);
    }


}
