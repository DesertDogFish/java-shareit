package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/{id}")
    public ItemDto get(@PathVariable Integer id) {
        return itemService.get(id);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                @RequestParam String text) {
        return itemService.search(text, userId);
    }

    @PostMapping
    public ItemDto save(@RequestHeader("X-Sharer-User-Id") Integer userId,
                        @Valid @RequestBody ItemDto itemDto) {
        return itemService.save(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto patch(@RequestHeader("X-Sharer-User-Id") Integer userId,
                         @PathVariable Integer itemId,
                         @RequestBody ItemDto itemDto) {
        return itemService.patch(itemDto, itemId, userId);
    }
}
