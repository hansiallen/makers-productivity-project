package com.example.productivity.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Event_Attendees")
public class EventAttendee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long eventId;
        private Long attendeeId;
        private Long attendingStatus;

        EventAttendee(){}

        EventAttendee(Long id, Long eventId, Long attendeeId, Long attendingStatus){
                this.id = id;
                this.eventId = eventId;
                this.attendeeId = attendeeId;
                this.attendingStatus = attendingStatus;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {

                this.id = id;
        }

        public Long getAttendeeId() {
                return attendeeId;
        }

        public void setAttendeeId(Long attendeeId) {
                this.attendeeId = attendeeId;
        }

        public Long getAttendingStatus() {
                return attendingStatus;
        }

        public void setAttendingStatus(Long attendingStatus) {
                this.attendingStatus = attendingStatus;
        }

        public Long getEventId() {
                return eventId;
        }

        public void setEventId(Long eventId) {
                this.eventId = eventId;
        }
}
