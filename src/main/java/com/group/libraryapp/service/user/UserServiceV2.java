package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 저장
    @Transactional
    public void saveUser(UserCreateRequest request){
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    // 유저 조회
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream()
                //.map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    // 유저 업데이트
    @Transactional
    public void updateUser(UserUpdateRequest request){
        User user = userRepository.findById(request.getId()).orElseThrow(IllegalArgumentException::new);
        user.updateName(request.getName());
        // userRepository.save(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
//        if (user == null) {
//            throw new IllegalArgumentException("User not found");
//        }
        userRepository.delete(user);
    }
}
