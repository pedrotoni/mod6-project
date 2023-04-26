package br.com.ada.projetomod6;

import br.com.ada.projetomod6.model.*;
import br.com.ada.projetomod6.repository.CarrinhoRepository;
import br.com.ada.projetomod6.repository.ClienteRepository;
import br.com.ada.projetomod6.repository.ProdutoRepository;
import br.com.ada.projetomod6.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ClienteTest {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private VendaRepository vendaRepository;

    @BeforeEach
    public void criaClienteTeste(){
        Cliente cliente = new Cliente();
        cliente.setNome("Dummy");
        cliente.setCpf("00011122233");
        cliente.setSaldo(BigDecimal.ZERO);
        clienteRepository.save(cliente);
    }

    //• O sistema verifica se o cliente tem saldo suficiente para efetuar a compra
    @Test
    public void testSaldoClienteSuficiente() {
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setSaldo(BigDecimal.valueOf(1000.00));
        clienteRepository.save(cliente);

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
        clienteRepository.save(cliente);

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

    //• O sistema atualiza o saldo do cliente
    @Test
    public void testSaldoClienteAtualizadoAposCompra() {
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setSaldo(BigDecimal.valueOf(500.00));
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setNome("Arroz");
        produto.setDescricao("Alimento");
        produto.setPreco(BigDecimal.valueOf(15.00));
        produto.setQtdDisponivelEmEstoque(100);
        produtoRepository.save(produto);

        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setProduto(produto);
        itemVenda.setQtd(10);
        carrinho.addItem(itemVenda);

        BigDecimal saldoClienteEsperado = cliente.getSaldo().subtract(carrinho.calculaValorTotalDoCarrinho());
        cliente.setSaldo(saldoClienteEsperado);
        BigDecimal saldoClienteAposCompra = clienteRepository.findById(cliente.getId_cliente())
                .orElseThrow(() -> new IllegalArgumentException
                        ("Cliente ID"+cliente.getId_cliente()+" não foi encontrado.")).getSaldo();
        Assertions.assertEquals(saldoClienteEsperado, saldoClienteAposCompra);

    }

    private Object[] setUpVendasCliente() {
        Cliente cliente = clienteRepository.findAll().get(0);
        cliente.setSaldo(BigDecimal.valueOf(500.00));
        cliente.setLista_vendas(new ArrayList<>());

        Produto p1 = new Produto();
        Produto p2 = new Produto();
        Produto p3 = new Produto();
        p1.setNome("Arroz");
        p2.setNome("Feijão");
        p3.setNome("Batata");
        p1.setDescricao("Alimento");
        p2.setDescricao("Alimento");
        p3.setDescricao("Alimento");
        p1.setPreco(BigDecimal.valueOf(10.00));
        p2.setPreco(BigDecimal.valueOf(8.00));
        p3.setPreco(BigDecimal.valueOf(6.00));
        p1.setQtdDisponivelEmEstoque(1000);
        p2.setQtdDisponivelEmEstoque(1000);
        p3.setQtdDisponivelEmEstoque(1000);
        produtoRepository.save(p1);
        produtoRepository.save(p2);
        produtoRepository.save(p3);

        Carrinho carrinho1 = new Carrinho();
        Carrinho carrinho2 = new Carrinho();
        carrinho1.setCliente(cliente);
        carrinho2.setCliente(cliente);

        ItemVenda iv1 = new ItemVenda();
        ItemVenda iv2 = new ItemVenda();
        ItemVenda iv3 = new ItemVenda();
        iv1.setProduto(p1);
        iv1.setQtd(3);
        iv2.setProduto(p2);
        iv2.setQtd(5);
        iv3.setProduto(p3);
        iv3.setQtd(10);
        carrinho1.addItem(iv1);
        carrinho1.addItem(iv2);
        carrinho2.addItem(iv3);
        carrinhoRepository.save(carrinho1);
        carrinhoRepository.save(carrinho2);

        Venda v1 = new Venda();
        Venda v2 = new Venda();
        v1.setCliente(cliente);
        v1.setLista_itens(carrinho1.getItensVenda());
        v2.setCliente(cliente);
        v2.setLista_itens(carrinho2.getItensVenda());
        vendaRepository.save(v1);
        vendaRepository.save(v2);

        return new Object[]{cliente, v1, v2};
    }

    //• Verificar se é possível buscar as vendas de um determinado cliente
    //• Verificar se todas as vendas do cliente são retornadas
    @Test
    public void testVerificaVendasDeUmCliente() {
        Object[] setUp = setUpVendasCliente();
        Cliente cliente = (Cliente) setUp[0];
        Venda v1 = (Venda) setUp[1];
        Venda v2 = (Venda) setUp[2];
        cliente.getLista_vendas().add(v1);
        cliente.getLista_vendas().add(v2);
        clienteRepository.save(cliente);

        List<Venda> listaDeVendasCliente = clienteRepository.findById
                (cliente.getId_cliente()).orElseThrow(() -> new IllegalArgumentException
                                ("Cliente ID: "+cliente.getId_cliente()+" não encontrado em nosso banco de dados."))
                .getLista_vendas();
        Assertions.assertEquals(2, listaDeVendasCliente.size());
        Assertions.assertEquals(v1.getId_venda(), listaDeVendasCliente.get(0).getId_venda());
        Assertions.assertEquals(v2.getId_venda(), listaDeVendasCliente.get(1).getId_venda());
        Assertions.assertTrue(listaDeVendasCliente.contains(v1));
        Assertions.assertTrue(listaDeVendasCliente.contains(v2));
    }


}
