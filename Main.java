package solver;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

class ComplexNumber { //Classe que representa um número complexo

    private double realPart, imaginaryPart;

    public ComplexNumber(double realPart, double imaginaryPart) { //Construtor

        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public  ComplexNumber(String number) {

        char c[] = new char[number.length()]; //Cria um array de chars com o mesmo tamanho da String number
        for (int i = 0; i < number.length(); i++) { //Põe cada caractere em uma posição do array de char
            c[i] = number.charAt(i);
        }

        String realPartString = "";
        String imaginaryPartString = "";
        //ANALISA A QUANTIDADE DE SINAIS E A PRESENÇA DE "i" NO ARRAY
        int numberOfSignals = 0; //Também serve de contador para signalsIndex[]
        boolean containsAnI = false;
        int numberOfParts = 0; //Indica a quantidade de partes

        String parts[] = number.split("[+-]"); //Divide a string utilizando onde houver "+" ou "-"
        numberOfParts = parts.length;
        int[] signalsIndex = new int[2]; //Array que guarda as posições (index) dos sinais localizados

        for (int i = 0; i < c.length; i++) { //Percorre o array de caracteres
            if (c[i] == '-' || c[i] == '+') { //Conta quantos sinais de "+" ou de "-" existem no array
                signalsIndex[numberOfSignals] = i; //Guarda o índice do sinal no array signalsIndex
                numberOfSignals++;
            }
            if ((c[i]) == 'i') { //Pesquisa se existe uma letra "i" no array
                containsAnI = true;
            }
        }

        //ANALISA OS POSSÍVEIS RESULTADOS GERADOS ACIMA
        if (numberOfSignals > 2 || (numberOfSignals == 2 && containsAnI == false)) { //Lança exceção se houver mais de dois sinais ou dois sinais e a letra "i"
            System.out.println("Sinais demais");
            throw new IllegalArgumentException("Too much signals.");
        }

        if (containsAnI == true && (parts.length == 1 || (parts.length == 2 && parts[0].contentEquals("")))) { //Se houver um ou nenhum sinal e a letra "i" deve ser só parte imaginária
            boolean lonelyI = false; //true se a String não contiver dígitos, somente a letra "i" e akgum sinal
            if (number.equals("i") || number.equals("+i")) { //Se for apenas "i" ou "+i"
                imaginaryPartString = "1";
                lonelyI = true;
            }
            if (number.equals("-i")) { //Se for apenas "-i"
                imaginaryPartString = "-1";
                lonelyI = true;
            }
            if (!lonelyI) { //Se a String number não for formada apenas por "i", "+i" ou "-i"
                imaginaryPartString = number.substring(0, number.length()-1); //Adiciona o valor ao string imaginaryPartString
            }
            realPartString = "0"; //realPartString fica zero em todas as situações
        }

        if (containsAnI == false && ((numberOfSignals == 1 && parts[0].contentEquals("")) || numberOfSignals == 0)) { //Se houver um ou nenhum sinal sem a letra "i" deve ser só parte real
            realPartString = number; //Adiciona a letra ao string realPartString
            imaginaryPartString = "0"; //imaginaryPartString fica zero
        }

        if (containsAnI == true && (parts.length == 3 || (parts.length == 2 && parts[0].contentEquals("") == false))) { //Se contém "i" e duas ou três partes deve haver parte imaginária e real
            if (numberOfSignals == 1) { //Se houver apenas um sinal, deve estar no meio, antes da parte imaginária
                realPartString = parts[0];

                String secondPart = number.substring(signalsIndex[0],number.length()); //A segunda parte do string. Pode conter sinal de "+" ou de "-" ou nenhum.
                boolean lonelyI = false;
                if (secondPart.equals("i") || secondPart.equals("+i") ) {
                    imaginaryPartString = "1";
                    lonelyI = true;
                }
                if (secondPart.equals("-i")) {
                    imaginaryPartString = "-1";
                    lonelyI = true;
                }
                if (!lonelyI) {
                    imaginaryPartString = number.substring(signalsIndex[0],number.length()-1); //Adiciona a imaginaryPartString a partir do sinal o trecho do sinal e encerra antes do "i"
                }
            }

            if (numberOfSignals == 2) {
                realPartString = number.substring(0, signalsIndex[1]); //Adiciona a realPartString a substring de number do início e encerra antes do segundo sinal
                imaginaryPartString = number.substring(signalsIndex[1],number.length()-1); //Adiciona a imaginaryPartString a partir do sinal e encerra antes do
            }
        }

        this.realPart = Double.valueOf(realPartString);
        this.imaginaryPart = Double.valueOf(imaginaryPartString);
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }
}

class ListaDePares { //Classe que armazena a lista de pares

    private int[][] list;
    private int size;

    public ListaDePares() {
        this.list = new int[0][2];
        this.size = 0;
    }

    public void add(int number_1, int number_2){

        this.size++;
        int[][] temporaryList = this.list;
        this.list = new int[this.size][2];
        for (int i = 0; i < this.size-1; i++) {
            this.list[i] = temporaryList[i];
        }
        int[] numbers = {number_1, number_2};
        this.list[this.size-1] = numbers;

    }

    public int[] getPair(int n){

        return this.list[n];
    }

    public int getSize(){

        return this.size;
    }
}

public class Main {

    static class ComplexOperations { //Classe que reúne as operações com números complexos

        static ComplexNumber add(ComplexNumber num1, ComplexNumber num2) { //Soma número complexos

            return new ComplexNumber( num1.getRealPart() + num2.getRealPart(), num1.getImaginaryPart() + num2.getImaginaryPart());
        }

        static ComplexNumber subtract(ComplexNumber num1, ComplexNumber num2) { //Subtrai número complexos

            return new ComplexNumber( num1.getRealPart() - num2.getRealPart(), num1.getImaginaryPart() - num2.getImaginaryPart());
        }

        static ComplexNumber multiply(ComplexNumber num1, ComplexNumber num2) {

            double newRealPart, newImaginaryPart;
            newRealPart = num1.getRealPart() * num2.getRealPart() - num1.getImaginaryPart() * num2.getImaginaryPart();
            newImaginaryPart = num1.getImaginaryPart() * num2.getRealPart() + num2.getImaginaryPart() * num1.getRealPart();

            return new ComplexNumber(newRealPart, newImaginaryPart);
        }

        static ComplexNumber divide(ComplexNumber num1, ComplexNumber num2) {

            double newRealPart = (num1.getRealPart() * num2.getRealPart() + num1.getImaginaryPart() * num2.getImaginaryPart()) / (num2.getRealPart() * num2.getRealPart() + num2.getImaginaryPart() * num2.getImaginaryPart());
            double newImaginaryPart = (num1.getImaginaryPart() * num2.getRealPart() - num1.getRealPart() * num2.getImaginaryPart()) / (num2.getRealPart() * num2.getRealPart() + num2.getImaginaryPart() * num2.getImaginaryPart());

            return new ComplexNumber(newRealPart, newImaginaryPart);
        }

    }

    static class MatrixUtils { //Classe que manipula as linhas e colunas

        static ComplexNumber complexZero = new ComplexNumber(0.0, 0.0);

        static void changeLines(ComplexNumber[][] matrix, int a, int b) {

            ComplexNumber[] linhaTemporaria = matrix[a];
            matrix[a] = matrix[b];
            matrix[b] = linhaTemporaria;
        }

        static void changeColumns(ComplexNumber[][] matrix, int a, int b) {

            ComplexNumber[] colunaTemporaria = new ComplexNumber[matrix.length];
            for (int i = 0; i < matrix.length; i++) { //Adiciona a coluna "a" à colunaTemporaria
                colunaTemporaria[i] = matrix[i][a];
            }

            for (int i = 0; i < matrix.length; i++) { //Adiciona a coluna "b" à coluna "a"
                matrix[i][a] = matrix[i][b];
            }

            for (int i = 0; i < matrix.length; i++) { //Adiciona a colunaTemporaria à coluna "b"
                matrix[i][b] = colunaTemporaria[i];
            }
        }

        static void print(ComplexNumber[][] matrix){
            for (ComplexNumber[] line: matrix) { //Para cada linha da matriz
                for (ComplexNumber d : line) { //Para cada elemento da linha
                    System.out.print(d.getRealPart() + " " + d.getImaginaryPart() + "i ");
                }
                System.out.println();
            }
        }

        static void print (ComplexNumber[] array){
            for (ComplexNumber d : array){
                System.out.print(d.getRealPart() + " " + d.getImaginaryPart() + "i ");
            }
            System.out.println();
        }

        static void printFloat(ComplexNumber[][] matrix) {
            for (ComplexNumber[] line : matrix) { //Para cada linha da matriz
                for (ComplexNumber d : line) { //Para cada elemento da linha
                    //Transforma em float
                    System.out.print((float)d.getRealPart() + " " + (float)d.getImaginaryPart() + "i ");
                }
                System.out.println();
            }
        }

        static void printFloat(ComplexNumber[] array){
            for (ComplexNumber d : array){
                System.out.print((float)d.getRealPart() + " " + d.getImaginaryPart() + "i ");
            }
            System.out.println();
        }

        static ComplexNumber[][] removeNullRows(ComplexNumber[][] matrix) { //BUG CONHECIDO: ESSE MÉTODO SÓ FUNCIONA SE HOUVER ALGUMA LINHA TOTALMENTE ZERADA, SE NÃO, RESULTA EM ERRO "out of bounds"
            int lines = matrix.length;
            int columns = matrix[0].length;

            //Verifica as linhas totalmente zeradas e armazena o número de cada uma no array "lineOfZeros"
            int[] lineOfZeros = new int[0]; //Lista as linhas zeradas
            for (int i = 0; i < matrix[0].length; i++) {
                boolean isLineZeros = true;
                for (int j = 0; j < matrix.length; j++) { //Verifica se toda a linha é igual a zero
                    if (matrix[i][j].getRealPart() != 0.0 || matrix[i][j].getImaginaryPart() != 0.0) { //Se o valor não for igual a zero
                        isLineZeros = false;
                        break;
                    }
                }
                if (isLineZeros == true) { //Se toda a linha for igual a zero, aumenta o tamanho do array "linhas" e armazena o índice lá.
                    int[] linhasTemp = lineOfZeros;
                    lineOfZeros = new int[linhasTemp.length + 1]; //Cria o array com um espaço a mais
                    for (int j = 0; j < lineOfZeros.length - 1; j++) {
                        lineOfZeros[j] = linhasTemp[j]; //Copia os valores antigos de "linhasTemp" as mesmas posições de "linhas"
                    }
                    lineOfZeros[lineOfZeros.length - 1] = i; //Armazena o índice da linha zerada na nova posição do array "linhas"
                }
            }
            //Cria uma matriz com a quantidade de linhas não zeradas
            ComplexNumber[][] cleanMatrix = new ComplexNumber[lines - lineOfZeros.length][columns];
            int contador = 0; //Contador de linhas para "cleanMatrix"
            //Adiciona somente as linhas não zeradas à nova matriz
            for (int i = 0; i < lines; i++) {
                boolean isThisLineZero = false;
                for (int line:lineOfZeros) { //Verifica se a linha atual é zerada
                    if(line == i){
                        isThisLineZero = true;
                    }
                }
                if(isThisLineZero == false) {
                    cleanMatrix[contador] = matrix[i] ;
                    contador++;
                }
            }
            return cleanMatrix;
        }

        static void diagonalOrganizer(ComplexNumber[][] matrix, ListaDePares lista) { //Se a matriz tiver menos linhas do que variáveis, não faz nada
                for (int i = 0; i < matrix.length-1; i++) { // O "-1" evita que se percorra a última coluna, a de constantes.

                    boolean lineChanged = false;
                    boolean rowChanged = false;

                    if (matrix[i][i].getRealPart() == 0.0 && matrix[i][i].getImaginaryPart() == 0.0) { //Verifica se algum valor da diagonal é igual a zero

                        for (int j = i+1; j < matrix.length; j++) { //Se houver um valor zero na diagonal, procura valor diferente de zero abaixo
                            if (matrix[j][i].getRealPart() != 0.0 || matrix[j][i].getImaginaryPart() != 0.0) { //Se houver um valor diferente de zero, troca as linhas
                                MatrixUtils.changeLines(matrix, i, j);
                                lineChanged = true; //Registra que a linha já foi trocada
                                break;
                            }
                        }

                        if (lineChanged == false) { //Se não houver valores diferentes de zero abaixo, procura valores à direita
                            for (int j = i+1; j < matrix[0].length - 1; j++) {
                                if(matrix[i][j].getRealPart() != 0.0 || matrix[i][j].getImaginaryPart() != 0.0) { //Se houver um valor diferente de zero, troca as colunas
                                    MatrixUtils.changeColumns(matrix, i, j);
                                    rowChanged = true;
                                    lista.add(i, j);
                                    break;
                                }
                            }
                        }

                        if ((lineChanged == false && rowChanged == false) && i != matrix.length-1) { //Se não obteve foi capaz de trocar linha nem coluna e não for a última linha, buscar no quadrante restante
                            for (int j = i+1; j < matrix.length - 1; j++) { //Variável "j" identifica a linha
                                for (int k = j; k < matrix[0].length - 1; k++) { //Variável "k" identifica a coluna
                                    if (matrix[j][k].getRealPart() != 0.0 || matrix[j][k].getImaginaryPart() != 0.0) {
                                        MatrixUtils.changeLines(matrix, i, j);
                                        MatrixUtils.changeColumns(matrix, i, k);
                                        lista.add(i, k);
                                        lineChanged = true;
                                        rowChanged = true;
                                    }
                                }
                            }
                        }

                        if (lineChanged == false && rowChanged == false) { //Se todos os valores do quadrante restante forem iguais a zero, encerra a verificação de diagonal
                            // TESTE DE ALTERAÇÃO PARA CIMA
                            for (int j = i-1; j>=0; j--){ //Percorre as linhas de baixo pra cima
                                if((matrix[j][i].getRealPart() != 0.0 || matrix[j][i].getImaginaryPart() != 0.0) && (matrix[i][j].getRealPart() != 0.0 && matrix[i][j].getImaginaryPart() != 0.0)){ //Verifica se a linha analisada pode ser trocada sem incluir um novo zero na diagonal
                                    MatrixUtils.changeLines(matrix, i, j);
                                }
                            }
                        }

                    }
                }
        }
    }

    public static void main(String[] args) {

        String inputFileName = "";
        String outputFileName = "";
        //Identificar os nomes dos arquivos
        for (int i = 0; i < args.length; i++) {
            if (args[i].contentEquals("-in")) {
                inputFileName = args[i+1];
            }
            if (args[i].contentEquals("-out")) {
                outputFileName = args[i+1];
            }
        }

        //String fileName = "/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/in.txt";
        //File inputFile = new File(fileName);
        File inputFile = new File(inputFileName);

        try(Scanner scanner = new Scanner(inputFile)) { //Tenta ler o arquivo de entrada

            final int numVar = scanner.nextInt(); //Lê o número de variáveis do sistema (nº de colunas - 1)
            System.out.println("numVar = " + numVar);
            final int numEq = scanner.nextInt(); //Lê o número de equações do sistema (nº de linhas)
            System.out.println("numEq = " + numEq);
            ComplexNumber[][] matrix = new ComplexNumber[numEq][numVar + 1]; //Cria a matriz numEq x numVar + 1 (por causa da coluna de constantes)
            for (int i = 0; i < numEq; i++) { //Preenche os elementos da matriz
                for (int j = 0; j < numVar + 1; j++) {
                    String complexNumber = scanner.next();
                    System.out.print("Valor Lido em [" + i + "][" + j + "] = ");
                    System.out.println(complexNumber);
                    matrix[i][j] = new ComplexNumber(complexNumber);
                }
            }

            System.out.println();
            System.out.println("A matriz do sistema é: ");
            MatrixUtils.print(matrix);


            //PRIMEIRA PARTE DO ALGORITMO - ORGANIZAR DIAGONAIS
            ListaDePares lista = new ListaDePares();

            MatrixUtils.diagonalOrganizer(matrix, lista);

            ComplexNumber complexZero = MatrixUtils.complexZero;

            //FIM DA PRIMEIRA PARTE - DIAGONAIS ORGANIZADAS

            System.out.println();
            System.out.println("A organização da diagonal resultou em: ");
            MatrixUtils.printFloat(matrix);
            System.out.println();


            //TESTANDO ESCALONAR ANTES

            //ESCALONAMENTO DA MATRIZ
            if(true) { //Se houver solução, inicia o solucionamento
                for (int linha = 0; linha < matrix.length; linha++) { //Linha base para o zeramento
                    for (int i = linha + 1; i < matrix.length; i++) { //Linha a ser zerada
                        if (matrix[i][linha].getRealPart() != 0.0 || matrix[i][linha].getImaginaryPart() != 0.0) { // TESTE Para evitar divisão por zero
                            ComplexNumber multiplicador = ComplexOperations.multiply(ComplexOperations.divide(matrix[linha][linha],matrix[i][linha]), new ComplexNumber(-1, 0)); //Define o multiplicador da linha (Qual o valor que multiplicado pela diagonal matriz[linha][linha] é igual a zero.
                            ComplexNumber tempLine[] = new ComplexNumber[matrix[0].length];
                            for (int j = 0; j < matrix[0].length; j++) {
                                tempLine[j] = ComplexOperations.add(matrix[linha][j],ComplexOperations.multiply(matrix[i][j], multiplicador)); //Multiplica cada elemento da linha a ser zerada por multiplicador e soma com o a linha base para zeramento
                            }
                            boolean containsInfinite = false;
                            for (int j = 0; j < tempLine.length; j++) { //Verifica se algum dos objetos presentes em tempLine possui algum valor infinito
                                if (tempLine[j].getRealPart() == Double.POSITIVE_INFINITY || tempLine[j].getRealPart() == Double.POSITIVE_INFINITY || tempLine[j].getImaginaryPart() == Double.POSITIVE_INFINITY || tempLine[j].getImaginaryPart() == Double.POSITIVE_INFINITY) {
                                    containsInfinite = true;
                                    break;
                                }
                            }
                            if (containsInfinite == false) {
                                for (int j = 0; j < matrix[0].length; j++) {
                                    matrix[i][j] = ComplexOperations.add(matrix[linha][j],ComplexOperations.multiply(matrix[i][j], multiplicador)); //Multiplica cada elemento da linha a ser zerada por multiplicador e soma com o a linha base para zeramento
                                }
                            }
                        }
                    }
                }
            }

                System.out.println();
                System.out.println("A matriz escalonada é: ");
                MatrixUtils.printFloat(matrix);
                System.out.println();
            //TESTANDO ESCALONAR ANTES


            //SEGUNDA PARTE DO ALGORITMO - VERIFICAR SE EXISTEM SOLUÇÕES
            //Verificar se existe uma linha igual a zero com constante diferente de zero.

            boolean isNoSolution = false;
            int zerosLinesCounter = 0; //Quantas linhas zeradas existem

            for (int i = 0; i < matrix.length; i++) { //Para linha da coluna
                boolean isLineZero = true;
                for (int j = 0; j < matrix[0].length - 1; j++) { //Para cada coluna da linha, exceto a última
                    if(matrix[i][j].getRealPart() != 0.0 || matrix[i][j].getImaginaryPart() != 0.0) {
                        isLineZero = false;
                        break;
                    }
                }
                if(isLineZero == true) { //Se a linha tiver apenas zeros, verificar se a constante também é zero.
                    if(matrix[i][matrix[0].length-1].getRealPart() == 0.0 && matrix[i][matrix[0].length-1].getImaginaryPart() == 0.0) { //Se o último valor da linha também for zero, contar como linha totalmente zerada.
                        zerosLinesCounter++;
                    } else { //Se a linha zerada possuir constante diferente de zero é uma contradição.
                        int linhaAnalisada = i+1;
                        isNoSolution = true; //Registra se a o sistema é sem solução.
                        System.out.println("A linha " + linhaAnalisada + " apresenta uma contradição. NÃO EXISTE RESULTADO.");
                        System.out.println();

                        //GRAVA MENSAGEM DE ERRO
                        try (FileWriter outputFile = new FileWriter(outputFileName)) { //Tenta criar o arquivo de saída
                        //try (FileWriter outputFile = new FileWriter("/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/out.txt")) { //Tenta criar o arquivo de saída
                            outputFile.write("No solutions"); //Escreve no arquivo e pula uma linha (com separador para Linux)
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                            System.out.println("Erro de gravação.");
                        }
                        break;
                    }
                }
            }

            //Verificar se o número de equações válidas é igual ao de variáveis
            boolean isSolvable = false;
            if (isNoSolution == false) {
                System.out.println("Número de linhas zeradas : " + zerosLinesCounter);
                System.out.println();

                if (numEq - zerosLinesCounter < numVar) { // "numEq - zerosLinesCounter" é o número de linhas significativas
                    System.out.println("O sistema possui mais variáveis do que equações: INFINITAS SOLUÇÕES.");
                    System.out.println();
                    //GRAVA MENSAGEM DE ERRO
                    try (FileWriter outputFile = new FileWriter(outputFileName)) { //Tenta criar o arquivo de saída
                    //try (FileWriter outputFile = new FileWriter("/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/out.txt")) {
                        outputFile.write("Infinitely many solutions"); //Escreve no arquivo e pula uma linha (com separador para Linux)
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                        System.out.println("Erro de gravação.");
                    }
                } else {
                    isSolvable = true;
                    System.out.println("O sistema pode ser solucionado.");
                }
            }
            //FIM DA SEGUNDA PARTE DO ALGORITMO - EXISTÊNCIA DE SOLUÇÕES VERIFICADAS

            //SANITIZAÇÃO - REMOÇÃO DAS LINHAS ZERADAS PARA ENTREGA AO ESCALONADOR
            if (zerosLinesCounter != 0) {
                matrix = MatrixUtils.removeNullRows(matrix); //Esse método só funciona se houver alguma linha totalmente zerada.
            }
            //FIM DA SANITIZAÇÃO

            //ESCALONAMENTO DA MATRIZ
            if(isSolvable) { //Se houver solução, inicia o solucionamento
                for (int linha = 0; linha < matrix.length; linha++) { //Linha base para o zeramento
                    for (int i = linha + 1; i < matrix.length; i++) { //Linha a ser zerada
                        if (matrix[i][linha].getRealPart() != 0.0 || matrix[i][linha].getImaginaryPart() != 0.0) { // TESTE Para evitar divisão por zero
                            ComplexNumber multiplicador = ComplexOperations.multiply(ComplexOperations.divide(matrix[linha][linha],matrix[i][linha]), new ComplexNumber(-1, 0)); //Define o multiplicador da linha (Qual o valor que multiplicado pela diagonal matriz[linha][linha] é igual a zero.
                            ComplexNumber tempLine[] = new ComplexNumber[matrix[0].length];
                            for (int j = 0; j < matrix[0].length; j++) {
                                tempLine[j] = ComplexOperations.add(matrix[linha][j],ComplexOperations.multiply(matrix[i][j], multiplicador)); //Multiplica cada elemento da linha a ser zerada por multiplicador e soma com o a linha base para zeramento
                            }
                            boolean containsInfinite = false;
                            for (int j = 0; j < tempLine.length; j++) { //Verifica se algum dos objetos presentes em tempLine possui algum valor infinito
                                if (!Double.isInfinite(tempLine[j].getRealPart()) || !Double.isInfinite(tempLine[j].getImaginaryPart())) {
                                    containsInfinite = true;
                                    break;
                                }
                            }
                            if (containsInfinite == false) {
                                for (int j = 0; j < matrix[0].length; j++) {
                                    matrix[i][j] = ComplexOperations.add(matrix[linha][j],ComplexOperations.multiply(matrix[i][j], multiplicador)); //Multiplica cada elemento da linha a ser zerada por multiplicador e soma com o a linha base para zeramento
                                    //System.out.println("matrix["+ i + "][" + j + " = " + matrix[i][j].getRealPart() + " " + matrix[i][j].getImaginaryPart());
                                }
                            }
                        }
                    }
                }

                System.out.println();
                System.out.println("A matriz escalonada novamente é: ");
                MatrixUtils.print(matrix);
                System.out.println();
            }
            //FIM DO ESCALONAMENTO DA MATRIZ


            //TESTE - VERIFICAÇÃO DA EXISTÊNCIA DE SOLUÇÕES APÓS O ESCALONAMENTO
            isNoSolution = false;
            zerosLinesCounter = 0; //Quantas linhas zeradas existem

            for (int i = 0; i < matrix.length; i++) { //Para linha da coluna
                boolean isLineZero = true;
                for (int j = 0; j < matrix[0].length - 1; j++) { //Para cada coluna da linha, exceto a última
                    if(matrix[i][j].getRealPart() != 0.0 || matrix[i][j].getImaginaryPart() != 0.0) {
                        isLineZero = false;
                        break;
                    }
                }
                if(isLineZero == true) { //Se a linha tiver apenas zeros, verificar se a constante também é zero.
                    if(matrix[i][matrix[0].length-1].getRealPart() == 0.0 && matrix[i][matrix[0].length-1].getImaginaryPart() == 0.0) { //Se o último valor da linha também for zero, contar como linha totalmente zerada.
                        zerosLinesCounter++;
                    } else { //Se a linha zerada possuir constante diferente de zero é uma contradição.
                        int linhaAnalisada = i+1;
                        isNoSolution = true; //Registra se a o sistema é sem solução.
                        System.out.println("A linha " + linhaAnalisada + " apresenta uma contradição. NÃO EXISTE RESULTADO.");
                        System.out.println();

                        //GRAVA MENSAGEM DE ERRO
                        try (FileWriter outputFile = new FileWriter(outputFileName)) { //Tenta criar o arquivo de saída
                        //try (FileWriter outputFile = new FileWriter("/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/out.txt")) { //Tenta criar o arquivo de saída
                                outputFile.write("No solutions"); //Escreve no arquivo e pula uma linha (com separador para Linux)
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                            System.out.println("Erro de gravação.");
                        }
                        break;
                    }
                }
            }

            //Verificar se o número de equações válidas é igual ao de variáveis
            isSolvable = false;
            if (isNoSolution == false) {
                System.out.println("Número de linhas zeradas : " + zerosLinesCounter);
                System.out.println();

                if (matrix.length - zerosLinesCounter < numVar) { // "numEq - zerosLinesCounter" é o número de linhas significativas
                    System.out.println("O sistema possui mais variáveis do que equações: INFINITAS SOLUÇÕES.");
                    System.out.println();
                    //GRAVA MENSAGEM DE ERRO
                    try (FileWriter outputFile = new FileWriter(outputFileName)) { //Tenta criar o arquivo de saída
                    //try (FileWriter outputFile = new FileWriter("/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/out.txt")) { //Tenta criar o arquivo de saída
                        outputFile.write("Infinitely many solutions"); //Escreve no arquivo e pula uma linha (com separador para Linux)
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                        System.out.println("Erro de gravação.");
                    }
                } else {
                    isSolvable = true;
                    System.out.println("O sistema pode ser solucionado.");
                }
            }
            //NOVA VERIFICAÇÃO DA EXISTÊNCIA DE SOLUÇÕES APÓS O ESCALONAMENTO


            //SOLUCIONAMENTO DO SISTEMA
            if(isSolvable){
                ComplexNumber[] solutions = new ComplexNumber[matrix.length]; //Vetor de soluções
                Arrays.fill(solutions,complexZero);

                for (int i = matrix.length-1; i >= 0; i--) { //Começa da última linha
                    ComplexNumber[] subtractArray = new ComplexNumber[matrix.length]; //Vetor contendo os valores a serem subtraídos
                    for (int j = 0; j < matrix.length; j++) { //Percorre colunas do para preencher "subtractArray"
                        subtractArray[j] = ComplexOperations.multiply(matrix[i][j],solutions[j]);
                    }
                    ComplexNumber subtract = complexZero;
                    for (ComplexNumber s : subtractArray) { //Soma todos os valores da "subtractArray" na variável "subtract"
                        subtract = ComplexOperations.add(subtract, s);
                    }
                    ComplexNumber solution;
                    solution = ComplexOperations.divide(ComplexOperations.subtract(matrix[i][matrix.length],subtract),matrix[i][i]); //Enfim calcula a solução para a coluna "i"
                    solutions[i] = solution; //Inclui a solução no vetor de soluções "solutions[]"
                    System.out.println("A solução para a coluna " + i + " da matriz é " + solution.getRealPart() + " " + solution.getImaginaryPart() + "i");

                }

                System.out.println();
                System.out.println("As soluções são: ");
                MatrixUtils.printFloat(solutions);

                //FIM DA SOLUÇÃO DO SISTEMA


                //GRAVAÇÃO DO RESULTADO NO ARQUIVO
                try (FileWriter outputFile = new FileWriter(outputFileName)) { //Tenta criar o arquivo de saída
                //try (FileWriter outputFile = new FileWriter("/media/itamar/92A816A5A81687BD/partes de solver/solver_completo/out.txt")) { //Tenta criar o arquivo de saída
                    for (ComplexNumber s:solutions) {
                        if (s.getRealPart() == 0.0 && (s.getImaginaryPart() == 0.0 || s.getImaginaryPart() == -0.0)) {
                            outputFile.write("0.0+0.0i\n"); //Valores zeros com quebra de linha para Linux
                        } else {
                            //transforma em float
                            float f = (float) s.getRealPart();
                            String realValueString = String.valueOf(f);
                            outputFile.write(realValueString); //Escreve a parte real
                            //Verifica se a parte imaginária é positiva ou negativa para a inclusão do sinal de "+" ou "-"
                            if (s.getImaginaryPart() == 0.0) { //Se a parte imaginária for igual a zero
                                outputFile.write("+0.0i\n");
                            }
                            if (s.getImaginaryPart() > 0.0) { //Se a parte imaginária for positiva
                                f = (float) s.getImaginaryPart();
                                outputFile.write("+" + f + "i" + "\n"); //Escreve a parte imaginária no arquivo, com o simbolo de "+" antes, e pula uma linha (com separador para Linux)
                            }
                            if (s.getImaginaryPart() < 0.0) { // Se a parte imaginária for negativa
                                f = (float) s.getImaginaryPart();
                                outputFile.write(f + "i" + "\n"); //Escreve a parte imaginária no arquivo e pula uma linha (com quebra de linha para Linux)
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("Erro de gravação.");
                }
            }

        } catch (Exception e) { //Erro de leitura
            System.out.println(e.getLocalizedMessage());
            System.out.println("Erro de leitura.");
        }
    }
}