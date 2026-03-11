package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "languages_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguagesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String value;
    private String description;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Languages languages;

}
