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
@Table(name = "closed_loans")
public class ClosedLoan {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "closedLoan")
    private Loan loan;

    @Column(name = "close_date", insertable = false)
    private Date closeDate;
}
