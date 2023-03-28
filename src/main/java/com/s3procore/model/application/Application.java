package com.s3procore.model.application;

import com.s3procore.model.tenant.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String applicationName;

    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;

    private String subId;

    private String clientId;

    private String clientSecret;

    @Enumerated(EnumType.STRING)
    private ApplicationGrantType applicationGrantType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Tenant tenant;

}
