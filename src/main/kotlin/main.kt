import com.google.gson.JsonParser
import provider.downloadTo
import java.io.File
import java.net.URL
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

val GLOBAL_FOLDER = File("mappings")
val FJP = ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true)
val MAPPING_CLASS_COUNT_APPROXIMATION = 4000
val MAPPING_METHOD_COUNT_APPROXIMATION = MAPPING_CLASS_COUNT_APPROXIMATION * 2
val MAPPING_FIELD_COUNT_APPROXIMATION = MAPPING_CLASS_COUNT_APPROXIMATION * 2

fun main() {
    GLOBAL_FOLDER.mkdirs()
    download_minecraft_version_infos()
    println("Downloaded all Version informations")
    val lock = ReentrantLock()
    val condition = lock.newCondition()
    val left = AtomicInteger(MinecraftVersion.values().size)
    val threadPoolExecutor = ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors())
    timed { ->
        for (version in MinecraftVersion.values()) {
            if (File(GLOBAL_FOLDER, version.mcVersion).exists()) {
                if (left.decrementAndGet() == 0){
                    lock.withLock {
                        condition.signal()
                    }
                }
                continue
            }
            threadPoolExecutor.execute {
                try{
                    timed { -> generateVersion(version) }
                } catch (e:Throwable) {
                    File(GLOBAL_FOLDER,version.mcVersion).deleteRecursively()
                    RuntimeException("Error generating mappings for $version.", e).printStackTrace()
                }finally{
                    if (left.decrementAndGet() == 0){
                        lock.withLock {
                            condition.signal()
                        }
                    }
                }
            }
        }
        lock.withLock { 
            condition.await()
        }
        threadPoolExecutor.shutdown()
        threadPoolExecutor.awaitTermination(Long.MAX_VALUE,TimeUnit.DAYS)
        FJP.shutdown()
        FJP.awaitTermination(Long.MAX_VALUE,TimeUnit.DAYS)
    }
}

fun timed(runnable: Runnable){
    val time = System.currentTimeMillis()
    runnable.run()
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
        val obj = item.asJsonObject
        if (!obj.get("type").asString.equals("release")) continue
        val id = obj.get("id").asString
        val folder = File(".minecraft/versions/$id/")
        folder.mkdirs()
        val file = File(folder, "$id.json")
        if (file.exists()) continue
        URL(obj.get("url").asString).downloadTo(file)
    }
}