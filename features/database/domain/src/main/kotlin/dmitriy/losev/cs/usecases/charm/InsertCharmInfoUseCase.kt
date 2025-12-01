package dmitriy.losev.cs.usecases.charm

//@Factory
//class InsertCharmInfoUseCase(@Provided private val databaseCharmRepository: DatabaseCharmRepository): BaseUseCase {
//
//    suspend operator fun invoke(charmInfo: CharmInfoDTO): Result<String> = runCatching {
//        databaseCharmRepository.insertCharmInfo(charmInfo) ?: throw DatabaseException.UnsuccessfulInsertCharmInfoException(name = charmInfo.name)
//    }
//}
