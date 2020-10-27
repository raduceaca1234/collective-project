package ro.ubb.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.MERGE)
    private Set<Announcement> announcements;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name="id")
    private Wishlist wishlist;

    @ToString.Exclude
    @OneToMany(mappedBy = "interestedUser", orphanRemoval = true, cascade = CascadeType.MERGE)
    private Set<Discussion> discussions;


}
