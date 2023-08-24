package com.devyash.basicsindepthpractice.models

class UserMapper {

    fun fromNetworkToUserMapper(networkData: NetworkData):User{
        return User(networkData.name,networkData.age,networkData.haveLicense)
    }
}