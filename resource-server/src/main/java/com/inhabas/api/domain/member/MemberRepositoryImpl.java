package com.inhabas.api.domain.member;

import com.inhabas.api.domain.member.type.wrapper.Phone;
import com.inhabas.api.domain.member.type.wrapper.Role;
import com.inhabas.api.dto.signUp.MemberDuplicationQueryCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.inhabas.api.domain.member.QMember.member;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isDuplicated(MemberDuplicationQueryCondition condition) {

        condition.verityAtLeastOneParameter();

        return !queryFactory.selectFrom(QMember.member)
                .where(eqAny(condition))
                .limit(1).fetch().isEmpty();
    }

    @Override
    public List<Member> searchAllByRole(Role role) {

        return queryFactory.select(member)
                .from(member)
                .where(eqRole(role))
                .fetch();
    }

    @Override
    public List<Member> searchByRoleLimit(Role role, Integer limit) {

        return queryFactory.select(member)
                .from(member)
                .where(eqRole(role))
                .limit(limit)
                .fetch();
    }

    private BooleanExpression eqRole(Role role) {
        return member.ibasInformation.role.eq(role);
    }

    private BooleanBuilder eqAny(MemberDuplicationQueryCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        return booleanBuilder.or(eqId(condition.getMemberId()))
                .or(eqPhone(condition.getPhone()));
    }

    private BooleanExpression eqId(Integer id) {
        return Objects.isNull(id) ? null : member.id.eq(id);
    }

    private BooleanExpression eqPhone(Phone phoneNumber) {
        return Objects.isNull(phoneNumber) ? null : member.phone.eq(phoneNumber);
    }
}
