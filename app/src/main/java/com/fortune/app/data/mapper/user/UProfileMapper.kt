package com.fortune.app.data.mapper.user

import com.fortune.app.data.entities.user.UProfileEntity
import com.fortune.app.domain.model.user.UProfileModel

object UProfileMapper {
    fun mapToDomain(uProfileEntity: UProfileEntity): UProfileModel {
        return UProfileModel(
            id = uProfileEntity.id,
            online = uProfileEntity.online,
            userId = uProfileEntity.user_id,
            name = uProfileEntity.name,
            address = uProfileEntity.address,
            phone = uProfileEntity.phone,
            pfp = uProfileEntity.profilePicture
        )
    }

    fun mapToEntity(uProfileModel: UProfileModel): UProfileEntity {
        return UProfileEntity(
            id = uProfileModel.id,
            online = uProfileModel.online,
            user_id = uProfileModel.userId,
            name = uProfileModel.name,
            address = uProfileModel.address,
            phone = uProfileModel.phone,
            profilePicture = uProfileModel.pfp
        )
    }
}