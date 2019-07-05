package jp.katana.server.event.server

import jp.katana.core.IServer
import jp.katana.core.event.IEvent

abstract class ServerEvent(val server: IServer) : IEvent