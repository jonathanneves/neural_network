# Neural Network - Backpropagation

Neural Network to read letters in ASCII ART from txt files

## Instruções:

### Como realizar o treinamento:

Crie arquivos txt na pasta Letters com todos os inputs e na última linha do txt é reservada para a saída desejada.

	 * Os inputs devem seguir o padrão: '.' = -1 '#' = 1  'etc' = 0;
	 
	 * Os outputs devem seguir o padrão: '.' = -1 'etc' = 1;
	 
**OBS.:** Todos os arquivos deve ter o mesmo número de inputs e de output.
Exemplo de um txt de Treinamento (LETRA A):

```
...#...
...#...
...#...
..#.#..
..#.#..
.#...#.
.#####.
.#...#.
.#...#.
A......
```

### Como realizar o teste:

Crie um arquivo chamado TESTE.txt, coloque apenas os inputs para Rede Neural classifica-la no teste. 

**OBS.:** Deve ter o mesmo número de inputs que os outros arquivos e não precisa da saída desejada.
Exemplo de um txt de Teste (LETRA B) com ruídos:
```
####@#.
.#...@#
.#.@..#
.#####.
.@....a
.#....#
.#.v..#
.#a...#
####x#.
```
### Como configurar o algoritmo

* Na classe Variables você pode definir o número de LAYER j desejado. Recomendado ser igual ao número de inputs;

* Na Classse Constants você pode definir o número de epócas em EPOCHS. É o número de vezes que o algoritmo vai realizar o treinamento antes de parar.
 
### Execução

1. Configure o algoritmo  de sua forma;
2. Realize o treinamento;
3. Realize o teste do arquivo TESTE.txt.
