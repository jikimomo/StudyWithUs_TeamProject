package st.project.studyWithUs.service.boardService;

import org.springframework.stereotype.Service;
import st.project.studyWithUs.domain.Board;
import st.project.studyWithUs.vo.BoardVO;

import java.util.List;

@Service
public interface BoardService {
    void saveContent(Board board);

    List<BoardVO> contentsListInfo();

    BoardVO findBybID(Long bID);

    Board findBybID2(Long bID);

    void delete(Long bID);

    void update(Long bID, String titleID, String contentID);
}
