package dmitriy.losev.cs.handlers

import dmitriy.losev.cs.dso.charm.CharmFloatDSO
import dmitriy.losev.cs.dso.charm.CharmInfoDSO
import dmitriy.losev.cs.dso.charm.CharmLinkDSO
import dmitriy.losev.cs.dso.charm.CharmSaleOfferDSO
import dmitriy.losev.cs.dto.charm.CharmLinkDTO

interface CharmHandler {

    suspend fun insertCharmInfo(charmInfo: CharmInfoDSO): String?

    suspend fun getCharmsInfo(delay: Long, count: Int): List<CharmInfoDSO>

    suspend fun checkCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDSO>): List<CharmSaleOfferDSO>

    suspend fun insertCharmSaleOffers(charmSaleOffers: List<CharmSaleOfferDSO>): List<CharmLinkDSO>

    suspend fun insertCharmFloat(charmFloat: CharmFloatDSO)

    suspend fun insertCharmsFloat(charmsFloat: List<CharmFloatDSO>)
}