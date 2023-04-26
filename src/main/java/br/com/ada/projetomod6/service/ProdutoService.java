package br.com.ada.projetomod6.service;

import br.com.ada.projetomod6.model.Produto;
import br.com.ada.projetomod6.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return this.produtoRepository.findAll();
    }

    public void salvarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(Long id_produto) {
        Optional<Produto> produtoBuscado = produtoRepository.findById(id_produto);
        if (produtoBuscado.isPresent()) {
            return produtoBuscado.get();
        } else {
            throw new IllegalArgumentException
                    ("Não encontramos produto ID nº"+id_produto+" em nosso banco de dados.");
        }
    }

    public void atualizarProduto(Produto produto) {
        Optional<Produto> produtoOptional = produtoRepository.findById(produto.getId_produto());
        if (produtoOptional.isPresent()) {
            Produto produtoNovaVersao = produtoOptional.get();
            produtoNovaVersao.setNome(produto.getNome());
            produtoNovaVersao.setDescricao(produto.getDescricao());
            produtoNovaVersao.setPreco(produto.getPreco());
            produtoNovaVersao.setQtdDisponivelEmEstoque(produto.getQtdDisponivelEmEstoque());
            produtoRepository.save(produtoNovaVersao);
        } else {
            throw new IllegalArgumentException
                    ("Não foi possível atualizar o Produto "+produto.getNome()+
                            " pois o mesmo não está cadastrado em nosso banco de dados.");
        }
    }

    public void deletarProduto(Long id_produto) {
        this.produtoRepository.deleteById(id_produto);
    }
}
