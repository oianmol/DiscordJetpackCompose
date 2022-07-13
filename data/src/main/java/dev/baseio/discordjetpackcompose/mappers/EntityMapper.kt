package dev.baseio.discordjetpackcompose.mappers

interface EntityMapper<Domain, Data> {
  fun mapToDomain(entity: Data): Domain
  fun mapToData(model: Domain): Data
}
