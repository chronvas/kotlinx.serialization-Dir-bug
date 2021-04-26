import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

fun main(args: Array<String>) {
    tryDeserialize()
}

@Serializable
data class MyBucket(val items: List<MyInterface?>)

@Polymorphic
interface MyInterface

@Serializable
data class First(val s1: String) : MyInterface


fun tryDeserialize() {
        val jsonStringList = """
        {
          "items": [
            {
             "type" : "First",
             "s1" : "some string"
            },
            {
             "type" : "First",
             "s1" : "some other string"
            }
         ]
       }
   """.trimIndent()

        val module = SerializersModule {
            polymorphic(MyInterface::class) {
                subclass(First::class)
            }
        }
        val json = Json {
            serializersModule = module
            isLenient = true
        }
        val output = json.decodeFromString<MyBucket>(jsonStringList)
        println(output)
}
