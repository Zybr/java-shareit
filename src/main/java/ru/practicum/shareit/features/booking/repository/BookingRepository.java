package ru.practicum.shareit.features.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
            select
                b
            from Booking b
                join b.item i
                join i.owner itemOwner
                join b.booker booker
            where (
                b.id = :bookingId
                and (
                    itemOwner.id = :userId
                    or booker.id = :userId
                )
            )
            """)
    Optional<Booking> findByIdAndUserId(
            @Param("bookingId") Long bookingId,
            @Param("userId") Long userId
    );


    // >>> By booker

    List<Booking> findAllByBookerId(Long bookerId);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndBefore(Long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartAfter(Long bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status);

    List<Booking> findAllByBookerIdAndStatusAndEndBefore(Long bookerId, BookingStatus status, LocalDateTime end);

    // <<< By booker

    // >>> By owner
    List<Booking> findAllByItemOwnerId(Long bookerId);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndEndBefore(Long bookerId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartAfter(Long bookerId, LocalDateTime start);

    List<Booking> findAllByItemOwnerIdAndStatus(Long bookerId, BookingStatus status);

    // <<< By owner
}
