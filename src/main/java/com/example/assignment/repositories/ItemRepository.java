package com.example.assignment.repositories;
import com.example.assignment.model.Item;
import com.example.assignment.utils.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemStatusAndItemEnteredByUser(ItemStatus itemStatus, String itemEnteredBy);

    Page<Item> findAll(Pageable pageable);
}
