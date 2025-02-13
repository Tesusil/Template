package com.tesusil.datasource.api.comparators

import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.ModelComparator
import com.tesusil.template.domain.models.User
import javax.inject.Inject

class UserComparator : ModelComparator<User, UserApiModel> {
    override fun compare(data1: User?, data2: UserApiModel?): Boolean {
        if (data1 == null || data2 == null) {
            return false
        }
        return data1.userId == data2.userId
                && data1.userName == data2.userName
                && data1.avatarUrl == data2.avatarUrl
                && data1.dateOfCreation.toString() == data2.dateOfCreation.toString()
    }
}