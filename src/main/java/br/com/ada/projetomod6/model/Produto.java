package br.com.ada.projetomod6.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal preco;
    @Column(nullable = false)
    @PositiveOrZero
    @DecimalMin(value = "0", inclusive = true)
    private Integer qtdDisponivelEmEstoque;

    public Integer atualizarEstoque(Integer qtdComprada) {
        if (qtdDisponivelEmEstoque < qtdComprada) {
            throw new IllegalArgumentException("Não há estoque disponível para essa compra.");
        }
        return qtdDisponivelEmEstoque -= qtdComprada;
    }
}
