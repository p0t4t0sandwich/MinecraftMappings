package tiny

import MAPPING_CLASS_COUNT_APPROXIMATION
import MAPPING_FIELD_COUNT_APPROXIMATION
import MAPPING_METHOD_COUNT_APPROXIMATION
import com.google.common.collect.ImmutableBiMap
import net.techcable.srglib.FieldData
import net.techcable.srglib.JavaType
import net.techcable.srglib.MethodData
import net.techcable.srglib.MethodSignature
import net.techcable.srglib.mappings.ImmutableMappings
import net.techcable.srglib.mappings.Mappings

/**
 * Merge multiple srglib.Mappings into one tiny.Mappings
 */

class Mappings(
    var namespaces: MutableList<String>,
    val classes: HashMap<ClassMappingData, MutableMap<String, String>>,
    val fields: HashMap<FieldMappingData, MutableMap<String, String>>,
    val methods: HashMap<MethodMappingData, MutableMap<String, String>>
) {

    constructor() : this(mutableListOf(), HashMap(), HashMap(), HashMap())

    fun addMappings(namespace: String, mappings: Mappings) {
        namespaces.add(namespace)
        println("tiny: starting conversion for $namespace")
        mappings.forEachClass { obf, mapped ->
            getClass(obf.name).add(namespace, mapped.name)
        }
        mappings.forEachField { obf, mapped ->
            getField(obf.declaringType.name, obf.name, "Lunk;").add(namespace, mapped.name)
        }
        mappings.forEachMethod { obf, mapped ->
            getMethod(obf.declaringType.name, obf.name, obf.signature.descriptor).add(namespace, mapped.name)
        }
    }

    fun getClass(source: String): EntryMapping<ClassMappingData> {
        val clazz = ClassMappingData(source)
        return EntryMapping(classes.getOrPut(clazz) { mutableMapOf() }, source, clazz)
    }

    fun getField(sourceClass: String, source: String, desc: String): EntryMapping<FieldMappingData> {
        val fieldData = FieldMappingData(sourceClass, source, desc)
        return EntryMapping(fields.getOrPut(fieldData) {mutableMapOf() }, source, fieldData)
    }

    fun getMethod(sourceClass: String, source: String, desc: String): EntryMapping<MethodMappingData> {
        val fieldData = MethodMappingData(sourceClass, source, desc)
        return EntryMapping(methods.getOrPut(fieldData) {mutableMapOf() }, source, fieldData)
    }

    fun toStrings(): List<String> {
        val entryMappings = mutableListOf("v1\tofficial\t${namespaces.joinToString("\t")}")
        entryMappings.addAll(classes.map { EntryMapping(it.value, it.key.source, it.key).toString(namespaces) })
        entryMappings.addAll(fields.map { EntryMapping(it.value, it.key.source, it.key).toString(namespaces) })
        entryMappings.addAll(methods.map { EntryMapping(it.value, it.key.source, it.key).toString(namespaces) })
        return entryMappings
    }

    fun toMappings(): Map<String, Mappings> = namespaces.map { namespace ->
        val classMappings = ImmutableBiMap.copyOf(classes.mapNotNull {
            try {
                Pair(
                    JavaType.fromDescriptor("L${it.key.source};"),
                    JavaType.fromDescriptor("L${(it.value[namespace] ?: it.key.source).replace('/', '.')};")
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }.toMap())
        val fieldDatas = fields.mapNotNull {
            try {
                it to FieldData.create(JavaType.fromDescriptor("L${it.key.sourceClass};"), it.key.source)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        val methodDatas = methods.mapNotNull {
            try {
                it to MethodData.create(
                    JavaType.fromDescriptor("L${it.key.sourceClass};"),
                    it.key.source,
                    MethodSignature.fromDescriptor(it.key.desc)
                )
            } catch (e: Exception) {
                // e.printStackTrace() // yarn data has numbers in it for some reason?
                null
            }
        }
        val fieldMappings = ImmutableBiMap.copyOf(fieldDatas.map { (field, fieldData) ->
            Pair(fieldData, fieldData.mapTypes { classMappings[it] ?: it }.withName(field.value[namespace] ?: field.key.source))
        }.toMap())
        val methodMappings = ImmutableBiMap.copyOf(methodDatas.map { (method, methodData) ->
            Pair(
                methodData,
                methodData.mapTypes { classMappings[it] ?: it }.withName(method.value[namespace] ?: method.key.source)
            )
        }.toMap())
        val namespace = when (namespace) {
            "named" -> "yarn"
            else -> namespace
        }
        namespace to ImmutableMappings.create(classMappings, methodMappings, fieldMappings)
    }.toMap()
}

interface MappingDataType{
    fun mappingDataType():String
}

class EntryMapping<T: MappingDataType>(
    private val mappings: MutableMap<String, String>,
    private val source: String,
    private val data: T
) {
    fun add(namespace: String, value: String): EntryMapping<T> {
        mappings[namespace] = value
        return this
    }

    operator fun get(namespace: String): String? = mappings[namespace]

    fun toString(namespaces: List<String>): String {
        val line = namespaces.joinToString("\t") { get(it) ?: source }
        return "${data.mappingDataType()}\t${data}\t$line".replace('.', '/')
    }
}

data class ClassMappingData(
    val source: String,
):MappingDataType {
    override fun mappingDataType(): String = "CLASS"
    override fun toString(): String = source
}

data class FieldMappingData (
    val sourceClass: String,
    val source: String,
    val desc: String,
):MappingDataType {
    override fun mappingDataType(): String = "FIELD"
    override fun toString(): String = "$sourceClass\t$desc\t$source"
}

data class MethodMappingData (
    val sourceClass: String,
    val source: String,
    val desc: String,
):MappingDataType {
    override fun mappingDataType(): String = "METHOD"
    override fun toString(): String = "$sourceClass\t$desc\t$source"
}
