package com.inhabas.api.domain.budget.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inhabas.api.domain.budget.domain.BudgetHistory;
import com.inhabas.api.domain.member.domain.valueObject.MemberId;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetHistoryCreateForm {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Past @NotNull
    private LocalDateTime dateUsed;

    @NotBlank
    private String title;

    private String details;

    @NotNull
    private MemberId personReceived;

    @PositiveOrZero @NotNull
    private Integer income;

    @PositiveOrZero @NotNull
    private Integer outcome;

    public BudgetHistoryCreateForm(LocalDateTime dateUsed, String title, String details,
            @NotNull Integer personReceived, Integer income, Integer outcome) {
        this.dateUsed = dateUsed;
        this.title = title;
        this.details = details;
        this.personReceived = new MemberId(personReceived);
        this.income = income;
        this.outcome = outcome;

        if (this.details.isBlank())
            this.details = this.title;
    }

    public BudgetHistory toEntity(MemberId CFO) {
        return BudgetHistory.builder()
                .title(this.title)
                .details(this.details)
                .income(this.income)
                .outcome(this.outcome)
                .dateUsed(this.dateUsed)
                .personReceived(this.getPersonReceived())
                .personInCharge(CFO)
                .build();
    }
}
