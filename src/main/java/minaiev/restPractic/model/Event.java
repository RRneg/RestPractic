package minaiev.restPractic.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "event_status")
    private EventAction eventStatus;

    @Column(name = "change_made")
    private Date changeMade;


    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "file_id")
    private Integer fileId;

    public Event(){
    }

    public Event(Integer id, EventAction eventStatus, Date changeTime, Integer userId, Integer fileId) {
        this.id = id;
        this.eventStatus = eventStatus;
        this.changeMade = changeTime;
        this.userId = userId;
        this.fileId = fileId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventAction getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventAction eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Date getChangeMade() {
        return changeMade;
    }

    public void setChangeMade(Date changeMade) {
        this.changeMade = changeMade;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventStatus=" + eventStatus +
                ", changeTime=" + changeMade +
                ", userId=" + userId +
                ", fileId=" + fileId +
                '}';
    }
}
