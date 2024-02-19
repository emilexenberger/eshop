package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.Item;
import webemex.eshop.repository.ItemRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

//    TODO: Daj do poznamok a odstran
//    private final ItemRepository itemRepository;
//
//    public ItemService(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(UUID id) {
        return itemRepository.findById(id).get();
    }

    public void deleteItemById(UUID id) {
        itemRepository.deleteById(id);
    }
}
