package ru.practicum.shareit.common.constraint.period;

import java.time.LocalDateTime;

public interface Period {
    public LocalDateTime getStart();

    public LocalDateTime getEnd();
}
