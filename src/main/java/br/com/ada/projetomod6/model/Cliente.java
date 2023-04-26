package br.com.ada.projetomod6.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_cliente;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT '0.00'")
    private BigDecimal saldo = BigDecimal.ZERO;
    @OneToMany(mappedBy = "cliente")
    @ElementCollection
    private List<Venda> lista_vendas;


}
