package com.fortune.app.data.mapper.user

import com.fortune.app.data.entities.user.UserEntity
import com.fortune.app.domain.model.user.UserModel

object UserMapper {
    fun mapToDomain(userEntity: UserEntity): UserModel {
        return UserModel(
            id = userEntity.id,
            identityDocument = userEntity.identity_document,
            email = userEntity.email,
            digitalSign = userEntity.digitalSign,
            is_profile_created = userEntity.isProfileCreated
        )
    }

    fun mapToEntity(userModel: UserModel): UserEntity {
        return UserEntity(
            id = userModel.id,
            identity_document = userModel.identityDocument,
            email = userModel.email,
            digitalSign = userModel.digitalSign,
            isProfileCreated = userModel.is_profile_created
        )
    }
}