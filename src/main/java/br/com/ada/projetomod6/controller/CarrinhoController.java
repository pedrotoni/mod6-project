package br.com.ada.projetomod6.controller;

import br.com.ada.projetomod6.model.Carrinho;
import br.com.ada.projetomod6.model.Venda;
import br.com.ada.projetomod6.service.CarrinhoService;
import br.com.ada.projetomod6.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public List<Carrinho> listarCarrinhos() {
        return carrinhoService.listarCarrinhos();
    }

    @GetMapping("/{id_carrinho}")
    public Carrinho buscaCarrinhoPorId(@PathVariable Long id_carrinho) {
        return carrinhoService.buscarCarrinhoPorId(id_carrinho);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarCarrinho(@RequestBody Carrinho carrinho) {
        carrinhoService.salvarCarrinho(carrinho);
    }

    @PutMapping
    public void atualizarCarrinho(@RequestBody Carrinho carrinho) {
        carrinhoService.atualizarCarrinho(carrinho);
    }

    @DeleteMapping("/{id_carrinho}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCarrinho(@PathVariable Long id_carrinho) {
        carrinhoService.deletarCarrinho(id_carrinho);
    }
}