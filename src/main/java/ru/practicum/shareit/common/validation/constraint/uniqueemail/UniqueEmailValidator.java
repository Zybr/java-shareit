package ru.practicum.shareit.common.validation.constraint.uniqueemail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDto> {
    private final UserRepository userRepository;


    @Override
    public boolean isValid(UserDto change, ConstraintValidatorContext constraintValidatorContext) {
        String email = change.getEmail();

        if (email == null) {
            return true;
        }

        Optional<User> existedUser = userRepository.findOneByEmail(email);

        return existedUser.isEmpty() // Email is unique
                || existedUser.get().getId().equals(change.getId()); // The user is the same
    }
}
