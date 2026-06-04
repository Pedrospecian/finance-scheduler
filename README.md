-Optei por delegar o cálculo da taxa de transferência ao Spring Boot para impedir cenários onde um usuário dispare o endpoint de criação de transação utilizando um valor incorreto de taxa de transferência.

-Quando o usuário altera o campo de valor ou o de data de transferência, o sistema envia ambos os dados para um endpoint específico para calcular a taxa de transferência. Esse cálculo poderia ser realizado no lado do frontend para evitar chamadas à API, mas eu optei por implementar um endpoint específico para isso para que a lógica do cálculo da taxa de transferência ficasse em apenas um lugar. Dessa maneira, caso essa lógica precisasse ser alterada no futuro, bastaria fazer alterações em um único lugar (no caso, a função calcularTaxa() da classe TransactionController)

-Decidi implementar páginas de criação e listagem de contas para que o usuário pudesse cadastrá-las diretamente pela aplicação

-TODO:
	
	- implementar paginação
	- substituir sistema de busca atual por uma busca acoplada ao sistema de paginação (com escalabilidade em mente)
	- refatorar páginas de conta e transação para que elas fiquem separados em módulos (com escalabilidade em mente)
	- informar os passo-a-passos da execução do sistema
	- informar as versões de linguagem utilizadas