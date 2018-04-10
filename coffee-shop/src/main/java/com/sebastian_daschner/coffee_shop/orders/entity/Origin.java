package com.sebastian_daschner.coffee_shop.orders.entity;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.Set;

import static com.sebastian_daschner.coffee_shop.orders.entity.Origin.FIND_ALL;

@Entity
@Table(name = "origins")
@NamedQuery(name = FIND_ALL, query = "select o from Origin o")
public class Origin {

    public static final String FIND_ALL = "Origin.findAll";

    @Id
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "origin_coffee_types", joinColumns = @JoinColumn(name = "origin_name", nullable = false))
    @Column(name = "coffee_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Set<CoffeeType> coffeeTypes = EnumSet.noneOf(CoffeeType.class);

    public Origin() {
    }

    public Origin(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<CoffeeType> getCoffeeTypes() {
        return coffeeTypes;
    }

}
