package com.inhabas.api.boards.domain.usecase;

import com.inhabas.api.boards.domain.entity.board.BoardNotFoundException;
import com.inhabas.api.boards.domain.entity.contest.ContestBoard;
import com.inhabas.api.boards.domain.entity.contest.ContestBoardRepository;
import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.MemberRepository;
import com.inhabas.api.boards.dto.contest.DetailContestBoardDto;
import com.inhabas.api.boards.dto.contest.ListContestBoardDto;
import com.inhabas.api.boards.dto.contest.SaveContestBoardDto;
import com.inhabas.api.boards.dto.contest.UpdateContestBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContestBoardServiceImpl implements ContestBoardService {

    private final ContestBoardRepository contestBoardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Integer write(Integer memberId, SaveContestBoardDto dto) {
        Member writer = memberRepository.getById(memberId);
        ContestBoard contestBoard = ContestBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .association(dto.getAssociation())
                .topic(dto.getTopic())
                .start(dto.getStart())
                .deadline(dto.getDeadline())
                .build()
                        .writtenBy(writer);
        return contestBoardRepository.save(contestBoard).getId();
    }

    @Override
    public Integer update(Integer memberId, UpdateContestBoardDto dto) {
        ContestBoard savedContestBoard = contestBoardRepository.findById(dto.getId())
                .orElseThrow(() -> new BoardNotFoundException());
        Member writer = memberRepository.getById(memberId);
        ContestBoard entity = ContestBoard.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .association(dto.getAssociation())
                .topic(dto.getTopic())
                .start(dto.getStart())
                .deadline(dto.getDeadline())
                .build()
                .writtenBy(writer)
                .inMenu(savedContestBoard.getMenu());
        return contestBoardRepository.save(entity).getId();
    }


    @Override
    public void delete(Integer id) {
        contestBoardRepository.deleteById(id);
    }

    @Override
    public DetailContestBoardDto getBoard(Integer id) {
        return  contestBoardRepository.findDtoById(id)
                .orElseThrow(()-> new BoardNotFoundException());
    }

    @Override
    public Page<ListContestBoardDto> getBoardList(Integer menuId, Pageable pageable) {
        return contestBoardRepository.findAllByMenuId(menuId, pageable);
    }
}
