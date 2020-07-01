package jp.katana.server.event.server

import jp.katana.core.IServer

class ServerUpdateTickEvent(server: IServer, tick: Long) : ServerEvent(server)