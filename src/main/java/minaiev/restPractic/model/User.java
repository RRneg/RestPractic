package minaiev.restPractic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @JsonProperty("user_name")
    @Column(name = "user_name")
    private String userName;




    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<Event> events;


    public User(Integer id, String userName, List<Event> events) {
        this.id = id;
        this.userName = userName;
        this.events = events;
    }


}
