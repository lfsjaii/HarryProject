package com.fsse2401.project_harry.service.impl;

import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import com.fsse2401.project_harry.repository.UserRepository;
import com.fsse2401.project_harry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData) {
        //LV2
//        Optional<UserEntity> userEntityOptional = userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
//        if(userEntityOptional.isEmpty()) {
//            UserEntity userEntity = new UserEntity(firebaseUserData);
//            return userRepository.save(userEntity);
//        } else {
//            return userEntityOptional.get();
//        }
        //LV3
        return userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid()).orElseGet(() -> userRepository.save(new UserEntity(firebaseUserData)));
    }
}
