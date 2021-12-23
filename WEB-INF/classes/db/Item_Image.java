package db;

import jakarta.persistence.*;

@Entity
@Table(name="item_image")
public class Item_Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name="item_id", referencedColumnName="id", nullable = false)
    public Item item;

    @ManyToOne
    @JoinColumn(name="image_id", referencedColumnName="id", nullable = false)
    public File_pool img;
}
