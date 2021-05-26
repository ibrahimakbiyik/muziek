package be.vdab.muziek.services;

import be.vdab.muziek.domain.Album;
import be.vdab.muziek.domain.Artiest;
import be.vdab.muziek.domain.Track;
import be.vdab.muziek.repositories.AlbumRepository;
import be.vdab.muziek.exceptions.AlbumNietGevondenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAlbumServiceTest {
    private DefaultAlbumService service;
    @Mock
    private AlbumRepository repository;
    private Album album;
    @BeforeEach
    void beforeEach() {
        album = new Album("test", new Artiest("test"), 10, new LinkedHashSet<Track>());
        service = new DefaultAlbumService(repository);
    }

    @Test
    void wijzigScore() {
       when(repository.findById(1)).thenReturn(Optional.of(album));
       service.wijzigScore(1, 10);
       assertThat(album.getScore()).isEqualTo(10);
       verify(repository).findById(1);
    }

    @Test
    void wijzigScoreOnbestaandeAlbum() {
        assertThatExceptionOfType(AlbumNietGevondenException.class).isThrownBy(() -> service.wijzigScore(-1, 10));
        verify(repository).findById(-1);
    }

}