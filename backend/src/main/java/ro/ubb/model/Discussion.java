package ro.ubb.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "discussions")
public class Discussion {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "created_date", insertable = false)
    private Date createdDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "interested_user_id")
    private User interestedUser;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement discussedAnnouncement;

    @ToString.Exclude
    @OneToOne(mappedBy = "discussion")
    private Loan loan;


}
