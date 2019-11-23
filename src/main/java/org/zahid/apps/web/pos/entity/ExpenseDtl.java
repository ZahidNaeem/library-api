package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the XXIM_EXPENSE_DTL database table.
 */
@Entity
@Table(name = "XXIM_EXPENSE_DTL")
@JsonIdentityInfo(scope = ExpenseDtl.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "expDtlId")
@NamedQuery(name = "ExpenseDtl.findAll", query = "SELECT e FROM ExpenseDtl e")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDtl extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
/*    @SequenceGenerator(name = "XXIM_EXPENSE_DTL_EXPDTLID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_EXPENSE_DTL_EXPDTLID_GENERATOR")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EXP_DTL_ID")
    private Long expDtlId;

    @Column(name = "EXP_DTL_AMT")
    private BigDecimal expDtlAmt;

    @Column(name = "EXP_OTHER_TYPE_DESC")
    private String expOtherTypeDesc;

    @Column(name = "EXP_TYPE")
    private String expType;

    private String remarks;

    // bi-directional many-to-one association to ExpenseMain
    @ManyToOne
    @JoinColumn(name = "EXP_MAIN_ID")
    private ExpenseMain expenseMain;
}
