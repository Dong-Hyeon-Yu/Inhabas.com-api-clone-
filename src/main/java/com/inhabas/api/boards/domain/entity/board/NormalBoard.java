package com.inhabas.api.boards.domain.entity.board;

import com.inhabas.api.boards.domain.entity.board.type.wrapper.Contents;
import com.inhabas.api.boards.domain.entity.board.type.wrapper.Title;
import com.inhabas.api.boards.domain.entity.comment.Comment;
import com.inhabas.api.common.BaseEntity;
import com.inhabas.api.boards.domain.entity.file.BoardFile;
import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.menus.domain.entity.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "normal_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", length = 15)
public class NormalBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Embedded
    protected Title title;

    @Embedded
    protected Contents contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", foreignKey = @ForeignKey(name = "fk_board_to_menu"))
    protected Menu menu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "fk_board_to_user"))
    protected Member writer;

    @OneToMany(mappedBy = "parentBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "parentBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<BoardFile> files = new HashSet<>();

    /* constructor */

    public NormalBoard(Integer id, String title, String contents) {
        this.id = id;
        this.title = new Title(title);
        this.contents = new Contents(contents);
    }

    public NormalBoard(String title, String contents) {
        this.title = new Title(title);
        this.contents = new Contents(contents);
    }


    /* getter */

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContents() {
        return contents.getValue();
    }

    public Menu getMenu() {
        return menu;
    }

    public Member getWriter() {
        return writer;
    }

    public Set<BoardFile> getFiles() {
        return Collections.unmodifiableSet(files);
    }

    /* relation method */

    protected void _writtenBy(Member writer){
        if (Objects.isNull(this.writer))
            this.writer = writer;
        else
            throw new IllegalStateException("게시글 작성자를 수정할 수 없습니다.");
    }

    public NormalBoard writtenBy(Member writer){
        this._writtenBy(writer);
        return this;
    }

    public NormalBoard addFiles(Set<BoardFile> UploadFiles) {
        if (Objects.nonNull(UploadFiles))
            UploadFiles.forEach(this::addFile);

        return this;
    }

    public boolean isWriter(Member member) {
        return this.writer.equals(member);
    }

    public void addFile(BoardFile uploadFile) {
        files.add(uploadFile);
        uploadFile.toBoard(this);
    }

    public void addComment(Comment newComment) {
        comments.add(newComment);
    }

    protected void _inMenu(Menu menu){
        this.menu = menu;
    }

    public NormalBoard inMenu(Menu menu) {
        _inMenu(menu);
        return this;
    }
}