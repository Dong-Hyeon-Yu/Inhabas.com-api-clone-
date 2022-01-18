package com.inhabas.api.domain;

import com.inhabas.api.config.JpaConfig;
import com.inhabas.api.domain.board.Category;
import com.inhabas.api.domain.board.NormalBoard;
import com.inhabas.api.domain.file.BaseFile;
import com.inhabas.api.domain.file.BoardFile;
import com.inhabas.api.domain.file.BoardFileRepository;
import com.inhabas.api.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@Import(JpaConfig.class)
public class BoardFileRepositoryTest {

    @Autowired BoardFileRepository boardFileRepository;
    @Autowired TestEntityManager em;

    Member writer;
    NormalBoard board;

    @BeforeEach
    public void setUp() {
        writer = em.persist(MemberTest.MEMBER1);
        board = NormalBoardTest.getFreeBoard()
                .writtenBy(writer)
                .inCategoryOf(em.getEntityManager().getReference(Category.class, 2));
        board = em.persist(board);

        em.flush();
        em.clear();
    }

    @DisplayName("파일 정보를 db 저장하는데 성공한다.")
    @Test
    public void saveFileInfoIntoDB() {
        //given
        BoardFile file1 =
                new BoardFile("100일 기념 사진.jpg", "550e8400-e29b-41d4-a716-446655440000.jpg")
                        .toBoard(board);
        BoardFile file2 =
                new BoardFile( "커플 증빙서류.pdf", "999a5928-c49b-65d9-7bd6-446655441234.pdf")
                        .toBoard(board);
        BoardFile file3 =
                new BoardFile("몰래 준비한 고소장.zip", "12345678-fa9c-539a-7a1c-f92c1200c000.zip")
                        .toBoard(board);
        List<BoardFile> files = List.of(new BoardFile[]{file1, file2, file3});

        //when
        Iterable<BoardFile> savedFiles = boardFileRepository.saveAll(files);


        //then
        assertThat(savedFiles)
                .hasSize(3)
                .extracting( BaseFile::getUploadName, BaseFile::getFilename)
                .contains(
                        tuple("100일 기념 사진.jpg", "550e8400-e29b-41d4-a716-446655440000.jpg"),
                        tuple("커플 증빙서류.pdf", "999a5928-c49b-65d9-7bd6-446655441234.pdf"),
                        tuple("몰래 준비한 고소장.zip", "12345678-fa9c-539a-7a1c-f92c1200c000.zip")
                );
    }

    @DisplayName("파일 정보를 불러오는데 성공한다.")
    @Test
    public void getFileInfoFromDB() {
        //given
        BoardFile file1 =
                new BoardFile("100일 기념 사진.jpg", "550e8400-e29b-41d4-a716-446655440000.jpg")
                        .toBoard(board);
        BoardFile file2 =
                new BoardFile( "커플 증빙서류.pdf", "999a5928-c49b-65d9-7bd6-446655441234.pdf")
                        .toBoard(board);
        BoardFile file3 =
                new BoardFile("몰래 준비한 고소장.zip", "12345678-fa9c-539a-7a1c-f92c1200c000.zip")
                        .toBoard(board);
        List<BoardFile> files = List.of(new BoardFile[]{file1, file2, file3});
        boardFileRepository.saveAll(files);

        em.flush();
        em.clear();


        //when
        List<BoardFile> savedFiles = boardFileRepository.findAllByParentBoard(board);


        //then
        assertThat(savedFiles)
                .hasSize(3)
                .extracting( BaseFile::getUploadName, BaseFile::getFilename)
                .contains(
                        tuple("100일 기념 사진.jpg", "550e8400-e29b-41d4-a716-446655440000.jpg"),
                        tuple("커플 증빙서류.pdf", "999a5928-c49b-65d9-7bd6-446655441234.pdf"),
                        tuple("몰래 준비한 고소장.zip", "12345678-fa9c-539a-7a1c-f92c1200c000.zip")
                );
    }
}
