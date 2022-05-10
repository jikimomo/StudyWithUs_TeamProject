package st.project.studyWithUs.vo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import st.project.studyWithUs.domain.Comment;
import st.project.studyWithUs.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BoardVO {

    private Long bbID;  // bID로 할 경우 데이터 안넘어옴.
    private String content;
    private String title;
    private LocalDate uploadTime;
    private Long uID;
    private String name;

}

