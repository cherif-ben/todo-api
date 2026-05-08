package com.todo;

import com.todo.dto.TacheRequestDTO;
import com.todo.dto.TacheResponseDTO;
import com.todo.dto.TacheUpdateDTO;
import com.todo.entity.StatutTache;
import com.todo.entity.Tache;
import com.todo.exception.TacheNotFoundException;
import com.todo.repository.TacheRepository;
import com.todo.service.TacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires — TacheService")
class TacheServiceTest {

    @Mock
    private TacheRepository tacheRepository;

    @InjectMocks
    private TacheServiceImpl tacheService;

    private Tache tacheExistante;

    @BeforeEach
    void setUp() {
        tacheExistante = Tache.builder()
                .id(1L)
                .titre("Tâche de test")
                .description("Description de test")
                .statut(StatutTache.A_FAIRE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("creer() — doit retourner la tâche créée avec l'id généré")
    void creer_devraitRetournerTacheCreee() {
        TacheRequestDTO dto = new TacheRequestDTO();
        dto.setTitre("Nouvelle tâche");
        dto.setDescription("Ma description");
        dto.setStatut(StatutTache.A_FAIRE);

        when(tacheRepository.save(any(Tache.class))).thenReturn(tacheExistante);

        TacheResponseDTO result = tacheService.creer(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitre()).isEqualTo("Tâche de test");
        verify(tacheRepository, times(1)).save(any(Tache.class));
    }

    @Test
    @DisplayName("listerToutes() — doit retourner la liste complète")
    void listerToutes_devraitRetournerListe() {
        when(tacheRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(tacheExistante));

        List<TacheResponseDTO> result = tacheService.listerToutes();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitre()).isEqualTo("Tâche de test");
    }

    @Test
    @DisplayName("filtrerParStatut() — doit retourner uniquement les tâches du statut demandé")
    void filtrerParStatut_devraitRetournerTachesFiltrees() {
        when(tacheRepository.findByStatutOrderByCreatedAtDesc(StatutTache.A_FAIRE))
                .thenReturn(List.of(tacheExistante));

        List<TacheResponseDTO> result = tacheService.filtrerParStatut(StatutTache.A_FAIRE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatut()).isEqualTo(StatutTache.A_FAIRE);
    }

    @Test
    @DisplayName("trouverParId() — doit retourner la tâche si elle existe")
    void trouverParId_devraitRetournerTache() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tacheExistante));

        TacheResponseDTO result = tacheService.trouverParId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("trouverParId() — doit lever TacheNotFoundException si id inexistant")
    void trouverParId_devraitLeverExceptionSiInexistant() {
        when(tacheRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tacheService.trouverParId(99L))
                .isInstanceOf(TacheNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("modifier() — doit mettre à jour uniquement les champs fournis")
    void modifier_devraitMettreAJourChamps() {
        TacheUpdateDTO dto = new TacheUpdateDTO();
        dto.setStatut(StatutTache.EN_COURS);

        Tache modifiee = Tache.builder()
                .id(1L).titre("Tâche de test")
                .description("Description de test")
                .statut(StatutTache.EN_COURS)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tacheExistante));
        when(tacheRepository.save(any(Tache.class))).thenReturn(modifiee);

        TacheResponseDTO result = tacheService.modifier(1L, dto);

        assertThat(result.getStatut()).isEqualTo(StatutTache.EN_COURS);
        verify(tacheRepository, times(1)).save(any(Tache.class));
    }

    @Test
    @DisplayName("supprimer() — doit appeler deleteById si la tâche existe")
    void supprimer_devraitSupprimerSiExiste() {
        when(tacheRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tacheRepository).deleteById(1L);

        tacheService.supprimer(1L);

        verify(tacheRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("supprimer() — doit lever TacheNotFoundException si id inexistant")
    void supprimer_devraitLeverExceptionSiInexistant() {
        when(tacheRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> tacheService.supprimer(99L))
                .isInstanceOf(TacheNotFoundException.class)
                .hasMessageContaining("99");
    }
}
