# Trabalho Final
## Implementação da Interface do Compilador

#### Geral
- [X] Janela fixa 1500x800 (não redimensionável).  
- [X] Permitir apenas minimizar e fechar.  
- [X] Disposição dos elementos conforme especificação (Figura 1).  
- [X] Permitir redimensionamento vertical entre editor e área de mensagens.  

#### Editor
- [X] Mostrar número de linhas à esquerda, iniciando em 1 (não editável).  
- [X] Barras de rolagem horizontal e vertical, visíveis mesmo sem texto.  

#### Área de Mensagens
- [X] Texto não editável.  
- [X] Barras de rolagem horizontal e vertical, visíveis mesmo sem mensagens.  

#### Barra de Status
- [X] Mostrar pasta e nome do arquivo aberto.  

#### Barra de Ferramentas
- [X] Incluir botões (mesmo tamanho), com ícone, nome completo e atalho associado:  
  - Novo [Ctrl+N]  
  - Abrir [Ctrl+O]  
  - Salvar [Ctrl+S]  
  - Copiar [Ctrl+C]  
  - Colar [Ctrl+V]  
  - Recortar [Ctrl+X]  
  - Compilar [F7]  
  - Equipe [F1]  

#### Funcionalidades
- [ ] **Novo** → limpar editor, mensagens e status.  
- [ ] **Abrir** → selecionar arquivo `.txt`, carregar no editor, limpar mensagens, atualizar status.  
  - Caso não selecione nada → manter estado anterior.  
- [ ] **Salvar** →  
  - Arquivo novo → solicitar pasta/nome, salvar em `.txt`, limpar mensagens, atualizar status.  
  - Arquivo existente → salvar alterações, limpar mensagens, manter status.  
- [ ] **Copiar / Colar / Recortar** → comportamento idêntico a editores convencionais.  
- [ ] **Compilar** → exibir mensagem na área de mensagens:  
- [ ] **Equipe** → exibir nomes dos integrantes na área de mensagens.  
