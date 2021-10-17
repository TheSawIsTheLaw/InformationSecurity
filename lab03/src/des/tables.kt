package des

object DESTables
{
    val ipTable: List<UByte> = listOf(
        58u, 50u, 42u, 34u, 26u, 18u, 10u, 2u, 60u, 52u, 44u, 36u, 28u, 20u, 12u, 4u,
        62u, 54u, 46u, 38u, 30u, 22u, 14u, 6u, 64u, 56u, 48u, 40u, 32u, 24u, 16u, 8u,
        57u, 49u, 41u, 33u, 25u, 17u, 9u, 1u, 59u, 51u, 43u, 35u, 27u, 19u, 11u, 3u,
        61u, 53u, 45u, 37u, 29u, 21u, 13u, 5u, 63u, 55u, 47u, 39u, 31u, 23u, 15u, 7u
    )

    val fpTable: List<UByte> = listOf(
        40u, 8u, 48u, 16u, 56u, 24u, 64u, 32u, 39u, 7u, 47u, 15u, 55u, 23u, 63u, 31u,
        38u, 6u, 46u, 14u, 54u, 22u, 62u, 30u, 37u, 5u, 45u, 13u, 53u, 21u, 61u, 29u,
        36u, 4u, 44u, 12u, 52u, 20u, 60u, 28u, 35u, 3u, 43u, 11u, 51u, 19u, 59u, 27u,
        34u, 2u, 42u, 10u, 50u, 18u, 58u, 26u, 33u, 1u, 41u, 9u, 49u, 17u, 57u, 25u
    )

    val epTable: List<UByte> = listOf(
        32u, 1u, 2u, 3u, 4u, 5u, 4u, 5u, 6u, 7u, 8u, 9u,
        8u, 9u, 10u, 11u, 12u, 13u, 12u, 13u, 14u, 15u, 16u, 17u,
        16u, 17u, 18u, 19u, 20u, 21u, 20u, 21u, 22u, 23u, 24u, 25u,
        24u, 25u, 26u, 27u, 28u, 29u, 28u, 29u, 30u, 31u, 32u, 1u
    )

    val sBox: List<List<List<UByte>>> = listOf(
        listOf(
            listOf(14u, 4u, 13u, 1u, 2u, 15u, 11u, 8u, 3u, 10u, 6u, 12u, 5u, 9u, 0u, 7u),
            listOf(0u, 15u, 7u, 4u, 14u, 2u, 13u, 1u, 10u, 6u, 12u, 11u, 9u, 5u, 3u, 8u),
            listOf(4u, 1u, 14u, 8u, 13u, 6u, 2u, 11u, 15u, 12u, 9u, 7u, 3u, 10u, 5u, 0u),
            listOf(15u, 12u, 8u, 2u, 4u, 9u, 1u, 7u, 5u, 11u, 3u, 14u, 10u, 0u, 6u, 13u),
        ),
        listOf(
            listOf(15u, 1u, 8u, 14u, 6u, 11u, 3u, 4u, 9u, 7u, 2u, 13u, 12u, 0u, 5u, 10u),
            listOf(3u, 13u, 4u, 7u, 15u, 2u, 8u, 14u, 12u, 0u, 1u, 10u, 6u, 9u, 11u, 5u),
            listOf(0u, 14u, 7u, 11u, 10u, 4u, 13u, 1u, 5u, 8u, 12u, 6u, 9u, 3u, 2u, 15u),
            listOf(13u, 8u, 10u, 1u, 3u, 15u, 4u, 2u, 11u, 6u, 7u, 12u, 0u, 5u, 14u, 9u),
        ),
        listOf(
            listOf(10u, 0u, 9u, 14u, 6u, 3u, 15u, 5u, 1u, 13u, 12u, 7u, 11u, 4u, 2u, 8u),
            listOf(13u, 7u, 0u, 9u, 3u, 4u, 6u, 10u, 2u, 8u, 5u, 14u, 12u, 11u, 15u, 1u),
            listOf(13u, 6u, 4u, 9u, 8u, 15u, 3u, 0u, 11u, 1u, 2u, 12u, 5u, 10u, 14u, 7u),
            listOf(1u, 10u, 13u, 0u, 6u, 9u, 8u, 7u, 4u, 15u, 14u, 3u, 11u, 5u, 2u, 12u),
        ),
        listOf(
            listOf(7u, 13u, 14u, 3u, 0u, 6u, 9u, 10u, 1u, 2u, 8u, 5u, 11u, 12u, 4u, 15u),
            listOf(13u, 8u, 11u, 5u, 6u, 15u, 0u, 3u, 4u, 7u, 2u, 12u, 1u, 10u, 14u, 9u),
            listOf(10u, 6u, 9u, 0u, 12u, 11u, 7u, 13u, 15u, 1u, 3u, 14u, 5u, 2u, 8u, 4u),
            listOf(3u, 15u, 0u, 6u, 10u, 1u, 13u, 8u, 9u, 4u, 5u, 11u, 12u, 7u, 2u, 14u),
        ),
        listOf(
            listOf(2u, 12u, 4u, 1u, 7u, 10u, 11u, 6u, 8u, 5u, 3u, 15u, 13u, 0u, 14u, 9u),
            listOf(14u, 11u, 2u, 12u, 4u, 7u, 13u, 1u, 5u, 0u, 15u, 10u, 3u, 9u, 8u, 6u),
            listOf(4u, 2u, 1u, 11u, 10u, 13u, 7u, 8u, 15u, 9u, 12u, 5u, 6u, 3u, 0u, 14u),
            listOf(11u, 8u, 12u, 7u, 1u, 14u, 2u, 13u, 6u, 15u, 0u, 9u, 10u, 4u, 5u, 3u),
        ),
        listOf(
            listOf(12u, 1u, 10u, 15u, 9u, 2u, 6u, 8u, 0u, 13u, 3u, 4u, 14u, 7u, 5u, 11u),
            listOf(10u, 15u, 4u, 2u, 7u, 12u, 9u, 5u, 6u, 1u, 13u, 14u, 0u, 11u, 3u, 8u),
            listOf(9u, 14u, 15u, 5u, 2u, 8u, 12u, 3u, 7u, 0u, 4u, 10u, 1u, 13u, 11u, 6u),
            listOf(4u, 3u, 2u, 12u, 9u, 5u, 15u, 10u, 11u, 14u, 1u, 7u, 6u, 0u, 8u, 13u),
        ),
        listOf(
            listOf(4u, 11u, 2u, 14u, 15u, 0u, 8u, 13u, 3u, 12u, 9u, 7u, 5u, 10u, 6u, 1u),
            listOf(13u, 0u, 11u, 7u, 4u, 9u, 1u, 10u, 14u, 3u, 5u, 12u, 2u, 15u, 8u, 6u),
            listOf(1u, 4u, 11u, 13u, 12u, 3u, 7u, 14u, 10u, 15u, 6u, 8u, 0u, 5u, 9u, 2u),
            listOf(6u, 11u, 13u, 8u, 1u, 4u, 10u, 7u, 9u, 5u, 0u, 15u, 14u, 2u, 3u, 12u),
        ),
        listOf(
            listOf(13u, 2u, 8u, 4u, 6u, 15u, 11u, 1u, 10u, 9u, 3u, 14u, 5u, 0u, 12u, 7u),
            listOf(1u, 15u, 13u, 8u, 10u, 3u, 7u, 4u, 12u, 5u, 6u, 11u, 0u, 14u, 9u, 2u),
            listOf(7u, 11u, 4u, 1u, 9u, 12u, 14u, 2u, 0u, 6u, 10u, 13u, 15u, 3u, 5u, 8u),
            listOf(2u, 1u, 14u, 7u, 4u, 10u, 8u, 13u, 15u, 12u, 9u, 0u, 3u, 5u, 6u, 11u),
        )
    )

    val permTable = listOf<UByte>(
        16u, 7u, 20u, 21u, 29u, 12u, 28u, 17u, 1u, 15u, 23u, 26u, 5u, 18u, 31u, 10u,
        2u, 8u, 24u, 14u, 32u, 27u, 3u, 9u, 19u, 13u, 30u, 6u, 22u, 11u, 4u, 25u
    )

    val k1PTable = listOf<UByte>(
        57u, 49u, 41u, 33u, 25u, 17u, 9u, 1u, 58u, 50u, 42u, 34u, 26u, 18u,
        10u, 2u, 59u, 51u, 43u, 35u, 27u, 19u, 11u, 3u, 60u, 52u, 44u, 36u
    )

    val k2PTable = listOf<UByte>(
        63u, 55u, 47u, 39u, 31u, 23u, 15u, 7u, 62u, 54u, 46u, 38u, 30u, 22u,
        14u, 6u, 61u, 53u, 45u, 37u, 29u, 21u, 13u, 5u, 28u, 20u, 12u, 4u
    )

    val compressTable = listOf<UByte>(
        14u, 17u, 11u, 24u, 1u, 5u, 3u, 28u, 15u, 6u, 21u, 10u,
        23u, 19u, 12u, 4u, 26u, 8u, 16u, 7u, 27u, 20u, 13u, 2u,
        41u, 52u, 31u, 37u, 47u, 55u, 30u, 40u, 51u, 45u, 33u, 48u,
        44u, 49u, 39u, 56u, 34u, 53u, 46u, 42u, 50u, 36u, 29u, 32u
    )
}