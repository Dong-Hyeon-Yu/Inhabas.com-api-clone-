package com.inhabas.api.security.domain.wrapper;

import com.inhabas.api.security.domain.socialAccount.type.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProviderTest {

    @DisplayName("Provider 타입에 제목을 저장한다.")
    @Test
    public void Provider_is_OK() {
        //given
        String  providerString = "google";

        //when
        Provider title = new Provider(providerString);

        //then
        assertThat(title.getValue()).isEqualTo("google");
    }

    @DisplayName("Provider 타입에 너무 긴 제목을 저장한다. 30자 초과")
    @Test
    public void Provider_is_too_long() {
        //given
        String okString = "지금이문장은10자임".repeat(3);
        String notOkString = "지금이문장은10자임".repeat(3) + ".";

        //then
        assertDoesNotThrow(() -> new Provider(okString));
        assertThrows(IllegalArgumentException.class,
                () -> new Provider(notOkString));
    }

    @DisplayName("Provider 은 null 일 수 없습니다.")
    @Test
    public void Provider_cannot_be_Null() {
        assertThrows(IllegalArgumentException.class,
                () -> new Provider(null));
    }

    @DisplayName("Provider 은 빈 문자열일 수 없습니다.")
    @Test
    public void Provider_cannot_be_Blank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Provider("\t"));
    }
}
