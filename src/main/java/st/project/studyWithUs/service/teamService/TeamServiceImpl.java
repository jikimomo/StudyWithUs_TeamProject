package st.project.studyWithUs.service.teamService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.project.studyWithUs.domain.Team;
import st.project.studyWithUs.domain.User;
import st.project.studyWithUs.domain.UserTeam;
import st.project.studyWithUs.repository.TeamRepository;
import st.project.studyWithUs.repository.UserTeamRepository;
import st.project.studyWithUs.vo.TeamVO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;


    @Override
    public List<Team> findMyTeam(Long uId) {
        return teamRepository.findMyTeam(uId);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }


    @Override
    @Transactional
    public void increaseData(Long tId, Long point) {

        Team t = teamRepository.findBytID(tId);
        t.setCurrentCount(t.getCurrentCount()+1);
        t.setDepositPoint(t.getDepositPoint()+point);
        teamRepository.save(t);
    }

    @Override
    @Transactional
    public void decreaseCurrentCount(Long tId) {
        Team t = teamRepository.findBytID(tId);
        t.setCurrentCount(t.getCurrentCount()-1);
        teamRepository.save(t);
    }

    @Override
    public Team findBytID(Long tId) {
        return teamRepository.findBytID(tId);
    }

    @Override
    public void deleteTeam(Long tID) {
        teamRepository.deleteById(tID);
    }

    @Override
    public List<TeamVO> searchTeam(String teamName, User user) {
        //비회원일 때 검색
        if(user==null){
            List <Team> teams = teamRepository.findAll();
            List <TeamVO> serachTeamList = new ArrayList<>();
            for(Team t : teams){
                if(t.getTeamName().contains(teamName)&&t.getHeadCount()!=t.getCurrentCount()){
                    serachTeamList.add(create(t));
                }
            }
            return serachTeamList;
        }
        //회원일 때 검색
        else{
            List<Team> te;
            te = userTeamRepository.findUserTeam(user.getUID());
            List <Team> teams = teamRepository.findAll();
            List <TeamVO> serachTeamList = new ArrayList<>();

            for(Team t : teams){
                boolean check1 =true;
                if(t.getCurrentCount()==t.getHeadCount())continue;
                for(Team ut : te){
                    if(ut.getTID()==t.getTID()){
                        //현재 속한 스터디거나, 스터디 인원이 꽉 차있으면.
                        check1=false;
                        break;
                    }
                }
                if(check1==true){
                    if(t.getTeamName().contains(teamName)){
                        serachTeamList.add(create(t));
                    }
                }
            }
            return serachTeamList;
        }
    }

    @Override
    public List<TeamVO> findAllTeams(User user) {
        //비회원일때
        if(user ==null){
            List<Team> findTeams = teamRepository.findTeams();
            List<TeamVO> teamVO =  new ArrayList<>();
            for(Team t : findTeams){
                teamVO.add(create(t));
            }
            return teamVO;
        }
        //회원일 때
        else{
            List<Team> teams = teamRepository.findAll();
            List<Team> te;
            te = userTeamRepository.findUserTeam(user.getUID());
            List<TeamVO> teamVO =  new ArrayList<>();
            for(Team t : teams){
                boolean check1 =true;
                if(t.getCurrentCount()==t.getHeadCount())continue;
                for(Team ut : te){
                    if(ut.getTID()==t.getTID()){
                        //현재 속한 스터디거나, 스터디 인원이 꽉 차있으면.
                        check1=false;
                        break;
                    }
                }
                if(check1==true){
                    teamVO.add(create(t));
                }
            }
            return teamVO;
        }
    }

    @Override
    public List<TeamVO> findSearchTeam(String teamName) {
        List <Team> teams = teamRepository.findAll();
        List <TeamVO> searchTeamList = new ArrayList<>();
        for(Team t : teams){
            if(t.getTeamName().contains(teamName)){
                searchTeamList.add(create(t));
            }
        }
        return searchTeamList;
    }

    public TeamVO create(Team t){
        TeamVO tVO = new TeamVO();
        tVO.setTtID(t.getTID());
        tVO.setTeamName(t.getTeamName());
        tVO.setTeamDesc(t.getTeamDesc());
        tVO.setDepositPoint(t.getDepositPoint());
        tVO.setTotalDepositPoint(t.getTotalDepositPoint());
        tVO.setHeadCount(t.getHeadCount());
        tVO.setCurrentCount(t.getCurrentCount());
        tVO.setEndDate(t.getEndDate());
        tVO.setStartDate(t.getStartDate());
        tVO.setTeamImage(t.getTeamImage());
        return tVO;
    }
}


