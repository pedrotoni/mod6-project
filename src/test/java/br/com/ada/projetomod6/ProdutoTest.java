package br.com.ada.projetomod6;

import br.com.ada.projetomod6.model.Carrinho;
import br.com.ada.projetomod6.model.Cliente;
import br.com.ada.projetomod6.model.ItemVenda;
import br.com.ada.projetomod6.model.Produto;
import br.com.ada.projetomod6.repository.CarrinhoRepository;
import br.com.ada.projetomod6.repository.ClienteRepository;
import br.com.ada.projetomod6.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class ProdutoTest {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    //• O cliente adiciona um produto ao carrinho de compras
    @Test
    public void testAdicionarProdutoAoCarrinho() {
        Cliente cliente = new Cliente();
        cliente.setNome("Alexandre");
        cliente.setCpf("30020010022");
        cliente.setSaldo(BigDecimal.valueOf(5000.00));
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setNome("Caneta");
        produto.setPreco(BigDecimal.valueOf(3.50));
        produto.setDescricao("Instrumento de escrita");
        produto.setQtdDisponivelEmEstoque(1000);
        produtoRepository.save(produto);

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);

        ItemVenda item = new ItemVenda();
        item.setCarrinho(carrinho);
        item.setProduto(produto);
        item.setQtd(30);

        carrinho.addItem(item);
        carrinhoRepository.save(carrinho);

        Assertions.assertEquals(1, carrinho.getItensVenda().size());
        Assertions.assertEquals(item, carrinho.getItensVenda().get(0));
    }

    //• O sistema atualiza o estoque dos produtos comprados
    @Test
    public void testAtualizacaoCorretaDeEstoqueAposCompra() {
        Produto produto = new Produto();
        produto.setNome("Arroz");
        produto.setDescricao("Alimento");
        produto.setPreco(BigDecimal.valueOf(15.00));
        produto.setQtdDisponivelEmEstoque(200);
        produtoRepository.save(produto);

        Cliente cliente = new Cliente();
        cliente.setNome("Alexandre");
        cliente.setCpf("30020010022");
        cliente.setSaldo(BigDecimal.valueOf(5000.00));
        clienteRepository.save(cliente);

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinhoRepository.save(carrinho);

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setProduto(produto);
        itemVenda.setQtd(3);
        itemVenda.setCarrinho(carrinho);
        carrinho.addItem(itemVenda);

        Integer quantidadeEsperadaEmEstoque = produto.atualizarEstoque(itemVenda.getQtd());

        Produto produtoAtualizado = produtoRepository.findById(produto.getId_produto())
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado nenhum produto com este ID."));

        Assertions.assertEquals(quantidadeEsperadaEmEstoque, produtoAtualizado.getQtdDisponivelEmEstoque());

    }
}
