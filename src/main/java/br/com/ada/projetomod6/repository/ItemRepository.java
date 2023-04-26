package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.ItemVenda;
import br.com.ada.projetomod6.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemVenda, Long> {

}
