package br.com.ada.projetomod6.controller;

import br.com.ada.projetomod6.model.Venda;
import br.com.ada.projetomod6.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> listarVendas() {
        return vendaService.listarVendas();
    }

    @GetMapping("/{id_venda}")
    public Venda buscaVendaPorId(@PathVariable Long id_venda) {
        return vendaService.buscarVendaPorId(id_venda);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarVenda(@RequestBody Venda venda) {
        vendaService.salvarVenda(venda);
    }

    @PutMapping
    public void atualizarVenda(@RequestBody Venda venda) {
        vendaService.atualizarVenda(venda);
    }

    @DeleteMapping("/{id_venda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarVenda(@PathVariable Long id_venda) {
        vendaService.deletarVenda(id_venda);
    }
}
