package br.com.ada.projetomod6.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="carrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_carrinho;
    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @OneToMany(mappedBy = "carrinho")
    private List<ItemVenda> itensVenda;

    public void addItem(ItemVenda item) {
        if (itensVenda == null) {
            itensVenda = new ArrayList<>();
        }
        item.setCarrinho(this);
        itensVenda.add(item);
    }

    public BigDecimal calculaValorTotalDoCarrinho() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        if (itensVenda != null) {
            for (ItemVenda item : itensVenda) {
                valorTotal =
                        valorTotal.add(item.getProduto().getPreco()
                                .multiply(BigDecimal.valueOf(item.getQtd())));
            }
        }
        return valorTotal;
    }
}
