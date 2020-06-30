package jp.katana.client.event.client

import jp.katana.client.Client
import jp.katana.core.event.IEvent

abstract class ClientEvent(val client: Client) : IEvent