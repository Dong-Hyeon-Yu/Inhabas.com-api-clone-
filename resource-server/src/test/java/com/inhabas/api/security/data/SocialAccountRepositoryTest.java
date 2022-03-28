package com.inhabas.api.security.data;

import com.inhabas.api.security.domain.authUser.AuthUser;
import com.inhabas.api.security.domain.socialAccount.SocialAccount;
import com.inhabas.api.security.domain.socialAccount.SocialAccountRepository;
import com.inhabas.api.security.domain.socialAccount.type.Provider;
import com.inhabas.api.security.domain.socialAccount.type.UID;
import com.inhabas.testConfig.DefaultDataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DefaultDataJpaTest
public class SocialAccountRepositoryTest {

    @Autowired
    private SocialAccountRepository socialAccountRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("소셜 계정을 uid 와 provider 로 조회한다.")
    public void findBySocialAccountByUidAndProvider() {
        //given
        AuthUser authUser = entityManager.persist(new AuthUser(12171652));
        SocialAccount socialAccount = new SocialAccount(new Provider("google"), new UID("1234"), LocalDateTime.now(), "")
                .connectTo(authUser);
        socialAccountRepository.save(socialAccount);

        //when
        SocialAccount find = socialAccountRepository.findWithAuthUserByUidAndProvider(new UID("1234"), new Provider("google"))
                .orElseThrow(EntityNotFoundException::new);

        //then
        assertThat(find.getAuthUser().getProfileId()).isEqualTo(12171652);
    }
}
