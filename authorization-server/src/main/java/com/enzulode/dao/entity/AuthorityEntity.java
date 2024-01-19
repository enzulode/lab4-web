package com.enzulode.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the smallest piece of authority. This is required for better permissions
 * granularity in the entire application.
 */
@Getter
@Setter
@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @Column(name = "authority_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "authority_code", nullable = false)
    private String code;

    @Column(name = "authority_description", nullable = false)
    private String description;

    @Column(name = "active")
    private Boolean active;
}
