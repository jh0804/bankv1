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
}
