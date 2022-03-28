package com.inhabas.api.security.domain.authUser;

/**
 * 수직적 권한 계층은 Role 에 의해서 결정됨.
 */
public enum AuthUserRole {
    ADMIN, // 사이트 관리자
    CHIEF, // 회장
    EXECUTIVES, // 회장단
    BASIC_MEMBER, // 일반회원
    DEACTIVATED_MEMBER, // 비활동회원
    NOT_APPROVED_MEMBER, // 가입 후 아직 승인되지 않은 회원
    ANONYMOUS // 익명.
}

