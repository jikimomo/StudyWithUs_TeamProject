package st.project.studyWithUs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import st.project.studyWithUs.domain.UserTeam;

@Repository
public interface StudyingRepository extends JpaRepository<UserTeam, Long> {

    @Query("select ut from UserTeam ut where ut.user.uID = :uID and ut.team.tID = :tID")
    UserTeam findByuIDandtID(@Param("uID") Long uID, @Param("tID") Long tID);



}
