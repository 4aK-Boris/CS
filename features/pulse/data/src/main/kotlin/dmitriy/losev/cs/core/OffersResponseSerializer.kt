package dmitriy.losev.cs.core

import dmitriy.losev.cs.dso.offers.response.OfferItemDSO
import dmitriy.losev.cs.dso.offers.response.OffersResponseDSO
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object OffersResponseSerializer : KSerializer<OffersResponseDSO> {
    private val listSerializer = ListSerializer(OfferItemDSO.serializer())

    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: OffersResponseDSO) {
        listSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): OffersResponseDSO {
        return OffersResponseDSO(listSerializer.deserialize(decoder))
    }
}