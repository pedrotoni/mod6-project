package br.com.ada.projetomod6.controller;

import br.com.ada.projetomod6.model.Cliente;
import br.com.ada.projetomod6.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id_cliente}")
    public Cliente buscaClientePorId(@PathVariable Long id_cliente) {
        return clienteService.buscarClientePorId(id_cliente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarCliente(@RequestBody Cliente cliente) {
        clienteService.salvarCliente(cliente);
    }

    @GetMapping("/cpf/{cpf}")
    public Cliente buscaClientePorCpf(@PathVariable String cpf) {
        return clienteService.buscarClientePorCpf(cpf);
    }

    @PutMapping
    public void atualizarCliente(@RequestBody Cliente cliente) {
        clienteService.atualizarCliente(cliente);
    }

    @DeleteMapping("/{id_cliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(@PathVariable Long id_cliente) {
        clienteService.deletarCliente(id_cliente);
    }
}
