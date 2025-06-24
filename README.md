EducaInclusão – Aplicativo para Apoio à Inclusão de Alunos com TEA

O EducaInclusão é um aplicativo educacional voltado para escolas públicas, com o objetivo de facilitar o acompanhamento de alunos com Transtorno do Espectro Autista (TEA) por professores, responsáveis e os próprios alunos.

O app foi desenvolvido no Android Studio com a linguagem Kotlin, e pretendendo utilizar o Firebase para autenticação e armazenamento em nuvem dos dados.

Tecnologias Utilizadas

Android Studio,Kotlin,XML para layouts e futuramente utilizaremos o Firebase Realtime Database e Firebase Authentication

Perfis de Usuário

O app possui três perfis principais:Professor,Responsável e Aluno
Cada um possui telas e funcionalidades específicas, conectadas entre si via banco de dados.

 Funcionalidades Planejadas

 
adicionar nome de escola, Login e Cadastro com Firebase,

Painel do Professor com lista de turmas dos alunos(lista de todas as turmas que possuem alunos com TEA),atividade(area para passar atividade apropriada e buscar no banco de dados uma atividade basica atraves de uma foto ),relatorio(escrever relatorio sobres os alunos para enviar para os responsaveis),agendamento semanal(professor pode criar uma agenda especifica para os alunos),notificação(onde recebem notificação da escolar,dos resposaveis e informarções de alunos),mensagens(area onde pode dialogar sobre o comportamento do aluno) e configuração,

Painel do Responsável com visualização de relatórios e rotina,mensagem(area em que o pai pode manda mensagens pra perguntar sobre o aluno), tarefas do aluno(area em que o responsavel pode visualizar as tarefas em que o aluno fez ou ainda vai faazer), configuração

Painel do Aluno com rotina visual, emojis de humor e PECS, Registro de humor e participação nas atividades, Comunicação básica entre escola e família, Configurações de acessibilidade (modo sensível, fonte ampliada)


Situação Atual do Projeto (Desenvolvimeneto)

As telas foram criadas com XML e Kotlin

A estrutura de navegação entre Activities já está configurada

Algumas telas ainda não estão funcionando totalmente por conta de erros em execução ou conexão com o Firebase

A lógica principal ainda estar em desenvolvimento, utilizando somente APIs de dados (Firebase)



---

🔒 Segurança

Login com e-mail e senha via Firebase Authentication

Regras de segurança no banco de dados para garantir acesso controlado por perfil
