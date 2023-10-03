package com.santechture.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Token")
@Entity
public class Token {

    @GeneratedValue
    @Id
    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    private String token;
    private Boolean expired;
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
    private Admin admin;
}
