package br.com.ada.projetomod6.controller;

import br.com.ada.projetomod6.model.ItemVenda;
import br.com.ada.projetomod6.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemVenda> listarItens() {
        return itemService.listarItens();
    }

    @GetMapping("/{id_item}")
    public ItemVenda buscaItemPorId(@PathVariable Long id_item) {
        return itemService.buscarItemPorId(id_item);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarItem(@RequestBody ItemVenda item) {
        itemService.salvarItem(item);
    }

    @PutMapping
    public void atualizarItem(@RequestBody ItemVenda item) {
        itemService.atualizarItem(item);
    }

    @DeleteMapping("/{id_item}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarItem(@PathVariable Long id_item) {
        itemService.deletarItem(id_item);
    }
}
