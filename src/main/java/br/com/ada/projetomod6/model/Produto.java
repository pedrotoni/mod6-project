package br.com.ada.projetomod6.model;

import jakarta.persistence.*;
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
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private BigDecimal preco;
}
