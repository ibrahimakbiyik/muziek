package be.vdab.muziek.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAlbumRepository.class)
@Sql({"/insertArtiesten.sql", "/insertAlbum.sql"})
class JpaAlbumRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ALBUMS = "albums";
    private final JpaAlbumRepository repository;
    private final EntityManager manager;

    JpaAlbumRepositoryTest(JpaAlbumRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestAlbum() {
        return jdbcTemplate.queryForObject("select id from albums where naam = 'test'", Long.class);
    }

    @Test
    void findAll() {
        var albums = repository.findAll();
        Assertions.assertThat(albums).hasSize(countRowsInTable(ALBUMS))
                .extracting(album -> album.getNaam().toLowerCase()).isSorted();
        Assertions.assertThat(albums).extracting(album -> album.getArtiest().getNaam()); // N + 1 probleem
    }

    @Test
    void findById() {
        var album = repository.findById(idVanTestAlbum()).get();
        Assertions.assertThat(album.getNaam()).isEqualTo("test");
        Assertions.assertThat(album.getArtiest().getNaam()).isEqualTo("test");
        Assertions.assertThat(album.getTijd()).isEqualTo(LocalTime.of(0, 10));
    }

    @Test
    void findByObestaandeId() {
        Assertions.assertThat(repository.findById(-1)).isNotPresent();
    }
}