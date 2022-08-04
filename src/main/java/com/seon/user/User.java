package com.seon.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user")
@Entity
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String firstName;
    private String surname;
    private String dob;
    private String jobTitle;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
