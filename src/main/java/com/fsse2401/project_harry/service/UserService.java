package com.fsse2401.project_harry.service;

import com.fsse2401.project_harry.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_harry.data.user.entity.UserEntity;

public interface UserService {
    UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData);
}
