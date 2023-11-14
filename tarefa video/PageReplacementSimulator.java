import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PageReplacementSimulator {
    private static final int RAM_SIZE = 10;
    private static final int SWAP_SIZE = 100;
    private static final int PAGE_PROB_MODIFICATION = 30;
    private static final int INSTRUCTIONS_TO_RUN = 1000;

    private static int[][] ram = new int[RAM_SIZE][6];
    private static int[][] swap = new int[SWAP_SIZE][6];

    public static void main(String[] args) {
        initializeMatrices();

        for (int instructionNumber = 1; instructionNumber <= INSTRUCTIONS_TO_RUN; instructionNumber++) {
            int instruction = getRandomNumber(1, 100);

            int pageIndex = findPageIndexInRAM(instruction);

            if (pageIndex != -1) {
                // Página encontrada na RAM
                ram[pageIndex][3] = 1; // Atualizar bit de acesso R

                if (shouldModifyPage()) {
                    ram[pageIndex][2]++; // Atualizar Dado (D)
                    ram[pageIndex][4] = 1; // Atualizar bit de modificação M
                }
            } else {
                // Página não encontrada na RAM, realizar substituição
                int victimPageIndex = nruAlgorithm();
                replacePage(victimPageIndex, instruction);
            }

            if (instructionNumber % 10 == 0) {
                resetRBit();
            }
        }

        printMatrices();
    }

    private static void initializeMatrices() {
        for (int i = 0; i < SWAP_SIZE; i++) {
            swap[i][0] = i; // N
            swap[i][1] = i + 1; // I
            swap[i][2] = getRandomNumber(1, 50); // D
            swap[i][3] = 0; // R
            swap[i][4] = 0; // M
            swap[i][5] = getRandomNumber(100, 9999); // T
        }

        List<Integer> swapIndices = new ArrayList<>();
        for (int i = 0; i < RAM_SIZE; i++) {
            swapIndices.add(i);
        }

        Collections.shuffle(swapIndices);

        for (int i = 0; i < RAM_SIZE; i++) {
            int swapIndex = swapIndices.get(i);
            System.arraycopy(swap[swapIndex], 0, ram[i], 0, 6);
        }
    }

    private static int findPageIndexInRAM(int instruction) {
        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i][1] == instruction) {
                return i;
            }
        }
        return -1;
    }

    private static boolean shouldModifyPage() {
        return getRandomNumber(1, 100) <= PAGE_PROB_MODIFICATION;
    }

    private static void resetRBit() {
        for (int i = 0; i < RAM_SIZE; i++) {
            ram[i][3] = 0;
        }
    }

    private static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void printMatrices() {
        System.out.println("MATRIZ RAM:");
        printMatrix(ram);

        System.out.println("\nMATRIZ SWAP:");
        printMatrix(swap);
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static int nruAlgorithm() {
        // Implemente o algoritmo NRU aqui
        // Este é um exemplo simples, você pode expandi-lo conforme necessário
        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i][3] == 0 && ram[i][4] == 0) {
                return i;
            }
        }

        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i][3] == 0 && ram[i][4] == 1) {
                return i;
            }
        }

        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i][3] == 1 && ram[i][4] == 0) {
                return i;
            }
        }

        for (int i = 0; i < RAM_SIZE; i++) {
            if (ram[i][3] == 1 && ram[i][4] == 1) {
                return i;
            }
        }

        // Caso padrão, retorna 0
        return 0;
    }

    private static void replacePage(int victimPageIndex, int newInstruction) {
        // Implementar a lógica de substituição de página aqui
        // Atualize a página na RAM com base na página na SWAP
        System.arraycopy(swap[newInstruction - 1], 0, ram[victimPageIndex], 0, 6);
    }
}