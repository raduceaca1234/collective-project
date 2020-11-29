package ro.ubb.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "wishlists")
public class Wishlist {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "wishlist")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.MERGE,
                    },
            targetEntity = Announcement.class)
    @JoinTable(name = "wishlist_items",
            joinColumns = @JoinColumn(name = "wishlist_id",
                    nullable = false,
                    updatable = false),
            inverseJoinColumns = @JoinColumn(name = "announcement_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Announcement> wantedAnnouncements;

    public Wishlist(User user) {
        this.owner = user;
        this.wantedAnnouncements = new HashSet<>();
    }
}
