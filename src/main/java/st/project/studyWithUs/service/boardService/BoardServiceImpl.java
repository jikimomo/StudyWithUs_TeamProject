package st.project.studyWithUs.service.boardService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import st.project.studyWithUs.domain.Board;
import st.project.studyWithUs.repository.BoardRepository;
import st.project.studyWithUs.vo.BoardVO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;


    @Override
    public void saveContent(Board board) {
        boardRepository.save(board);
    }

    @Override
    public List<BoardVO> contentsListInfo() {
        List<Board> boards =  boardRepository.findAll();
        List<BoardVO> bVO = new ArrayList<>();
        for(Board b : boards ){
            BoardVO bvo  = new BoardVO();
            bvo.setBbID(b.getBID());
            bvo.setTitle(b.getTitle());
            bvo.setName(b.getUser().getUserName());
            bvo.setUploadTime(b.getUploadTime());
            bVO.add(bvo);
        }
        return bVO;
    }

    @Override
    public BoardVO findBybID(Long bID) {
        Board b = boardRepository.findBybID(bID);
        BoardVO boardVO = new BoardVO();
        boardVO.setUploadTime(b.getUploadTime());
        boardVO.setContent(b.getContent());
        boardVO.setBbID(b.getBID());
        boardVO.setUID(b.getUser().getUID());
        boardVO.setTitle(b.getTitle());
        boardVO.setName(b.getUser().getUserName());

        return boardVO;
    }

    @Override
    public Board findBybID2(Long bID) {
        return boardRepository.findBybID(bID);
    }

    @Override
    public void delete(Long bID) {
        boardRepository.delete(boardRepository.findBybID(bID));
    }

    @Transactional
    @Override
    public void update(Long bID, String titleID, String contentID) {
        Board b = boardRepository.findBybID(bID);
        b.setTitle(titleID);
        b.setContent(contentID);
        boardRepository.save(b);

    }
}
