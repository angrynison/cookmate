package member.service;

import member.dto.JoinRequest;
import member.dto.JwtResponse;
import member.dto.LoginRequest;

public interface MemberService {

    public Long join(JoinRequest joinRequest);
    public JwtResponse login(LoginRequest loginRequest);

}
