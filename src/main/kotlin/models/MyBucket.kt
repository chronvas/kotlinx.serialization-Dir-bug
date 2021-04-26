package models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class MyBucket(val items: List<MyInterface?>)

@Polymorphic
interface MyInterface

@Serializable
data class First(val s1: String) : MyInterface
