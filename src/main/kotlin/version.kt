import com.google.common.collect.ImmutableList
import com.google.common.collect.MultimapBuilder
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.techcable.srglib.FieldData
import net.techcable.srglib.JavaType
import net.techcable.srglib.MethodData
import net.techcable.srglib.format.MappingsFormat
import net.techcable.srglib.mappings.Mappings
import provider.*
import java.io.File
import SpigotMappingType.*

//mcp versions: https://web.archive.org/web/20211108214657if_/http://export.mcpbot.bspk.rs/versions.json
//And again, but only stable: https://nexus.c0d3m4513r.com/repository/Forge/de/oceanlabs/mcp/mcp_stable/maven-metadata.xml
//And again, but only snapshot: https://nexus.c0d3m4513r.com/repository/Forge/de/oceanlabs/mcp/mcp_snapshot/maven-metadata.xml
//even more mcp versions?: https://nexus.c0d3m4513r.com/repository/Forge/de/oceanlabs/mcp/mcp/maven-metadata.xml
//mcpConfig versions: https://nexus.c0d3m4513r.com/repository/Forge/de/oceanlabs/mcp/mcp_config/maven-metadata.xml
//spigot versions: https://hub.spigotmc.org/versions
//legacy intermediate versions: https://github.com/Legacy-Fabric/Legacy-Intermediaries/tree/master/mappings
enum class MinecraftVersion(
    val mcVersion: String,
    val mcpVersion: String? = null,
    val mcpConfig: Boolean = false,
    val spigot: SpigotMappingType = NO_SPIGOT,
    val yarn: Boolean = false,
    val mojang: Boolean = false,
    val legacyIntermediary: Boolean = false
) {
    V1_21_1("1.21.1", null, true, MODERN_SPIGOT, true, true, false),
    V1_21("1.21", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_6("1.20.6", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_5("1.20.5", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_4("1.20.4", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_3("1.20.3", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_2("1.20.2", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_1("1.20.1", null, true, MODERN_SPIGOT, true, true, false),
    V1_20_0("1.20", null, true, MODERN_SPIGOT, true, true, false),
    V1_19_4("1.19.4", null, true, MODERN_SPIGOT, true, true, false),
    V1_19_3("1.19.3", null, true, MODERN_SPIGOT, true, true, false),
    V1_19_2("1.19.2", null, true, MODERN_SPIGOT, true, true, false),
    V1_19_1("1.19.1", null, true, MODERN_SPIGOT, true, true, false),
    V1_19_0("1.19", null, true, MODERN_SPIGOT, false, true, false), //yarn throws consistency errors
    V1_18_2("1.18.2", null, true, MODERN_SPIGOT, true, true, false),
    V1_18_1("1.18.1", null, true, MODERN_SPIGOT, true, true, false),
    V1_18_0("1.18", null, true, MODERN_SPIGOT, true, true, false),
    V1_17_1("1.17.1", null, true, SPIGOT, true, true, false),
    V1_17_0("1.17", null, true, SPIGOT, true, true, false),
    V1_16_5("1.16.5", null, true, SPIGOT, true, true, false),
    V1_16_4("1.16.4", null, true, SPIGOT, true, true, false),
    V1_16_3("1.16.3", null, true, SPIGOT, true, true, false),
    V1_16_2("1.16.2", null, true, SPIGOT, true, true, false),
    V1_16_1("1.16.1", null, true, SPIGOT, true, true, false),
    V1_16_0("1.16", null, true, NO_SPIGOT, true, true, false),
    V1_15_2("1.15.2", null, true, SPIGOT, true, true, false),
    V1_15_1("1.15.1", "snapshot_nodoc_20211108", true, SPIGOT, true, true, false),
    V1_15_0("1.15", "stable_nodoc_60", true, SPIGOT, true, true, false),
    //V1_14_4("1.14.4", "stable_nodoc_58", true, SPIGOT, true, true, false), //mcp mappings error out (mcpConfig, stable and snapshot)
    V1_14_3("1.14.3", "stable_nodoc_56", true, SPIGOT, true, false, false),
    V1_14_2("1.14.2", "stable_nodoc_53", true, SPIGOT, true, false, false),
    V1_14_1("1.14.1", "stable_nodoc_51", true, SPIGOT, true, false, false),
    V1_14_0("1.14", "stable_nodoc_49", true, SPIGOT, true, false, false),
    V1_13_2("1.13.2", "stable_nodoc_47", true, SPIGOT, false, false, true),
    V1_13_1("1.13.1", "stable_nodoc_45", true, SPIGOT, false, false, false),
    V1_13_0("1.13", "stable_nodoc_43", true, SPIGOT, false, false, false),
    V1_12_2("1.12.2", "stable_nodoc_39", true, SPIGOT, false, false, true),
    V1_12_1("1.12.1", null, false, SPIGOT, false, false, false),
    V1_12_0("1.12", "stable_nodoc_39", false, SPIGOT, false, false, false),
    V1_11_2("1.11.2", null, false, SPIGOT, false, false, true),
    V1_11_1("1.11.1", null, false, SPIGOT, false, false, false),
    V1_11_0("1.11", "stable_nodoc_32", false, SPIGOT, false, false, false),
    V1_10_2("1.10.2", "stable_nodoc_29", false, SPIGOT, false, false, true),
    V1_10_0("1.10", null, false, SPIGOT, false, false, false),
    V1_9_4("1.9.4", "stable_nodoc_26", false, SPIGOT, false, false, true),
    V1_9_2("1.9.2", null, false, SPIGOT, false, false, false),
    V1_9("1.9", "stable_nodoc_24", false, SPIGOT, false, false, false),
    V1_8_9("1.8.9", "stable_nodoc_22", false, NO_SPIGOT, false, false, true),
    V1_8_8("1.8.8","stable_nodoc_20", false, SPIGOT, false, false, true),
    V1_8("1.8", "stable_nodoc_18", false, SPIGOT, false, false, true),
    V1_7_10("1.7.10", "stable_nodoc_12", false, NO_SPIGOT, false, false, false);
    fun generateMappings(): List<Pair<String, Mappings>> {
        // Mappings, fromObf
        val mappings = mutableListOf<Pair<Mappings, String>>()
        
        //srg mappings
        val obf2srgMappings = if (mcpConfig) {
            getMCPConfigMappings(mcVersion)
        } else {
            downloadSrgMappings(mcVersion)
        }
        mappings.add(Pair(obf2srgMappings, "srg"))
        
        //mcp mappings build ontop of srg mappings. Only add mcp, if available
        if (mcpVersion != null) {
            val srg2mcpMappings = downloadMcpMappings(obf2srgMappings, mcpVersion)
            val obf2mcp = Mappings.chain(ImmutableList.of(obf2srgMappings, srg2mcpMappings))
            mappings.add(Pair(obf2mcp, "mcp"))
        }
        if (spigot == SPIGOT || spigot == MODERN_SPIGOT) {
            val buildDataCommit = getBuildDataCommit(mcVersion)
            val obf2spigotMappings = downloadSpigotMappings(buildDataCommit, spigot == MODERN_SPIGOT)
            mappings.add(Pair(obf2spigotMappings, "spigot"))
        }
        if (yarn) {
            val obf2yarnMappingsSet = getYarnMappings(mcVersion)
            obf2yarnMappingsSet.forEach { id, m -> mappings.add(Pair(m, id)) }
        }
        if (legacyIntermediary) {
            val obf2legacyIntermediaryMappingsSet = getLegacyIntermediaryMappings(mcVersion)
            obf2legacyIntermediaryMappingsSet.forEach { id, m -> mappings.add(Pair(m, id)) }
        }
        if (mojang) {
            val obf2mojangMappingSet = MojangMappings.getMappings(mcVersion)
            mappings.add(Pair(obf2mojangMappingSet, "mojang"))
        }

        val completeMappings = mutableListOf<Pair<String, Mappings>>()
        for (a in mappings) {
            val obf2aMappings = a.first
            val a2obfMappings = obf2aMappings.inverted()

            completeMappings.add(Pair("obf2${a.second}", obf2aMappings))
            completeMappings.add(Pair("${a.second}2obf", a2obfMappings))
            for (b in mappings) {
                if (a != b) {
                    try {
                        // some code
                        val a2bMappings = Mappings.chain(a2obfMappings, b.first)
                        completeMappings.add(Pair("${a.second}2${b.second}", a2bMappings))
                    } catch (e: IllegalArgumentException) {
                        // handler
                        println("Failed: ${a.second}2${b.second}")
                    }
                }
            }
        }
        return completeMappings
    }

    fun write(mappingsFolder: File) {
        val outputFolder = File(mappingsFolder, mcVersion)
        outputFolder.mkdirs()

        fun Mappings.writeTo(fileName: String) {
            println("$mcVersion: writing mappings to $fileName.srg")
            val strippedMappings = stripDuplicates(this)
            val srgLines = MappingsFormat.SEARGE_FORMAT.toLines(strippedMappings)
            srgLines.sort()
            val file = File(outputFolder, "$fileName.srg")
            file.createNewFile()
            file.bufferedWriter().use {
                for (line in srgLines) {
                    it.write(line)
                    it.write("\n")
                }
            }

            println("$mcVersion: writing mappings to $fileName.csrg")
            val csrgLines = MappingsFormat.COMPACT_SEARGE_FORMAT.toLines(strippedMappings)
            csrgLines.sort()
            File(outputFolder, "$fileName.csrg").bufferedWriter().use {
                for (line in csrgLines) {
                    it.write(line)
                    it.write("\n")
                }
            }

            println("$mcVersion: writing mappings to $fileName.tsrg")
            TSrgUtil.fromSrg(file, File(outputFolder, "$fileName.tsrg"))
        }

        // srg & tsrg
        val generatedMappings = generateMappings()
        generatedMappings.forEach { pair ->
            val fileName = pair.first
            val mappings = pair.second
            mappings.writeTo(fileName)
        }

        // tiny
        println("$mcVersion: writing tiny mappings to $mcVersion.tiny")
        val tinyMappings = tiny.Mappings()
        generatedMappings.filter { it.first.startsWith("obf2") }.forEach { pair ->
            val name = pair.first.split("2")[1]

            tinyMappings.addMappings(name, pair.second)
        }
        File(outputFolder, "$mcVersion.tiny").bufferedWriter().use {
            for (line in tinyMappings.toStrings()) {
                it.write(line)
                it.write("\n")
            }
        }

        // json
        val classMappings =
            MultimapBuilder.hashKeys(1000).arrayListValues().build<JavaType, Pair<String, JavaType>>()
        val fieldMappings =
            MultimapBuilder.hashKeys(1000).arrayListValues().build<FieldData, Pair<String, FieldData>>()
        val methodMappings =
            MultimapBuilder.hashKeys(1000).arrayListValues().build<MethodData, Pair<String, MethodData>>()
        generatedMappings.filter { it.first.startsWith("obf2") }.forEach { pair ->
            val name = pair.first.split("2")[1]
            val mappings = pair.second
            mappings.forEachClass { obf, mapped -> classMappings.put(obf, Pair(name, mapped)) }
            mappings.forEachField { obf, mapped -> fieldMappings.put(obf, Pair(name, mapped)) }
            mappings.forEachMethod { obf, mapped -> methodMappings.put(obf, Pair(name, mapped)) }
            println("$mcVersion: generating json for $name")
        }

        fun String.lp(): String = split(".").last()

        val classArray = JsonArray()
        val fieldArray = JsonArray()
        val methodArray = JsonArray()
        for (obf in classMappings.keySet()) {
            val mappedObj = JsonObject()
            mappedObj.addProperty("obf", obf.name.lp())
            classMappings.get(obf).forEach {
                mappedObj.addProperty(it.first, it.second.name.lp())
            }
            classArray.add(mappedObj)
        }
        for (obf in fieldMappings.keySet()) {
            val mappedObj = JsonObject()
            mappedObj.addProperty("obf", obf.declaringType.name.lp() + "." + obf.name.lp())
            fieldMappings.get(obf).forEach {
                mappedObj.addProperty(it.first, it.second.declaringType.name.lp() + "." + it.second.name)
            }
            fieldArray.add(mappedObj)
        }
        for (obf in methodMappings.keySet()) {
            val mappedObj = JsonObject()
            mappedObj.addProperty("obf", obf.declaringType.name.lp() + "." + obf.name.lp())
            methodMappings.get(obf).forEach {
                mappedObj.addProperty(it.first, it.second.declaringType.name.lp() + "." + it.second.name)
            }
            methodArray.add(mappedObj)
        }

        val bigJson = JsonObject()
        bigJson.addProperty("minecraftVersion", mcVersion)
        bigJson.add("classes", classArray)
        bigJson.add("fields", fieldArray)
        bigJson.add("methods", methodArray)
        File(outputFolder, "$mcVersion.json").writeText(Gson().toJson(bigJson))
    }
}