package jp.katana.server

import jp.katana.core.IServer
import jp.katana.core.IServerProperties
import jp.katana.core.ServerState
import jp.katana.i18n.I18n
import jp.katana.server.console.KatanaConsole
import org.apache.logging.log4j.LogManager
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File


class Server : IServer {
    companion object {
        init {
            System.setProperty("log4j.skipJansi", "false")
            System.setProperty("log4j.configurationFile", "log4j2-katana.xml")
        }
    }

    override val propertiesFile: File = File("properties.yml")
    override var serverProperties: IServerProperties? = null
        private set
    override var state: ServerState = ServerState.Stopped
        private set
    override val logger = LogManager.getLogger(Server::class.java)!!
    override val console = KatanaConsole(this)

    private val consoleThread = Thread { startConsole() }

    override fun start() {
        state = ServerState.Running
        consoleThread.start()

        logger.info(I18n["katana.server.starting"])

        try {
            loadServerProperties()
        } catch (e: Exception) {
            return
        }

        while (state == ServerState.Running) {
        }
    }

    override fun shutdown(): Boolean {
        state = ServerState.Stopping

        state = ServerState.Stopped
        return true
    }

    override fun shutdownForce(): Boolean {
        return true
    }

    private fun startConsole() {
        try {
            console.start()
        } catch (e: Exception) {
            logger.error(e)
        }
    }

    private fun loadServerProperties() {
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        options.indicatorIndent = 2
        options.indent = 4
        val yaml = Yaml(options)
        if (propertiesFile.exists()) {
            serverProperties = yaml.loadAs(propertiesFile.reader(), IServerProperties::class.java)
        } else {
            serverProperties = ServerProperties()
            propertiesFile.createNewFile()
            yaml.dump(serverProperties, propertiesFile.writer())
        }
    }
}