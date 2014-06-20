Integração
=============

Sistema para envio e recebimento de notificações através do serviço Google Cloud Messaging.

***

#### Objetivo
Desenvolver dois projetos, um para Enviar a Noficação e outro para receber (Android).
Esses dois projetos tem que estar integrados, para que quando o dispositivo seja cadastrado (projeto "cliente"), possa receber as mensagens enviadas pelo projeto "server".


***

### Arquitetura

#### Projeto Web ([send-notification-4-android][1])

* Java, como plataforma geral de desenvolvimento;
* Objectify, como solução de persistência;
* JSF, como especificação dos componentes web;
* Bootstrap, como framework front-end para facilitar no desenvolvimento do layout;
* Google App Engine, como plataforma de computação em nuvem.
* Google DataStore, como base de dados em nuvem.

#### Projeto Mobile ([ReceiveNotification][2])

* Java, como plataforma geral de desenvolvimento
* Android SDK, como principal API de desenvolvimento.
* SQLite, como banco de dados.
* GCM, como serviço de envio/recebimento de notificações.


***

### Sobre os projetos:

O projeto [web][1] já está hospedado na plataforma da Google, disponível no seguinte [link][3]. http://1.send-notification-4-android.appspot.com/

O projeto [mobile][2] deverá ser baixado no Eclipse para que consiga ser emulado/instalado em um dispositivo Android.

#### Como utilizar

Primeiramente, no projeto web, será necessário cadastrar as [informações do projeto][7], no menu [Dados do Sistema - Configuração][4]. Feito isso, o nosso aplitativo android já poderá ser utilizado.

Ao abrir o aplicativo, temos 2 opções: Registrar e Histórico. Ao clicar em Registrar, o aplicativo irá solicitar seu nome e email. Quando clicar em Registrar, ele irá se comunicar com o aplicativo web para receber o *senderId*, que é necessário para fazer o registro no GCM.

Ao término, o aplicativo vai voltar para a tela principal com o botão "registrar" bloqueado. Já podemos receber notificações.

No projeto web, você poderá ver na [lista de usuários][5] que o seu usuário já foi cadastrado. Agora só resta escrever uma notificação e enviar, através do menu [Notificações - Enviar][6].

Todas as notificações recebidas, são gravadas no Histórico do aplicativo android.

```
Autor: Douglas Tavares de Azevedo Japiassu
* Graduando em Engenharia de Software pela UFG
```


[1]:https://github.com/douglasjapiassu/integracao/tree/master/send-notification-4-android
[2]:https://github.com/douglasjapiassu/integracao/tree/master/ReceiveNotification
[3]:http://1.send-notification-4-android.appspot.com/
[4]:http://1.send-notification-4-android.appspot.com/editarConfiguracao.jsf
[5]:http://1.send-notification-4-android.appspot.com/listaUsuarios.jsf
[6]:http://1.send-notification-4-android.appspot.com/enviarNotificacao.jsf
[7]:https://github.com/douglasjapiassu/integracao/blob/master/Configura%C3%A7%C3%B5es%20do%20App.rar
