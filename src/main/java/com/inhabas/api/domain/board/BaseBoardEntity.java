package com.inhabas.api.domain.board;

import com.inhabas.api.domain.BaseEntity;
import com.inhabas.api.domain.File;
import com.inhabas.api.domain.comment.Comment;
import com.inhabas.api.domain.member.Member;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "base_board")
@Getter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class BaseBoardEntity extends BaseEntity {
    @Id @GeneratedValue
    protected Integer id;

    protected String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String contents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Member writer;

    @OneToMany(mappedBy = "board")
    protected List<Comment> comments;

//    @OneToMany(mappedBy = "board")
//    protected Set<File> files = new HashSet<>();
}
