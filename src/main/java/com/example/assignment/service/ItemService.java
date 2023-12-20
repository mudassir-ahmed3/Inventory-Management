package com.example.assignment.service;

import com.example.assignment.dto.ItemDto;
import com.example.assignment.model.Item;
import com.example.assignment.repositories.ItemRepository;
import com.example.assignment.utils.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    public ItemDto createItem(ItemDto itemDto){
        // if user didn't provide the dates, it will default to current timestamp
        itemDto.setItemEnteredDate(Optional.ofNullable(itemDto.getItemEnteredDate()).orElse(Instant.now()));
        itemDto.setItemLastModifiedDate(Optional.ofNullable(itemDto.getItemLastModifiedDate()).orElse(Instant.now()));
//        if (item.getItemEnteredDate() == null){
//            item.setItemEnteredDate(Instant.now());
//        }
//        if (item.getItemLastModifiedDate() == null){
//            item.setItemLastModifiedDate(Instant.now());
//        }
        return convertToItemDto(itemRepository.save(convertToItemDomain(itemDto)));
    }

    public Boolean isItemExists(Long itemId){
        return itemRepository.findById(itemId).isPresent();
    }

    public void deleteItem(Long itemId){
        itemRepository.deleteById(itemId);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public ItemDto getItemById(Long itemId) {
        return convertToItemDto(itemRepository.findById(itemId).get());
    }

    public List<Item> getItemByStatusAndEnteredBy(ItemStatus itemStatus, String enteredBy) {
        return itemRepository.findByItemStatusAndItemEnteredByUser(itemStatus, enteredBy);
    }

    public Page<Item> getItemPaginatedAndSorted(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public ItemDto convertToItemDto(Item item){
        return modelMapper.map(item, ItemDto.class);
    }

    public Item convertToItemDomain(ItemDto itemDto){
        return modelMapper.map(itemDto, Item.class);
    }
}
