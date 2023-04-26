package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.ItemVenda;
import br.com.ada.projetomod6.model.Produto;
import br.com.ada.projetomod6.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda,Long> {
}
