package com.example.solvatask.resttemplatemodel

import com.fasterxml.jackson.annotation.JsonAlias

class CurrencyCourseModel {

    @JsonAlias("USD/KZT:Huobi", "USD/RUB:Huobi", "USD/AED:Huobi")
    var pair: DataCurrency? = null
}