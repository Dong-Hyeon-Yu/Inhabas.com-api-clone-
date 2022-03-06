package boards.domain.entity.contest;

import boards.domain.entity.board.NormalBoard;
import boards.domain.entity.board.type.wrapper.Contents;
import boards.domain.entity.board.type.wrapper.Title;
import boards.domain.entity.contest.type.wrapper.Association;
import boards.domain.entity.contest.type.wrapper.Topic;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import members.domain.entity.member.Member;
import menus.domain.entity.Menu;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contest_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContestBoard extends NormalBoard {

    @Embedded
    private Topic topic;

    @Embedded
    private Association association;

    @Column
    private LocalDate start;

    @Column
    private LocalDate deadline;

    /* Getter */
    public String getAssociation(){
        return association.getValue();
    }

    public String getTopic(){
        return topic.getValue();
    }

    /* Constructor */

    public ContestBoard(String title, String contents, String association, String topic, LocalDate start, LocalDate deadline){
        this.title = new Title(title);
        this.contents = new Contents(contents);
        this.association = new Association(association);
        this.topic = new Topic(topic);
        this.start = start;
        this.deadline = deadline;
    }

    @Builder
    public ContestBoard(Integer id, String title, String contents,String association, String topic, LocalDate start , LocalDate deadline){
        super(id, title, contents);
        this.association = new Association(association);
        this.topic = new Topic(topic);
        this.start = start;
        this.deadline =deadline;
    }

    /* relation method */


    public ContestBoard writtenBy(Member writer){
        super._writtenBy(writer);
        return this;
    }

    public ContestBoard inMenu(Menu menu){
        super._inMenu(menu);
        return this;
    }
}
