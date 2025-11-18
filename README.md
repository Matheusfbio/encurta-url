# Encurtador de URLs 

Este é um aplicativo Android nativo que permite aos usuários encurtar URLs e visualizar uma lista de URLs previamente encurtadas. O projeto foi construído com Kotlin e segue as práticas mais modernas de desenvolvimento Android, utilizando componentes do Jetpack para criar uma aplicação robusta, reativa e de fácil manutenção.

https://github.com/user-attachments/assets/6af04d58-bf18-4ecb-9b13-2f03eda6f209

## ✨ Funcionalidades

- **Listagem de URLs**: Exibe uma lista de URLs que já foram encurtadas.
- **Encurtar Nova URL**: Permite ao usuário inserir uma nova URL para ser encurtada.
- **Copiar para Área de Transferência**: Funcionalidade para copiar a URL encurtada com um único toque.
- **Interface Reativa**: A UI reage a diferentes estados da aplicação, exibindo indicadores de carregamento, listas vazias, mensagens de erro ou o conteúdo de sucesso.

## 🏛️ Arquitetura e Tecnologias

O projeto utiliza a arquitetura **MVVM (Model-View-ViewModel)**, promovendo uma separação clara de responsabilidades entre a UI, a lógica de apresentação e os dados.

### Tecnologias Utilizadas

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Componentes do Jetpack**:
  - **ViewModel**: Gerencia o estado da UI e a lógica de apresentação de forma consciente ao ciclo de vida.
  - **LiveData**: Utilizado para notificar a UI sobre mudanças no estado dos dados.
  - **Flow**: Empregado para operações assíncronas e para criar um fluxo de dados reativo desde o repositório até a UI.
  - **Navigation Component**: Gerencia a navegação entre as telas (Fragments) da aplicação.
  - **ViewBinding**: Acessa as views do XML de forma segura e eficiente.
- **Assincronismo**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) para gerenciar threads e operações em segundo plano, como chamadas de rede.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) e [Gson](https://github.com/google/gson) para realizar chamadas de API e converter objetos JSON.
- **UI**: `RecyclerView` para exibir a lista de URLs de forma otimizada e componentes do [Material Design](https://material.io/design) para uma aparência moderna.
- **Injeção de Dependência**: Injeção manual através de uma `ViewModelProvider.Factory` personalizada, que constrói os ViewModels com seus repositórios necessários.

## 💡 Padrões de Código e Destaques

O código-fonte demonstra padrões modernos de desenvolvimento Android:

### 1. Gerenciamento de Estado da UI com `sealed interface`

O estado da tela de listagem de URLs (`UrlsFragment`) é modelado com uma `sealed interface` chamada `UrlsState`. Isso permite representar todos os estados possíveis da UI de forma explícita e segura:

- `UrlsState.Loading`: Exibido enquanto os dados estão sendo carregados.
- `UrlsState.Success`: Exibido quando os dados são carregados com sucesso, contendo a lista de URLs.
- `UrlsState.Empty`: Um estado específico para quando a lista de URLs está vazia.
- `UrlsState.Failed`: Exibido quando ocorre um erro, contendo a mensagem de erro.

### 2. UI Reativa com `Flow` e `LiveData`

O `UrlsViewModel` transforma o fluxo de dados do `repository.getAll()` em `LiveData` de forma reativa. Isso significa que qualquer mudança na fonte de dados (seja um banco de dados local ou uma API) será refletida automaticamente na UI, sem a necessidade de chamadas manuais.

```kotlin
val state: LiveData<UrlsState> = repository.getAll()
    .map { urls ->
        if (urls.isNotEmpty()) UrlsState.Success(urls)
        else UrlsState.Empty
    }
    .onStart { emit(UrlsState.Loading) }
    .catch { error -> UrlsState.Failed(...) }
    .asLiveData()
```

### 3. Ações de Disparo Único com a Classe `Event`

Para ações que devem ser executadas apenas uma vez (como exibir um `Toast` ou um indicador de carregamento para uma ação específica), o projeto utiliza uma classe `Event` que "envelopa" o conteúdo da ação. Isso evita que a ação seja disparada novamente em caso de reconstrução da UI (como uma rotação de tela), garantindo uma experiência de usuário consistente.

```kotlin
private val _action = MutableLiveData<Event<UrlsAction>>()
val action: LiveData<Event<UrlsAction>> = _action
```

## 🚀 Como Executar o Projeto

1. Clone este repositório.
2. Abra o projeto no Android Studio.
3. Execute o aplicativo em um emulador ou dispositivo físico.
