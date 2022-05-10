package st.project.studyWithUs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import st.project.studyWithUs.domain.Board;
import st.project.studyWithUs.domain.Comment;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findBycmID(Long cmID);
}
