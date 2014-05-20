send-notification-4-android
===============

Aplicativo web para o cadastro de usuários via servlet e envio de notificações para os mesmos.
Detalhes da implementacao
-------
Tecnologias utilizadas:
* JSF 2: utilizamos o framework JavaServer Faces, seguindo o modelo arquitetural MVC e o uso de componentes visuais para a construcao das interfaces graficas (front-end);
* Twitter Bootstrap: framework para front-end, define uma serie de definicoes CSS e codigo JavaScript para a criacao de layouts na web, incluindo tipografica, formularios, tabelas, etc.
* Objectify: framework que define uma API alto nivel para lidar com o mecanismo de persistencia do App Engine, o DataStore.
* Google App Engine: configuracoes necessarias para executar a aplicacao no ambiente cloud do Google;
* Google Cloud Message: serviço do GAE (google app engine) para envio de notificações da nuvem.