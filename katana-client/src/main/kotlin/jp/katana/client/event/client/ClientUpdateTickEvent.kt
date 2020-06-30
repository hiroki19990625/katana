package jp.katana.client.event.client

import jp.katana.client.Client

class ClientUpdateTickEvent(client: Client, tick: Long) : ClientEvent(client)