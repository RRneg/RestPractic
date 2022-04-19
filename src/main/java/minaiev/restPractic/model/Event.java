package minaiev.restPractic.model;

import javax.persistence.*;
import java.util.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "created")
    private Date created;


    @JoinColumns(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumns(name = "file_id")
    @OneToOne
    private File file;


}
