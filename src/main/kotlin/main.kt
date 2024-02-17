import com.google.gson.JsonParser
import provider.downloadTo
import java.io.File
import java.net.URL
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

val GLOBAL_FOLDER = File("mappings")

fun main() {
    GLOBAL_FOLDER.mkdirs()
    download_minecraft_version_infos()
    println("Downloaded all Version informations")
    val threadPoolExecutor = ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors())
    timed { ->
        for (version in MinecraftVersion.values()) {
            if (File(GLOBAL_FOLDER, version.mcVersion).exists()) continue;
            threadPoolExecutor.execute {
                try{
                    timed { -> generateVersion(version) }
                } catch (e:Throwable) {
                    File(GLOBAL_FOLDER,version.mcVersion).deleteRecursively()
                }
            }
        }
        threadPoolExecutor.shutdown()
        threadPoolExecutor.awaitTermination(Long.MAX_VALUE,TimeUnit.DAYS)
    }
}

fun timed(runnable: Runnable){
    val time = System.currentTimeMillis()
    runnable.run();
    val elapsed = (System.currentTimeMillis() - time) / 1000.0
    println("Done. Took ${elapsed / 60}m (${elapsed}s)")
}
fun generateVersion(version: MinecraftVersion) {
    println("Generating mappings for version: ${version.mcVersion}")
    version.write(GLOBAL_FOLDER)
    println("Generated mappings for version: ${version.mcVersion}")
}

fun download_minecraft_version_infos(){
    for (
    item in JsonParser().parse(URL("https://launchermeta.mojang.com/mc/game/version_manifest.json").openStream().reader())
        .asJsonObject
        .get("versions").asJsonArray
    ){
        val obj = item.asJsonObject;
        if (!obj.get("type").asString.equals("release")) continue
        val id = obj.get("id").asString
        val folder = File(".minecraft/versions/$id/")
        folder.mkdirs()
        val file = File(folder, "$id.json");
        if (file.exists()) continue;
        URL(obj.get("url").asString).downloadTo(file)
    }
}