package com.tesusil.datasource.api.mappers

import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.Mapper
import com.tesusil.template.domain.models.User

class ApiUserMapper: Mapper<UserApiModel, User> {
    override fun mapToDomainModel(dataModel: UserApiModel): User {
        return User(
            userName = dataModel.userName,
            avatarUrl = dataModel.avatarUrl,
            userId = dataModel.userId,
            dateOfCreation = dataModel.dateOfCreation
        )
    }

    override fun mapToDataModel(domainModel: User): UserApiModel {
        return UserApiModel(
            userName = domainModel.userName,
            avatarUrl = domainModel.avatarUrl,
            userId = domainModel.userId,
            dateOfCreation = domainModel.dateOfCreation
        )
    }

    override fun mapToDomainModelList(dataModelList: List<UserApiModel>): List<User> {
        return dataModelList.map { mapToDomainModel(it) }
    }

    override fun mapToDataModelList(domainModelList: List<User>): List<UserApiModel> {
        return domainModelList.map { mapToDataModel(it) }
    }
}