package com.example.prog7313_starsucks

class Order() {
    /*
        -- ORDER PROPERTIES --
        Getters and setters are automatically created,
        therefore methods do not need to be created
     */
    lateinit var productName: String
    lateinit var customerName: String
    lateinit var customerCell: String
    lateinit var orderDate: String

    // secondary constructor
    constructor(pName: String): this() {
        productName = pName
    }

    // master constructor
    constructor(pName: String, cName: String, cCell: String, oDate: String) : this(pName) {
        customerName = cName
        customerCell = cCell
        orderDate = oDate
    }


}