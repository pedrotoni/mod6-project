package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho,Long> {
}
