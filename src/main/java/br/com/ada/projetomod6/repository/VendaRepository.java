package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda,Long> {
}
