package st.project.studyWithUs.service.commentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import st.project.studyWithUs.domain.Comment;
import st.project.studyWithUs.repository.CommentRepository;
import st.project.studyWithUs.repository.UserRepository;
import st.project.studyWithUs.vo.CommentVO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentVO> findComment(Long bID) {
        List<Comment> comments = commentRepository.findAll();
        List<CommentVO> cvos = new ArrayList<>();
        for(Comment c : comments){
            if(c.getBoard().getBID()==bID){
                CommentVO commentVO = new CommentVO();
                commentVO.setCommentContent(c.getCommentContent());
                commentVO.setCmID(c.getCmID());
                commentVO.setBID(c.getBoard().getBID());
                commentVO.setWriterName(userRepository.findByuID(c.getWriterUID()).getUserName());
                cvos.add(commentVO);
            }
        }

        return cvos;
    }

    @Override
    public void save(Comment comment1) {
        commentRepository.save(comment1);
    }

    @Override
    public Long findBycmID(Long cmID) {
        return commentRepository.findBycmID(cmID).getWriterUID();
    }

    @Override
    public void delete(Long cmID) {
        commentRepository.delete(commentRepository.findBycmID(cmID));
    }

    @Override
    public boolean updateCommentCheck(Long cmID, Long uid) {
        if(commentRepository.findBycmID(cmID).getWriterUID()==uid){
            return true;
        }
        else return false;
    }




    @Transactional
    @Override
    public void updateCommentSave(Long cmID, String commentContent) {
        Comment c = commentRepository.findBycmID(cmID);
        c.setCommentContent(commentContent);
        commentRepository.save(c);
    }
}
