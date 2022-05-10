package st.project.studyWithUs.service.commentService;

import org.springframework.stereotype.Service;
import st.project.studyWithUs.argumentresolver.Login;
import st.project.studyWithUs.domain.Comment;
import st.project.studyWithUs.vo.CommentVO;

import java.util.List;



@Service
public interface CommentService {
    List<CommentVO> findComment(Long bID);

    void save(Comment comment1);

    Long findBycmID(Long cmID);

    void delete(Long cmID);

    boolean updateCommentCheck(Long cmID, Long uid);

    void updateCommentSave(Long cmID, String commentContent);
}
