import { ref } from 'vue'
import SockJS from 'sockjs-client'
import { Client } from '@stomp/stompjs'

export function useWebSocket() {
  const isConnected = ref(false)
  const client = ref(null)
  const messageHandlers = new Map()

  const connect = (onConnectCallback) => {
    const socket = new SockJS('/ws')
    
    client.value = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      
      onConnect: () => {
        console.log('WebSocket connected')
        isConnected.value = true
        
        client.value.subscribe('/topic/entities', (message) => {
          try {
            const data = JSON.parse(message.body)
            console.log('WebSocket message received:', data)
            
            messageHandlers.forEach((handler) => {
              handler(data)
            })
          } catch (error) {
            console.error('Error parsing WebSocket message:', error)
          }
        })

        if (onConnectCallback) {
          onConnectCallback()
        }
      },

      onDisconnect: () => {
        console.log('WebSocket disconnected')
        isConnected.value = false
      },

      onStompError: (frame) => {
        console.error('STOMP error:', frame)
      }
    })

    client.value.activate()
  }

  const disconnect = () => {
    if (client.value) {
      client.value.deactivate()
      client.value = null
      isConnected.value = false
    }
  }

  const addMessageHandler = (id, handler) => {
    messageHandlers.set(id, handler)
  }

  const removeMessageHandler = (id) => {
    messageHandlers.delete(id)
  }

  return {
    isConnected,
    connect,
    disconnect,
    addMessageHandler,
    removeMessageHandler
  }
}
