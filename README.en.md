# netty-client-server
[![en](https://github.com/renatocunha216/common/blob/main/images/lang-en.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.en.md)
[![pt-br](https://github.com/renatocunha216/common/blob/main/images/lang-pt-br.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.md)

Example of using the [Netty](https://netty.io/) NIO client server framework with FlatBuffers for protocol data serialization.

### Application protocol

The application protocol is a binary protocol where the first bytes of protocol messages define the total size of the message.<br>
This pattern is implemented by the Netty framework through the **LengthFieldBasedFrameDecoder** class.<br>

In addition to the length field, the protocol includes the payload of the message encoded with FlatBuffers.<br>

| Position | Field                    | Length                  |
|----------|--------------------------|-------------------------|
|  0       |  Length                  | 2 bytes                 |
|  1       |  Data (Payload)          | N bytes                 |


### Example of use