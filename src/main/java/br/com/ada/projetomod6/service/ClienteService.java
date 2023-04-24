package br.com.ada.projetomod6.service;

import br.com.ada.projetomod6.model.Cliente;
import br.com.ada.projetomod6.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return this.clienteRepository.findAll();
    }

    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Long id_cliente) {
        Optional<Cliente> clienteBuscado = clienteRepository.findById(id_cliente);
        if (clienteBuscado.isPresent()) {
            return clienteBuscado.get();
        } else {
            throw new IllegalArgumentException
                    ("Não encontramos cliente ID nº"+id_cliente+" em nosso banco de dados.");
        }
    }


    public Cliente buscarClientePorCpf(String cpf){
        Optional<Cliente> clienteBuscado = clienteRepository.findClienteByCpf(cpf);
        if (clienteBuscado.isPresent()) {
            return clienteBuscado.get();
        } else {
            throw new IllegalArgumentException
                    ("Não encontramos cliente de nome "+cpf+" em nosso banco de dados.");
        }
    }

    public void atualizarCliente(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cliente.getId_cliente());
        if (clienteOptional.isPresent()) {
            Cliente clienteNovaVersao = clienteOptional.get();
            clienteNovaVersao.setNome(cliente.getNome());
            clienteNovaVersao.setCpf(cliente.getCpf());
            clienteRepository.save(clienteNovaVersao);
        } else {
            throw new IllegalArgumentException
                    ("Não foi possível atualizar o cliente CPF: "+cliente.getCpf()+
                            " pois o mesmo não está cadastrado em nosso banco de dados.");
        }
    }

    public void deletarCliente(Long id_cliente) {
        this.clienteRepository.deleteById(id_cliente);
    }
}
