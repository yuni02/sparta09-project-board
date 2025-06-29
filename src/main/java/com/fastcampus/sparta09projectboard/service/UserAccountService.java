package com.fastcampus.sparta09projectboard.service;

import com.fastcampus.sparta09projectboard.dto.UserAccountDto;
import com.fastcampus.sparta09projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {
    
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
    public boolean isUserIdExists(String userId) {
        return userAccountRepository.existsById(userId);
    }
    
    public UserAccountDto saveUser(UserAccountDto dto) {
        if (isUserIdExists(dto.userId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다: " + dto.userId());
        }
        
        // 비밀번호 암호화
        UserAccountDto encodedDto = UserAccountDto.of(
                dto.userId(),
                passwordEncoder.encode(dto.userPassword()),
                dto.email(),
                dto.nickname()
        );
        
        return UserAccountDto.from(
                userAccountRepository.save(encodedDto.toEntity())
        );
    }
} 