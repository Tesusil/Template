package com.tesusil.template.domain

interface Mapper<DatasourceModel, DomainModel> {
    fun mapToDomainModel(dataModel: DatasourceModel): DomainModel
    fun mapToDataModel(domainModel: DomainModel): DatasourceModel
    fun mapToDomainModelList(dataModelList: List<DatasourceModel>): List<DomainModel>
    fun mapToDataModelList(domainModelList: List<DomainModel>): List<DatasourceModel>
}