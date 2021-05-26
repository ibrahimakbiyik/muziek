package be.vdab.muziek.domain;

import javax.persistence.*;
import java.time.LocalTime;

@Embeddable
@Access(AccessType.FIELD)
public class Track {
    private String naam;
    private LocalTime tijd;

    public Track(String naam, LocalTime tijd) {
        this.naam = naam;
        this.tijd = tijd;
    }

    protected Track() {
    }

    public String getNaam() {
        return naam;
    }

    public LocalTime getTijd() {
        return tijd;
    }

    @Override
    public boolean equals(Object object) {
       if (object instanceof Track) {
           return naam.equalsIgnoreCase(((Track) object).naam);
       }
       return false;
    }

    @Override
    public int hashCode() {
        return naam.toUpperCase().hashCode();
    }
}
