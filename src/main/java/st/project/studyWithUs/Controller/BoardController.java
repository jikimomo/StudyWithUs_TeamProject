package st.project.studyWithUs.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import st.project.studyWithUs.argumentresolver.Login;
import st.project.studyWithUs.domain.Board;
import st.project.studyWithUs.domain.User;
import st.project.studyWithUs.service.boardService.BoardService;
import st.project.studyWithUs.vo.BoardVO;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {


    private final BoardService boardservice;


    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @ResponseBody
    @GetMapping("/saveContent")
    public boolean saveContent(@RequestParam String title, @RequestParam String content,
                               @Login User loginUser) {

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUser(loginUser);
        board.setUploadTime(LocalDate.now());

        boardservice.saveContent(board);
        return true;
    }


    @ResponseBody
    @GetMapping("/contentsListInfo")
    public List<BoardVO> contentsListInfo() {
        return boardservice.contentsListInfo();
    }


    @GetMapping("/detailContent/{bID}")
    public String detailContent(@PathVariable("bID") Long bID) {

        return "detailContent";
    }

    @ResponseBody
    @GetMapping("/detailContent")
    public BoardVO detailContent2(@RequestParam Long bID) {

        return boardservice.findBybID(bID);

    }


    @ResponseBody
    @GetMapping("/deleteContent")
    public boolean deleteContent(@RequestParam Long bID, @Login User loginUser) {

        if (boardservice.findBybID2(bID).getUser().getUID() == loginUser.getUID()) return true;
        else return false;
    }

    @ResponseBody
    @GetMapping("/deleteBoard")
    public boolean deleteBoard(@RequestParam Long bID) {

        boardservice.delete(bID);
        return true;
    }

    @ResponseBody
    @GetMapping("/updateContent")
    public boolean updateContent(@RequestParam Long bID,
                                 @RequestParam String ti,
                                 @RequestParam String co){

        log.info("ti co {}, {}", ti, co);

        boardservice.update(bID, ti, co);
        return true;
    }
}
