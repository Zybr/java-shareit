package ru.practicum.shareit.features.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.features.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long ownerId);

    @Query("""
            select i from Item i
            join i.owner o on o.id = :ownerId
                where i.available = TRUE
                and (
                    lower(i.name) like lower(concat('%', :search, '%'))
                    or lower(i.description) like lower(concat('%', :search, '%'))
                )
            """)
    List<Item> findAllByOwnerIdAndSearchText(
            @Param("ownerId") Long ownerId,
            @Param("search") String searchText
    );

    @Query("""
                select i from Item i
                where (
                    i.available = TRUE
                    and i.id = :id
                )
            """)
    Optional<Item> findOneAvailableById(
            @Param("id") Long id
    );

    List<Item> findAllByRequestId(Long requestId);
}
