package be.vdab.muziek.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
@NamedEntityGraph(name = Album.MET_ARTIEST, attributeNodes = @NamedAttributeNode("artiest"))
public class Album {
    public static final String MET_ARTIEST = "Album.metArtiest";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artiestId")
    private Artiest artiest;
    @Range(min = 0, max = 10)
    private int score;
    @ElementCollection
    @CollectionTable(name = "tracks", joinColumns = @JoinColumn(name = "albumId"))
    private Set<Track> tracks = new LinkedHashSet<>();

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public int getScore() {
        return score;
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    public Album(String naam, Artiest artiest, int score, Set<Track> tracks) {
        this.naam = naam;
        this.artiest = artiest;
        this.score = score;
        this.tracks = tracks;
    }

    protected Album() {
    }

    public LocalTime getTijd() {
        LocalTime som = LocalTime.MIN;
        for (var track : tracks) {
            var tijd = track.getTijd();
            som = som.plusHours(tijd.getHour()).plusMinutes(tijd.getMinute()).plusSeconds(tijd.getSecond());
        }
        return som;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
