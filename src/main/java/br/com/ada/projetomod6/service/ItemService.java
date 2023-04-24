package br.com.ada.projetomod6.service;

import br.com.ada.projetomod6.model.ItemVenda;
import br.com.ada.projetomod6.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemVenda> listarItens() {
        return itemRepository.findAll();
    }

    public ItemVenda salvarItem(ItemVenda item) {
        return itemRepository.save(item);
    }

    public ItemVenda buscarItemPorId(Long id_item) {
        Optional<ItemVenda> itemBuscado = itemRepository.findById(id_item);
        if (itemBuscado.isPresent()) {
            return itemBuscado.get();
        } else {
            throw new IllegalArgumentException
                    ("Item ID nº"+id_item+" não foi encontrado em nosso banco de dados.");
        }
    }

    public void atualizarItem(ItemVenda item) {
        Optional<ItemVenda> itemOptional = itemRepository.findById(item.getId_item());
        if (itemOptional.isPresent()) {
            ItemVenda itemNovaVersao = itemOptional.get();
            itemNovaVersao.setProduto(item.getProduto());
            itemNovaVersao.setQtd(item.getQtd());
            itemRepository.save(itemNovaVersao);
        } else {
            throw new IllegalArgumentException
                    ("Não foi possível atualizar o Item ID n."+item.getId_item()+
                            " pois o mesmo não está cadastrado em nosso banco de dados.");
        }
    }

    public void deletarItem(Long id_item) {
        itemRepository.deleteById(id_item);
    }
}
