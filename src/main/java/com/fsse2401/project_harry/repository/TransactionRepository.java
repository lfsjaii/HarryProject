package com.fsse2401.project_harry.repository;

import com.fsse2401.project_harry.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project_harry.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_harry.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    Optional<TransactionEntity> findByTidAndUser(Integer tid, UserEntity userEntity);
}
