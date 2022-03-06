package members.domain.usecase;

import members.domain.entity.member.Member;
import members.domain.entity.member.type.wrapper.Phone;
import security.domain.Role;
import members.dto.DetailSignUpDto;
import members.dto.ProfessorSignUpDto;
import members.dto.StudentSignUpDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member saveSignUpForm(StudentSignUpDto signUpForm);

    Member saveSignUpForm(ProfessorSignUpDto signUpForm);

    DetailSignUpDto loadSignUpForm(Integer memberId, String email);

    List<Member> findMembers();

    Member findById(Integer memberId);

    Optional<Member> updateMember(Member member);

    void changeRole(Integer memberId, Role role);

    boolean isDuplicatedId(Integer memberId);

    boolean isDuplicatedPhoneNumber(Phone phoneNumber);
}
