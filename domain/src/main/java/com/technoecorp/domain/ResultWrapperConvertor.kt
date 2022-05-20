package com.technoecorp.domain

import retrofit2.Response

class ResultWrapperConvertor {
    companion object{
        fun <T> responseToResource(response: Response<T>): ResultWrapper<T> {
            if (response.isSuccessful) {
                response.body()?.let {
                    return ResultWrapper.Success(it)
                }
            }
            return ResultWrapper.Error(response.message())
        }

    }
}