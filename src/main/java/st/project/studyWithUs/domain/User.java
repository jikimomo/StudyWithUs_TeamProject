package st.project.studyWithUs.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    //test
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uID;

    @NotNull
    private String userID;

    @NotNull
    private String password;

    @NotNull
    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String role;

    private String userImage;

    private Long point;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTeam> userTeams = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> contents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefundUserAccount> refundUserAccounts = new ArrayList<>();
}
