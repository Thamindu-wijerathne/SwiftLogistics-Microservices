package com.cms.demo.service;

import com.cms.demo.dto.ItemDTO;
import com.cms.demo.model.Item;
import com.cms.demo.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Convert Entity -> DTO
    private ItemDTO mapToDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .sku(item.getSku())
                .stock(item.getStock())
                .build();
    }

    // Convert DTO -> Entity
    private Item mapToEntity(ItemDTO itemDTO) {
        return Item.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .price(itemDTO.getPrice())
                .sku(itemDTO.getSku())
                .stock(itemDTO.getStock())
                .build();
    }

    // Create Item
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = mapToEntity(itemDTO);
        return mapToDTO(itemRepository.save(item));
    }

    // Get All Items
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Item by ID
    public ItemDTO getItemById(Long id) {
        return itemRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // Update Item
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setSku(itemDTO.getSku());
        item.setStock(itemDTO.getStock());

        return mapToDTO(itemRepository.save(item));
    }

    // Delete Item
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemDTO decreaseStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        int newStock = item.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("Not enough stock");
        }
        item.setStock(newStock);
        itemRepository.save(item);

        if (newStock < 10) { // threshold for low stock
            // Send notification (log, email, etc.)
            System.out.println("Low stock alert for item: " + item.getName());
            // Integrate with notification service if needed
        }
        return mapToDTO(item); // assuming you have a mapper
    }
}
