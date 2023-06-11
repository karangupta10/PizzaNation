package com.example.pizzanation

import android.provider.BaseColumns

object CartItemObject {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "orderItems"
        const val COLUMN_NAME_ITEM_NAME = "item_name"
        const val COLUMN_NAME_QUANTITY = "quantity"
        const val COLUMN_NAME_ITEM_PRICE = "item_price"
        const val COLUMN_NAME_NET_PRICE = "net_price"
    }
}