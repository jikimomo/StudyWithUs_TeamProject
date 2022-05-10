package st.project.studyWithUs.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import st.project.studyWithUs.argumentresolver.Login;
import st.project.studyWithUs.domain.Comment;
import st.project.studyWithUs.domain.User;
import st.project.studyWithUs.repository.BoardRepository;
import st.project.studyWithUs.service.boardService.BoardService;
import st.project.studyWithUs.service.commentService.CommentService;
import st.project.studyWithUs.vo.CommentVO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;


    @ResponseBody
    @GetMapping("/findComment")
    public List<CommentVO> board(@RequestParam Long bID){

        return commentService.findComment(bID);

    }


    @ResponseBody
    @GetMapping("/saveComment")
    public boolean saveComment(@Login User loginUser, @RequestParam String comment,
                               @RequestParam Long bID){


        if(comment==""){
            return false;
        }

        Comment comment1 = new Comment();
        comment1.setCommentContent(comment);
        comment1.setWriterUID(loginUser.getUID());
        comment1.setBoard(boardService.findBybID2(bID));
        commentService.save(comment1);
        return true;
    }


    @ResponseBody
    @GetMapping("/deleteCommentCheck")
    public boolean deleteCommentCheck(@RequestParam Long bID,
                                      @RequestParam Long cmID,
                                      @Login User loginUser){
        //댓글 주인이거나, 그 글의 글쓴이거나 둘 중 하나 .
        if(loginUser.getUID()==commentService.findBycmID(cmID)||boardService.findBybID2(bID).getUser().getUID()==loginUser.getUID()){
                return true;
        }
        else return false;
    }


    @ResponseBody
    @GetMapping("/deleteComment")
    public boolean deleteComment(@RequestParam Long cmID){

        commentService.delete(cmID);

        return true;
    }

    @ResponseBody
    @GetMapping("/updateComment")
    public boolean updateComment(@RequestParam Long cmID){
        return true;
    }

    @ResponseBody
    @GetMapping("/updateCommentCheck")
    public boolean updateCommentCheck(@RequestParam Long cmID, @Login User loginUser){
        return commentService.updateCommentCheck(cmID, loginUser.getUID());
    }


    @ResponseBody
    @GetMapping("/updateCommentSave")
    public boolean updateCommentSave(@RequestParam Long cmID, @RequestParam String commentContent){

        log.info("--------------{}, {}3", cmID, commentContent);

        commentService.updateCommentSave(cmID, commentContent);
        return true;
    }
}
