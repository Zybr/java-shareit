package ru.practicum.shareit.features.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.factory.user.UserDtoFactory;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.mapper.UserMapper;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserController controller;
    private final ObjectMapper mapper = new ObjectMapper();
    private final UserDtoFactory userDtoFactory = new UserDtoFactory();
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void createUser() throws Exception {
        UserDto dto = userDtoFactory.make();
        User model = User.builder().build();

        when(userMapper.toModel(any(UserDto.class)))
                .thenReturn(model);
        when(userService.createOne(any(User.class)))
                .thenReturn(model);
        when(userMapper.toDto(any(User.class)))
                .thenReturn(dto);
        mvc.perform(
                        post("/users")
                                .content(
                                        mapper.writeValueAsString(
                                                userDtoFactory.make()
                                        )
                                )
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }
}
