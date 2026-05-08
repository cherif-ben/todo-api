package com.todo.dto;

import com.todo.entity.StatutTache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacheResponseDTO {
    private Long id;
    private String titre;
    private String description;
    private StatutTache statut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
