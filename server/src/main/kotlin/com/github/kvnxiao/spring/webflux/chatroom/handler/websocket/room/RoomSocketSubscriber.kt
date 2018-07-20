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
package com.github.kvnxiao.spring.webflux.chatroom.handler.websocket.room

import com.github.kvnxiao.spring.webflux.chatroom.handler.websocket.WebSocketSubscriber
import com.github.kvnxiao.spring.webflux.chatroom.handler.websocket.event.UserConnectedEvent
import com.github.kvnxiao.spring.webflux.chatroom.handler.websocket.event.UserDisconnectedEvent
import com.github.kvnxiao.spring.webflux.chatroom.handler.websocket.event.WebSocketEvent
import com.github.kvnxiao.spring.webflux.chatroom.model.ChatLobby
import com.github.kvnxiao.spring.webflux.chatroom.model.User
import reactor.core.publisher.UnicastProcessor

class RoomSocketSubscriber(
    val lobby: ChatLobby,
    eventProcessor: UnicastProcessor<WebSocketEvent>,
    user: User
)
    : WebSocketSubscriber<WebSocketEvent>(eventProcessor, user) {

    override fun onComplete() {
        lobby.removeUserFromRoom(user)
        globalEventProcessor.onNext(UserDisconnectedEvent(user))
    }

    override fun onConnect() {
        globalEventProcessor.onNext(UserConnectedEvent(user))
    }
}
