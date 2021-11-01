package com.example.productapp.data

import com.example.productapp.domain.ProductItem

class ProductListMapper {

    fun mapEntityToDbModel(productItem: ProductItem) : ProductItemDbModel = ProductItemDbModel(
        id = productItem.id,
        name = productItem.name,
        count = productItem.count,
        enabled = productItem.enabled
    )

    fun mapDbModelToEntity(productItemDbModel: ProductItemDbModel) : ProductItem = ProductItem(
        id = productItemDbModel.id,
        name = productItemDbModel.name,
        count = productItemDbModel.count,
        enabled = productItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ProductItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}