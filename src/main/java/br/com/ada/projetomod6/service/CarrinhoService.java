package br.com.ada.projetomod6.service;

import br.com.ada.projetomod6.model.Carrinho;
import br.com.ada.projetomod6.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public List<Carrinho> listarCarrinhos() {
        return this.carrinhoRepository.findAll();
    }

    public void salvarCarrinho(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }

    public Carrinho buscarCarrinhoPorId(Long id_carrinho) {
        Optional<Carrinho> carrinhoBuscado = carrinhoRepository.findById(id_carrinho);
        if (carrinhoBuscado.isPresent()) {
            return carrinhoBuscado.get();
        } else {
            throw new IllegalArgumentException
                    ("Não encontramos o carrinho ID nº"+id_carrinho+" em nosso banco de dados.");
        }
    }

    public void atualizarCarrinho(Carrinho carrinho) {
        Optional<Carrinho> carrinhoOptional = carrinhoRepository.findById(carrinho.getId_carrinho());
        if (carrinhoOptional.isPresent()) {
            Carrinho carrinhoNovaVersao = carrinhoOptional.get();
            carrinhoNovaVersao.setCliente(carrinho.getCliente());
            carrinhoNovaVersao.setVenda(carrinho.getVenda());
            carrinhoRepository.save(carrinhoNovaVersao);
        } else {
            throw new IllegalArgumentException
                    ("Não foi possível atualizar o carrinho ID: "+carrinho.getId_carrinho()+
                            " pois o mesmo não está cadastrado em nosso banco de dados.");
        }
    }

    public void deletarCarrinho(Long id_carrinho) {
        this.carrinhoRepository.deleteById(id_carrinho);
    }
}
