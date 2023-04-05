# golden-raspberry-awards

API para carregar e trazer os ganhadores de prêmios, calculando os com menor e maior intervalo entre os anos vitoriósos.

Essa api foi construina com Java 11/Maven/Spring-Boot.

Logo, para rodar a aplicação, pode tanto ser utilizada IDEs ou através do comando maven na raiz do projeto: `mvn spring-boot:run`

É importante lembrar que esse é um teste técnico e que ainda tem requisitos a atender e melhorar dentro do que foi solicitado.

Após o início da API, pode ser executada a chamada para consulta das informações

curl --location 'http://localhost:8080/texoit/awards/interval'


Como itens a desenvolver ou melhorias, ainda estão:

- implementar o nível 3 de maturidade do maturidade de Richardson
- implementar testes, tanto únitários quanto de integração
- melhorar o processo de importação de dados via arquivo, tratando e prevenindo erros de formato incorreto