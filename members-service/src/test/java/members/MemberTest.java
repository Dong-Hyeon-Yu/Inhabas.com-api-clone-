package members;

import com.inhabas.api.members.domain.entity.member.type.wrapper.IbasInformation;
import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.type.wrapper.SchoolInformation;
import com.inhabas.api.members.domain.entity.member.type.wrapper.Role;

public class MemberTest {
    public static final Member MEMBER1 = new Member(
            12171234, "유동현", "010-1111-1111", ""
            , SchoolInformation.ofStudent("건축공학과", 3, 1)
            , new IbasInformation(Role.BASIC_MEMBER, "hello", 0));

    public static final Member MEMBER2 = new Member(
            12114321, "김민겸", "010-2222-2222", ""
            , SchoolInformation.ofStudent("경영학과", 2, 2)
            , new IbasInformation(Role.BASIC_MEMBER, "hi", 0));

    public static Member getTestMember(Integer id) {
        return new Member(
                id, "유동현", "010-1111-1111", ""
                , SchoolInformation.ofStudent("건축공학과", 3, 1)
                , new IbasInformation(Role.BASIC_MEMBER, "hello", 0));
    }


}
