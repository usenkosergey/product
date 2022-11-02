package com.example.productlist.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer kcal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "list_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_product_id"))
    private ListEntity listEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity productEntity = (ProductEntity) o;
        return id.equals(productEntity.id) && name.equals(productEntity.name) && description.equals(productEntity.description) && kcal.equals(productEntity.kcal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, kcal);
    }

}
