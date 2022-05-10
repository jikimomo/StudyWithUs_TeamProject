package st.project.studyWithUs.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    // 커밋 테스트 중입니다~!!!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmID;

    @NotNull
    private Long writerUID;

    @NotNull
    private String commentContent;

    //eunji
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bID")
    private Board board;
}

