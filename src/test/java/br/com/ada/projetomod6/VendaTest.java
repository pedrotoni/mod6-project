package br.com.ada.projetomod6;

import br.com.ada.projetomod6.model.*;
import br.com.ada.projetomod6.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
public class VendaTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @BeforeEach
    public void criaClienteTeste(){
        Cliente cliente = new Cliente();
        cliente.setNome("Dummy");
        cliente.setCpf("00011122233");
        cliente.setSaldo(BigDecimal.ZERO);
        clienteRepository.save(cliente);
    }

    private Object[] setUpItemVendaProduto() {
        Cliente cliente = new Cliente();
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);

        Produto produto = new Produto();
        produto.setNome("Bola");
        produto.setDescricao("Produto esportivo");
        produto.setPreco(BigDecimal.valueOf(50.00));
        produto.setQtdDisponivelEmEstoque(1000);
        produtoRepository.save(produto);

        Produto produto2 = new Produto();
        produto2.setNome("Camiseta");
        produto2.setDescricao("Vestuário");
        produto2.setPreco(BigDecimal.valueOf(75.00));
        produto2.setQtdDisponivelEmEstoque(1000);
        produtoRepository.save(produto2);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQtd(10);
        item.setCarrinho(carrinho);
        ItemVenda item2 = new ItemVenda();
        item2.setProduto(produto2);
        item2.setQtd(5);
        item2.setCarrinho(carrinho);
        carrinho.addItem(item);
        carrinho.addItem(item2);
        carrinhoRepository.save(carrinho);

        return new Object[]{carrinho, produto, produto2, cliente};
    }

    //• Verificar se é possível buscar os itens de venda de um determinado produto
    @Test
    public void testBuscaItemVendaDeUmProduto() {
        Object[] setUp = setUpItemVendaProduto();
        Carrinho carrinho = (Carrinho) setUp[0];
        Produto produto = (Produto) setUp[1];
        Produto produto2 = (Produto) setUp[2];

        List<ItemVenda> listaItemProduto1 = carrinho.getItensDaVendaPeloProduto(produto);
        List<ItemVenda> listaItemProduto2 = carrinho.getItensDaVendaPeloProduto(produto2);

        Assertions.assertEquals(1, listaItemProduto1.size());
        Assertions.assertEquals(produto.getId_produto(), listaItemProduto1.get(0).getProduto().getId_produto());
        Assertions.assertEquals(1, listaItemProduto2.size());
        Assertions.assertEquals(produto2.getId_produto(), listaItemProduto2.get(0).getProduto().getId_produto());

    }

    //• Validar se as informações dos itens de venda retornados estão corretas, como a quantidade e o preço unitário
    @Test
    public void testVerificaQuantidadePrecoUnitarioDeItemDaVenda() {
        Object[] setUp = setUpItemVendaProduto();
        Carrinho carrinho = (Carrinho) setUp[0];
        Produto produto = (Produto) setUp[1];

        List<ItemVenda> listaItemProduto = carrinho.getItensDaVendaPeloProduto(produto);
        ItemVenda item = listaItemProduto.get(0);
        itemRepository.save(item);

        Assertions.assertEquals(10, item.getQtd());
        Assertions.assertEquals(produto.getPreco(),item.getProduto().getPreco());
    }

    @Test
    public void testVerificaRetornoDeTodosOsItensDeVendaDoProduto() {
        Object[] setUp = setUpItemVendaProduto();
        Produto produto = (Produto) setUp[1];
        Cliente cliente = (Cliente) setUp[3];

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinhoRepository.save(carrinho);

        ItemVenda item1 = new ItemVenda();
        item1.setProduto(produto);
        item1.setQtd(50);
        item1.setCarrinho(carrinho);
        ItemVenda item2 = new ItemVenda();
        item2.setProduto(produto);
        item2.setQtd(35);
        item2.setCarrinho(carrinho);
        carrinho.addItem(item1);
        carrinho.addItem(item2);

        List<ItemVenda> listaItemVendaProduto = carrinho.getItensDaVendaPeloProduto(produto);
        Assertions.assertEquals(2, listaItemVendaProduto.size());
        Assertions.assertEquals(item1.getId_item(), listaItemVendaProduto.get(0).getId_item());
        Assertions.assertEquals(item2.getId_item(), listaItemVendaProduto.get(1).getId_item());
    }
}
