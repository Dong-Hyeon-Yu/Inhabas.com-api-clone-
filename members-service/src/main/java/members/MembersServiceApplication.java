package members;

import config.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableSecurity 이건 안됨. 알아봐야함.
@ComponentScan(basePackages = "security")  // 이렇게 하면 회원 서비스 테스트 할 때, 인증인가를 분리하는 작업을 또 해줘야 하는데,
// 중앙에서 따로 해주면 회원 서비스는 거기에 영향을 받지 않는다. 즉 각 서비스를 빈처럼 등록해서 전체서비스에 등록하는 형태!
// oauth2 인증 관련 application.yml 파일도 마찬가지
public class MembersServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MembersServiceApplication.class, args);
    }
}
