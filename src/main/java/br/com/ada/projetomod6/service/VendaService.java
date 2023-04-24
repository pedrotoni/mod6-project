package br.com.ada.projetomod6.service;

import br.com.ada.projetomod6.model.Produto;
import br.com.ada.projetomod6.model.Venda;
import br.com.ada.projetomod6.repository.ProdutoRepository;
import br.com.ada.projetomod6.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    public List<Venda> listarVendas() {
        return this.vendaRepository.findAll();
    }

    public void salvarVenda(Venda venda) {
        vendaRepository.save(venda);
    }

    public Venda buscarVendaPorId(Long id_venda) {
        Optional<Venda> vendaBuscada = vendaRepository.findById(id_venda);
        if (vendaBuscada.isPresent()) {
            return vendaBuscada.get();
        } else {
            throw new IllegalArgumentException
                    ("Não encontramos a venda ID nº"+id_venda+" em nosso banco de dados.");
        }
    }

    public void atualizarVenda(Venda venda) {
        Optional<Venda> vendaOptional = vendaRepository.findById(venda.getId_venda());
        if (vendaOptional.isPresent()) {
            Venda vendaNovaVersao = vendaOptional.get();
            vendaNovaVersao.setCliente(venda.getCliente());
            vendaNovaVersao.setLista_itens(venda.getLista_itens());
            vendaRepository.save(vendaNovaVersao);
        } else {
            throw new IllegalArgumentException
                    ("Não foi possível atualizar a venda "+venda.getId_venda()+
                            " pois a mesma não está cadastrada em nosso banco de dados.");
        }
    }

    public void deletarVenda(Long id_venda) {
        this.vendaRepository.deleteById(id_venda);
    }
}
