package com.s3procore.model.user;

import com.s3procore.model.Company;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Locale lang;

    private LocalDateTime createdDate;

    private String zitadelUserId;

    private String zitadelResourceOwner;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

}
