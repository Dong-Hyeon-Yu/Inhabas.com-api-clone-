package com.inhabas.security.annotataion;

import com.inhabas.api.domain.member.type.MemberType;
import com.inhabas.api.security.domain.authUser.AuthUserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Jwt 인증 결과를 securityContext 에 담아두기 위한 test 용 annotation.
 * Jwt 인증 후의 어떤 특정한 상황을 Mocking 하고 싶을 때 사용한다.<br>
 * - memberId 가 default 이면 AuthUser 와 매핑되는 Member 는 null 이다.
 * @see WithMockJwtAuthenticationTokenSecurityContextFactory
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtAuthenticationTokenSecurityContextFactory.class)
public @interface WithMockJwtAuthenticationToken {

    int authUserId() default 1;

    String email() default "my@email.com";

    String provider() default "google";

    int memberId() default 0; // 다른값으로 설정되지 않으면, authUser 의 member profile 을 null 로 간주.

    String memberName() default "홍길동";

    int memberGeneration() default 1;

    String memberMajor() default "의예과";

    String memberPhone() default "010-1234-5678";

    AuthUserRole memberRole() default AuthUserRole.BASIC_MEMBER;

    MemberType memberType() default MemberType.UNDERGRADUATE;
}
