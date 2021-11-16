package com.example.productapp.data

import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.UniqueProduct

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

    fun mapUniqueEntityToUniqueDbModel(uniqueProduct: UniqueProduct) : UniqueProductDBModel =
        UniqueProductDBModel(
            item = uniqueProduct.item,
            useCount = uniqueProduct.useCount
        )

    private fun mapUniqueDbModelToUniqueEntity(uniqueProductDBModel: UniqueProductDBModel) : UniqueProduct =
        UniqueProduct(
                item = uniqueProductDBModel.item,
                useCount = uniqueProductDBModel.useCount
            )

    fun mapListUniqueDbModelToListUniqueEntity(list: List<UniqueProductDBModel>) = list.map {
        mapUniqueDbModelToUniqueEntity(it)
    }
}