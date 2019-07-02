package jp.katana.i18n

import org.apache.logging.log4j.LogManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class I18n {
    companion object {
        private var bundle: ResourceBundle? = null
        private val logger = LogManager.getLogger()

        init {
            bundle = ResourceBundle.getBundle("lang", Locale.JAPANESE, ResourceBundleUtf8Control())
        }

        operator fun get(key: String): String {
            return try {
                bundle!!.getString(key)
            } catch (e: RuntimeException) {
                val msg = "リソースが見つかりません!! <$key>"
                logger.warn(msg)
                msg
            }
        }

        operator fun get(key: String, vararg args: Any): String {
            return try {
                String.format(get(key), *args)
            } catch (e: Exception) {
                val msg = "リソースのフォーマット中にエラーが発生しました。"
                logger.warn(msg)
                logger.warn(e.toString())
                msg
            }
        }

        internal class ResourceBundleUtf8Control : ResourceBundle.Control() {
            @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
            override fun newBundle(
                baseName: String, locale: Locale, format: String,
                loader: ClassLoader, reload: Boolean
            ): ResourceBundle {
                val bundleName = toBundleName(baseName, locale)
                val resourceName = toResourceName(bundleName, "properties")

                loader.getResourceAsStream(resourceName)!!.use { `is` ->
                    InputStreamReader(
                        `is`,
                        "UTF-8"
                    ).use { isr -> BufferedReader(isr).use { reader -> return PropertyResourceBundle(reader) } }
                }
            }
        }
    }
}