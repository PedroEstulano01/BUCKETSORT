package com.mycompany.bucketsort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BucketSort {

    public static void bucketSort(int[] vetor, int numeroBaldes, int[] comparacoes, int[] movimentacoes) {

        int valorMinimo = vetor[0];
        comparacoes[0]++;
        for (int i = 1; i < vetor.length; i++) {
            comparacoes[0]++;
            valorMinimo = Math.min(valorMinimo, vetor[i]);
        }

    
        if (valorMinimo < 0) {
            for (int i = 0; i < vetor.length; i++) {
                vetor[i] += Math.abs(valorMinimo);
                movimentacoes[0]++;
            }
        }

    
        int valorMaximo = vetor[0];
        comparacoes[0]++;
        for (int i = 1; i < vetor.length; i++) {
            comparacoes[0]++;
            valorMaximo = Math.max(valorMaximo, vetor[i]);
        }

 
        List<List<Integer>> baldes = new ArrayList<>(numeroBaldes);
        for (int i = 0; i < numeroBaldes; i++) {
            baldes.add(new ArrayList<>());
        }

       
        for (int elemento : vetor) {
            int indiceBalde = (int) (((long) (elemento - valorMinimo) * numeroBaldes) / (valorMaximo - valorMinimo + 1));
            baldes.get(indiceBalde).add(elemento);
            movimentacoes[0]++; 
        }

        // Ordena cada balde usando Quick Sort 
        int indiceVetor = 0;
        for (List<Integer> balde : baldes) {
            quickSort(balde, 0, balde.size() - 1, comparacoes, movimentacoes); 
            for (int elemento : balde) {
                vetor[indiceVetor++] = elemento;
                movimentacoes[0]++; 
            }
        }
    }

    // Implementação do Quick Sort 
    private static void quickSort(List<Integer> balde, int inicio, int fim, int[] comparacoes, int[] movimentacoes) {
        if (inicio < fim) {
            int indicePivo = particionar(balde, inicio, fim, comparacoes, movimentacoes);
            quickSort(balde, inicio, indicePivo - 1, comparacoes, movimentacoes);
            quickSort(balde, indicePivo + 1, fim, comparacoes, movimentacoes);
        }
    }

    private static int particionar(List<Integer> balde, int inicio, int fim, int[] comparacoes, int[] movimentacoes) {
        int pivo = balde.get(fim);
        int i = (inicio - 1);
        for (int j = inicio; j < fim; j++) {
            comparacoes[0]++;
            if (balde.get(j) <= pivo) {
                i++;
                Collections.swap(balde, i, j);
                movimentacoes[0] += 3; // 3 trocas 
            }
        }
        Collections.swap(balde, i + 1, fim);
        movimentacoes[0] += 3; 
        return i + 1;
    }

 
    public static int[] carregarDados(String nomeArquivo) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(nomeArquivo));
        List<Integer> dadosLista = new ArrayList<>();
        while (scanner.hasNextInt()) {
            dadosLista.add(scanner.nextInt());
        }
        scanner.close();

        int[] dados = new int[dadosLista.size()];
        for (int i = 0; i < dadosLista.size(); i++) {
            dados[i] = dadosLista.get(i);
        }
        return dados;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String nomeArquivo = "C:\\Users\\Administrator\\Downloads\\dados500mil.txt"; // Substitua pelo caminho correto
        int[] dados = carregarDados(nomeArquivo);

        int[] comparacoes = new int[1];
        int[] movimentacoes = new int[1];
        long inicio = System.currentTimeMillis(); 
        bucketSort(dados, 10, comparacoes, movimentacoes); 
        long fim = System.currentTimeMillis(); 

        long tempoExecucao = fim - inicio;

       
        long horas = TimeUnit.MILLISECONDS.toHours(tempoExecucao);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tempoExecucao) % 60;
        long segundos = TimeUnit.MILLISECONDS.toSeconds(tempoExecucao) % 60;
        long milissegundos = tempoExecucao % 1000;
        String tempoFormatado = String.format("%02d:%02d:%02d:%03d", horas, minutos, segundos, milissegundos);

        System.out.println("==============BUCKET SORT=============");
        System.out.println("Tempo de execução do Bucket Sort: " + tempoFormatado);
        System.out.println("Comparações: " + comparacoes[0]);
        System.out.println("Movimentações: " + movimentacoes[0]);
        System.out.println(Arrays.toString(dados)); 
    }
 }

   