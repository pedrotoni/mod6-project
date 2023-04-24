package br.com.ada.projetomod6.repository;

import br.com.ada.projetomod6.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    Optional<Produto> findProdutoByNome(String nome);
}
