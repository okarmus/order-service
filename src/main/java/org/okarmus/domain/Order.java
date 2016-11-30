package org.okarmus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by mateusz on 30.11.16.
 */

@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    private long id;
    private String description;

    public Order() {}

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
