package com.metacoding.bankv1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
// 기능을 정리하면 여기에 전부 다 있다.
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저 네임 있는지 검사
        User user = userRepository.findByUsername(joinDTO.getUsername());
        // 2. 있으면, exception 터트리기 (오류는 그 자리에서 터트려야 한다. 리턴X -> 따로 잡아채서 처리)
        if (user != null) throw new RuntimeException("동일한 username이 존재합니다.");
        // 3. 없으면 회원가입하기
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }

    // 클라이언트 측에는 id/pw 뭐 틀렸는지 알려줄 필요X
    // 서버는 id/pw 둘 중 뭐가 틀렸는지 알아야 한다.
    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 필터링 하는 방식으로
        // 1. 동일 유저 네임 있는지 검사
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 필터링 (유저네임, 패스워드가 불일치하는 것들을)
        // 2-1. 해당 username이 없을 경우
        if (user == null) {
            throw new RuntimeException("해당 username이 없습니다.");
        }

        // 2-2. 유저가 있으면 pw 비교 (pw가 일치하지 않는다면)
        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
            throw new RuntimeException("pw가 틀렸습니다.");
        }

        // 3. 인증
        return user;
    }
}
