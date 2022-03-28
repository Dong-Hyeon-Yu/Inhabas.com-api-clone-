package com.inhabas.api.domain.member.type;

import com.inhabas.api.domain.member.MemberTeam;
import com.inhabas.api.domain.member.type.wrapper.Introduce;
import com.inhabas.api.security.domain.authUser.AuthUserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
@Getter
public class IbasInformation {

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private List<MemberTeam> teamList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime joined;

    @Embedded
    private Introduce introduce;

    @Column(name = "USER_APPLY_PUBLISH", nullable = false)
    private Integer applyPublish;

    public IbasInformation() {
        this.introduce = new Introduce();
        this.applyPublish = 0;
    }

    public String getIntroduce() {
        return introduce.getValue();
    }

    public List<MemberTeam> getTeamList() {
        return Collections.unmodifiableList(teamList);
    }

    public void addTeam(MemberTeam team) {
        this.teamList.add(team);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(IbasInformation.class.isAssignableFrom(o.getClass()))) return false;
        IbasInformation that = (IbasInformation) o;
        return getJoined().equals(that.getJoined())
                && getIntroduce().equals(that.getIntroduce())
                && getApplyPublish().equals(that.getApplyPublish())
                && getTeamList().equals(that.getTeamList());
    }
}