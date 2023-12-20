package com.example.assignment.controller;

import com.example.assignment.dto.ItemDto;
import com.example.assignment.exception.ItemAlreadyExistException;
import com.example.assignment.exception.ItemNotFoundException;
import com.example.assignment.model.Item;
import com.example.assignment.service.ItemService;
import com.example.assignment.utils.ItemStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto itemDto) {
        if (itemService.isItemExists(itemDto.getItemId())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new ItemAlreadyExistException("Item already exists");
        }
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItemById(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemDto itemDto) {
        if (!itemService.isItemExists(itemId)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ItemNotFoundException("Item not found");
        }
        itemDto.setItemId(itemId);
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(
            @PathVariable Long itemId) {
        if (!itemService.isItemExists(itemId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteAllItems() {
        itemService.deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(
            @PathVariable Long itemId) {
        if (!itemService.isItemExists(itemId)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ItemNotFoundException("Item not found");
        }
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @GetMapping(params = {"itemStatus","itemEnteredByUser"})
    public ResponseEntity<List<Item>> getItemByStatusAndEnteredBy(
            @RequestParam(name = "itemStatus" ) ItemStatus itemStatus,
            @RequestParam(name = "itemEnteredByUser") String enteredBy) {
        return new ResponseEntity<>(itemService.getItemByStatusAndEnteredBy(itemStatus, enteredBy), HttpStatus.OK);
    }

    @GetMapping(params = {"pageSize","page","sortBy"})
    public ResponseEntity<Page<Item>> getItemPaginatedAndSorted(
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) @Min(value = 10) @Max(value = 50) int pageSize,
            @RequestParam(name = "page" , defaultValue = "0", required = false) @Positive int pageNumber,
            @RequestParam(name = "sortBy", defaultValue = "itemEnteredDate", required = false ) String sortByField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortByField));
        return new ResponseEntity<>(itemService.getItemPaginatedAndSorted(pageRequest), HttpStatus.OK);
    }

}
