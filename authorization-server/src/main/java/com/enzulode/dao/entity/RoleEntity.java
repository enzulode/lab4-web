package com.enzulode.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a set of allowed operations. Every role is mapped on several {@link
 * AuthorityEntity}s as a permissions customization improvement.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @Column(name = "role_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "role_code", nullable = false)
    private String code;

    @Column(name = "role_description", nullable = false)
    private String description;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    public List<AuthorityEntity> authorities;
}
