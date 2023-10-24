# netty-client-server
[![en](https://github.com/renatocunha216/common/blob/main/images/lang-en.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.en.md)
[![pt-br](https://github.com/renatocunha216/common/blob/main/images/lang-pt-br.svg?raw=true)](https://github.com/renatocunha216/netty-article-publisher/blob/main/README.md)

Exemplo de uso do framework [Netty](https://netty.io/) NIO em uma aplicação cliente servidor com FlatBuffers para serializar os dados do protocolo.


### Protocolo da aplicação

O protocolo da aplicação é um protocolo binário onde os primeiros bytes das mensagens do protocolo definem o tamanho total da mensagem.<br>
Este padrão é implementado pelo framework Netty através da classe **LengthFieldBasedFrameDecoder**.<br>

Além do campo de tamanho o protocolo inclui os dados úteis (payload) da mensagem codificados com FlatBuffers.<br>

| Posição | Campo                    | Tamanho                 |
|---------|--------------------------|-------------------------|
|  0      |  Tamanho                 | 2 bytes                 |
|  1      |  Dados úteis             | N bytes                 |


### Exemplo de uso
