# Tier List

## Descrição
Aplicativo Android que permite buscar músicas de um artista e classificá-las em tiers (S, A, B, C, D, E). O usuário digita o nome de um artista, o app busca as músicas na iTunes Search API e exibe os resultados com capa, nome e álbum. O usuário pode então adicionar cada música a um tier da lista.

## API utilizada
- Nome da API: iTunes Search API
- Endpoint utilizado: https://itunes.apple.com/search
- Exemplo de URL consultada: https://itunes.apple.com/search?term=the+weeknd&media=music&entity=song&limit=10&country=br
- Principais dados retornados: nome da faixa (trackName), artista (artistName), álbum (collectionName), capa do álbum (artworkUrl100)

## Funcionalidades
- Entrada de dados pelo usuário (nome do artista)
- Validação de campo vazio antes da consulta
- Consulta à iTunes Search API (pública, sem chave)
- Exibição das músicas com capa, nome e álbum
- Adição de músicas aos tiers S, A, B, C, D e E
- Tratamento de erro de conexão e resposta vazia

## Tecnologias utilizadas
- Kotlin
- Android Studio
- XML
- Volley (requisições HTTP)
- Glide (carregamento de imagens)
- iTunes Search API

## Permissões utilizadas
O aplicativo utiliza a permissão INTERNET para realizar requisições à API pública.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Como executar o projeto
1. Clonar este repositório.
2. Abrir o projeto no Android Studio.
3. Aguardar a sincronização do Gradle.
4. Executar o app em um emulador ou dispositivo físico.
5. Digitar o nome de um artista e clicar em Buscar Músicas.

## Prints do aplicativo
*(adicione prints aqui depois de rodar o app)*

## Autor
Eduardo Vieira Torres dos Santos.
