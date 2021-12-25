package db;

import jakarta.persistence.*;

@Entity
@Table(name="files")
public class File_pool {
    @Transient
    public final static String path="/home/ig/temp/";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;
}
