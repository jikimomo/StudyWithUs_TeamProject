package st.project.studyWithUs.vo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import st.project.studyWithUs.domain.Board;

import javax.persistence.*;


@Getter
@Setter
public class CommentVO {

    private Long cmID;
    private String writerName;
    private String commentContent;
    private Long bID;
}
