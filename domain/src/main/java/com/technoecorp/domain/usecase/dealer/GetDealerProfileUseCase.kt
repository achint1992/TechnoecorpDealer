package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.repository.IDealerRepository

class GetDealerProfileUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute() {
        //dealerRepository.getDealerProfile()
    }
}