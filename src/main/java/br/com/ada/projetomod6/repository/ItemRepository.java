package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemVenda, Long> {
}
