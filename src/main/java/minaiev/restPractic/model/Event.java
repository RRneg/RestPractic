package minaiev.restPractic.model;

import javax.persistence.*;
import java.util.Date;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    @Column(name = "created")
    @CreationTimestamp
    private Date created;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "file_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private File file;

}
