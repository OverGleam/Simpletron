import java.io.File;

public class Practice {
    private int accumulator;
    private int instructionRegister;
    private int instructionCounter;
    private int operand;
    private int opcode;
    public boolean run = true;
    private final int[] memory = new int[100];
    public Practice(){
        execute();
    }
    public void fetch(){
        instructionRegister = memory[instructionCounter];

        opcode = instructionRegister / 100;
        operand = instructionRegister % 100;
    }
    public void message(){
        System.out.printf("Instruction Counter : %02d\n",instructionCounter);
        System.out.printf("Instruction Register : %04d\n",instructionRegister);
        System.out.printf("Accumulator : %04d\n",accumulator);
        System.out.printf("Opcode: %02d\n",opcode);
        System.out.printf("Operand : %02d\n",operand);
    }
    public void execute(){
        int count = 0;
        try{
            System.out.print("Enter a file: ");
            String filename = new java.util.Scanner(System.in).nextLine();
            java.util.Scanner scanner = new java.util.Scanner(new File("src/"+filename));

            while(scanner.hasNextLine()){
                memory[count] = Integer.parseInt(scanner.nextLine());
                count++;
            }
        }
        catch (Exception e){
            System.out.print(e);
        }
        while(run){
            fetch();
            message();
            memoryDump();
            OperationStrix(opcode,operand);
        }
    }
    int quotient = 0;
    boolean divideCount = true;
    int remainder = 0;
    public void OperationStrix(int opcode, int operand){
        boolean branching = false;
        switch (opcode){
            case 10:
                System.out.print("Enter number: ");
                int number = new java.util.Scanner(System.in).nextInt();
                memory[operand] = number;
                break;
            case 11:
                if(divideCount) {
                    System.out.printf("Write : %d\n", memory[operand]);
                    quotient = memory[operand];
                    divideCount = false ;
                }
                else{
                    System.out.printf("Write : %d\n", memory[operand]);
                    remainder = memory[operand];
                }
                break;
            case 20:
                accumulator = memory[operand];
                break;
            case 21:
                memory[operand] = accumulator;
                break;
            case 30:
                accumulator += memory[operand];
                break;
            case 31:
                accumulator -= memory[operand];
                break;
            case 40:
                instructionCounter = operand;
                branching = true;
                break;
            case 41:
                if(accumulator < 0) {
                    instructionCounter = operand;
                    branching = true;
                }
                break;
            case 42:
                if(accumulator == 0) {
                    instructionCounter = operand;
                    branching = true;
                }
                break;
            case 43:
                run = false;
                System.out.printf("Quotient : %d\nRemainder : %d ",quotient,remainder);
                break;
        }
        if(!branching){
            instructionCounter++;
        }
    }
    public void memoryDump(){
        System.out.println(" +    0       1       2       3       4       5       6       7       8       9");
        for(int tens=0;tens<100;tens+=10){
            System.out.printf("%02d\t",tens);
            for(int ones=0;ones<10;ones++){
                System.out.printf("%04d\t",memory[tens + ones]);
            }
            System.out.println();
        }
    }
    public static void main(String... args){
        Practice memory = new Practice();
    }

}
