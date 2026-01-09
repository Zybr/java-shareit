package ru.practicum.shareit.gateway.features.request.dto;
import ru.practicum.shareit.features.request.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestDtoTest {

    @Autowired
    private JacksonTester<ItemRequestCreationDto> json;

    @Test
    void testItemRequestCreationDto() throws Exception {
        ItemRequestCreationDto dto = new ItemRequestCreationDto("Description");

        JsonContent<ItemRequestCreationDto> result = json.write(dto);

        assertThat(result).hasJsonPathStringValue("$.description", "Description");
    }
}
