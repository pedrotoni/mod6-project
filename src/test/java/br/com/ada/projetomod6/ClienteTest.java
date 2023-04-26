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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class ClienteTest {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void criaClienteTeste(){
        Cliente cliente = new Cliente();
        cliente.setNome("Dummy");
        cliente.setCpf("00011122233");
        cliente.setSaldo(BigDecimal.ZERO);
        clienteRepository.save(cliente);
    }

    @Test
    public void testSaldoClienteSuficiente() {
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setSaldo(BigDecimal.valueOf(1000.00));

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinhoRepository.save(carrinho);

        Produto produto = new Produto();
        produto.setNome("Blusa");
        produto.setDescricao("Agasalho de frio");
        produto.setPreco(BigDecimal.valueOf(80.00));
        produto.setQtdDisponivelEmEstoque(1);
        produtoRepository.save(produto);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQtd(4);
        item.setCarrinho(carrinho);

        carrinho.addItem(item);

        BigDecimal valorTotal = carrinho.calculaValorTotalDoCarrinho();
        Assertions.assertTrue(cliente.getSaldo().doubleValue() >= valorTotal.doubleValue());
    }

    @Test
    public void testSaldoClienteInsuficiente() {
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setSaldo(BigDecimal.valueOf(10.00));

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinhoRepository.save(carrinho);

        Produto produto = new Produto();
        produto.setNome("Blusa");
        produto.setDescricao("Agasalho de frio");
        produto.setPreco(BigDecimal.valueOf(80.00));
        produto.setQtdDisponivelEmEstoque(1);
        produtoRepository.save(produto);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQtd(4);
        item.setCarrinho(carrinho);

        carrinho.addItem(item);

        BigDecimal valorTotal = carrinho.calculaValorTotalDoCarrinho();
        Assertions.assertTrue(cliente.getSaldo().doubleValue() <= valorTotal.doubleValue());
    }

}
