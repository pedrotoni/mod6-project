package br.com.ada.projetomod6.controller;

import br.com.ada.projetomod6.model.Produto;
import br.com.ada.projetomod6.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id_produto}")
    public Produto exibeProdutoPorId(@PathVariable Long id_produto) {
        return produtoService.buscarProdutoPorId(id_produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarProduto(@RequestBody Produto produto) {
        produtoService.salvarProduto(produto);
    }

    @PutMapping
    public void atualizarProduto(@RequestBody Produto produto) {
        produtoService.atualizarProduto(produto);
    }

    @DeleteMapping("/{id_produto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long id_produto) {
        produtoService.deletarProduto(id_produto);
    }


}
