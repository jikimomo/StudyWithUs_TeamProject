package st.project.studyWithUs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import st.project.studyWithUs.domain.Board;

import javax.transaction.Transactional;
@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {


    Board findBybID(Long bID);
}


