package minaiev.restPractic.dto;

public class EventDTO {

    private String eventStatus;
    private String dateChange;


    public EventDTO() {
    }

    public EventDTO(String eventStatus, String dateChange, String userName) {
        this.eventStatus = eventStatus;
        this.dateChange = dateChange;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getDateChange() {
        return dateChange;
    }

    public void setDateChange(String dateChange) {
        this.dateChange = dateChange;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventStatus='" + eventStatus + '\'' +
                ", dateChange='" + dateChange + '\'' +
                '}';
    }
}
