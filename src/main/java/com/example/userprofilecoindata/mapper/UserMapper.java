package com.example.userprofilecoindata.mapper;

import com.example.userprofilecoindata.entity.UserEntity;
import com.example.userprofilecoindata.model.UserRequest;
import com.example.userprofilecoindata.model.UserResponse;
import com.example.userprofilecoindata.model.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    UserResponse entityToModel(UserEntity userEntity);
    UserEntity modelToEntity(UserRequest userRequest);
    UserEntity ToEntity(UserUpdateRequest userUpdateRequest);


}