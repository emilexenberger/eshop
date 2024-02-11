package webemex.eshop.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webemex.eshop.model.Item;
import webemex.eshop.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    void whenFindAllItems_thenItemListShouldBeReturned() {
        Item item = new Item();
        item.setProductName("TestItem");
        List<Item> list = new ArrayList<>();
        list.add(item);

        Mockito.when(itemRepository.findAll()).thenReturn(list);

        List<Item> items = itemService.findAllItems();
        assertEquals(1, items.size());
        assertEquals("TestItem", items.get(0).getProductName());
    }
}