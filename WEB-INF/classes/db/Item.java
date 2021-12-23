package db;

import jakarta.persistence.*;

@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(length=500, nullable=false, unique=false)
    public String name;

    @Column(nullable=false, unique=false)
    public double price;

    @Column(nullable=false, unique=false)
    public Long stock;
}
