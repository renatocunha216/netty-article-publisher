# netty-article-publisher
[![en](https://github.com/renatocunha216/common/blob/main/images/lang-en.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.en.md)
[![pt-br](https://github.com/renatocunha216/common/blob/main/images/lang-pt-br.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.md)

Example of using the [Netty](https://netty.io/) NIO client server framework with [FlatBuffers](https://flatbuffers.dev/) for protocol data serialization.

### Application protocol

The application protocol is a binary protocol where the first bytes of protocol messages define the total size of the message.<br>
This pattern is implemented by the Netty framework through the **LengthFieldBasedFrameDecoder** class.<br>

In addition to the length field, the protocol includes the payload of the message encoded with FlatBuffers.<br>

| Position | Field                    | Length                  |
|----------|--------------------------|-------------------------|
|  0       |  Length                  | 2 bytes                 |
|  1       |  Data (Payload)          | N bytes                 |

<br>

Protocol messages are defined in the [ProtocolMessages.fbs](https://github.com/renatocunha216/netty-article-publisher/blob/main/idl/ProtocolMessages.fbs) file.

To define all protocol messages in a FlatBuffers schema, a Table type was used as the root type that contains a single attribute of the Union type. The Union type object will group all protocol messages.


```
union AllProtocolMessagesFbs {
    loginRequestFbs: LoginRequestFbs = 5,
    loginResponseFbs: LoginResponseFbs = 6,
    postArticleRequestFbs: PostArticleRequestFbs = 7,
    postArticleResponseFbs: PostArticleResponseFbs = 8
}

table ProtocolMessageFbs {
    message: AllProtocolMessagesFbs;
}
```

### Example of use

**Server**:<br>
Execute the **main** method of the [br.com.rbcti.server.PublisherServerTest](https://github.com/renatocunha216/netty-article-publisher/blob/main/netty-publisher-server/src/main/java/br/com/rbcti/server/PublisherServerTest.java) class.<br>

**Client**:<br>
Execute the **main** method of the [br.com.rbcti.client.ExampleNettyPublisherClient](https://github.com/renatocunha216/netty-article-publisher/blob/main/netty-publisher-client/src/test/java/br/com/rbcti/client/ExampleNettyPublisherClient.java) class.<br>
