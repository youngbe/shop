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

    //0代表在售，非0代表下架
    @Column(nullable = false, unique = false)
    public byte on_market;

    @ManyToOne
    @JoinColumn(name="cover", referencedColumnName="id", nullable = false)
    public File_pool cover;

    @ManyToOne
    @JoinColumn(name="user", referencedColumnName="id", nullable = false)
    public User user;
}
