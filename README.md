# Repositório para o trabalho T4 da cadeira de Teoria da Informação
### Objetivo do T4:
Elaborar um **cifrador simétrico de bloco** (para blocos de 48 bits), que permita criptografar e decriptografar um arquivo (de qualquer tamanho) utilizando uma chave de 32 bits (o valor da chave é inicialmente fornecido pelo usuário como valor de entrada do processo de **key scheduling**). Implementar também a camada do modo de operação, que deve ser o modo **CBC**.
## Modo de usar
Na raíz do projeto está o executável `entrypting.jar`, a partir dele é possível rodar o programa passando argumentos no formato `chave:valor` para parametrizá-lo (apesar de todos os parâmetros terem valores padrão, fazendo com que nenhum seja obrigatório).
### Exemplo de execução: 
```bash
$ java -jar encrypting.jar action:encryptAndDecrypt file:alice29.txt key:28292A2B

---- Par▒metros aplicados: ----
        action:encryptAndDecrypt
        file:alice29.txt
        key:28292A2B
        USER_KEY_SIZE:4
        BLOCK_SIZE:6
-------------------------------

key: [40, 41, 42, 43]
Encrypted C:\git\encrypting\alice29.txt to C:\git\encrypting\alice29.ecc
Decrypted C:\git\encrypting\alice29.ecc to C:\git\encrypting\alice29.dcc

```
Argumentos recebidos por linha de comando:
* **action**
  * Tipo: String
  * Valor padrão: ´encryptAndDecrypt´
  * Descrição: Define qual fluxo será executado, tem as seguintes opções:
    * `encrypt`: realiza o ciframento em blocos do arquivo especificado para um arquivo `[nome].ecc`;
    * `decrypt`: realiza o deciframento do arquivo especificado para um arquivo `[nome].dcc`;
    * `encryptAndDecrypt`: realiza ambas, gera os arquivos `[nome].ecc` --> `[nome].dcc`;

* **file**
  * Tipo: String
  * Valor padrão: `alice29.txt`
  * Descrição: Caminho do arquico que será codificado (caso a ação seja `encrypt` ou `encryptAndDecrypt`) ou decodificado (caso a ação seja `decrypt`).

* **key**
  * Tipo: String
  * Valor padrão: `2A2B2C2D`
  * Descrição: chave de 32 bits que será usada para o Key Schedule do processo de ciframento. Deve estar no formato de 4 números **hexadecimais** concatenados.

 **Obs.:** `USER_KEY_SIZE` e `BLOCK_SIZE` não são editáveis.

---
## Passos da Encriptação


### CBC

### Key Schedule

### Ciframento
#### Transposição
#### Substituição
