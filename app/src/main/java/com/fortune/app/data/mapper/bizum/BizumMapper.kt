package com.fortune.app.data.mapper.bizum

import com.fortune.app.data.entities.bizum.BizumEntity
import com.fortune.app.domain.model.bizum.BizumModel

object BizumMapper {
    fun mapToDomain(bizumEntity: BizumEntity): BizumModel {
        return BizumModel(
            amount = bizumEntity.amount,
            date = bizumEntity.date,
            description = bizumEntity.description,
            from = bizumEntity.from,
            to = bizumEntity.to
        )
    }

    fun mapToEntity(bizumModel: BizumModel): BizumEntity {
        return BizumEntity(
            amount = bizumModel.amount,
            date = bizumModel.date,
            description = bizumModel.description,
            from = bizumModel.from,
            to = bizumModel.to
        )
    }
}
