import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.*;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;


@Entity
@Table(name="user")
public class User
{
    @Id
    //自动生成主健，插入时值转换为布尔值必须为false
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(length=500, nullable=false, unique=false)
    public String nick_name;

    @Column(length=1024, nullable=false, unique=false)
    public String password;
}