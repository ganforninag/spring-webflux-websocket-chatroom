/*
 *   Copyright (C) 2017-2018 Ze Hao Xiao
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.kvnxiao.spring.webflux.chatroom.handler.websocket

import com.github.kvnxiao.spring.webflux.chatroom.model.ChatLobby
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class LobbySocketHandler @Autowired constructor(
    private val lobby: ChatLobby
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
            Flux.interval(Duration.ZERO, Duration.ofSeconds(15))
                .map { session.textMessage(lobby.listRoomsJson()) }
        ).and(session.receive()
            .map { it.payloadAsText }.log())
    }
}
