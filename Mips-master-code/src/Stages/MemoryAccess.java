package Stages;

import Integrate.Main;

public class MemoryAccess {
    static int zero;
    static String rt;
    static String rd;
    static int ALUresult;
    static String readData1;
    static String readData2;
    static boolean[] signals;
    static String dataFromMem;

    public static Object[] memAccess(String[] memory, Object[] ALUreg){
        System.out.println("\nMemory Access......."+"\n");
        zero=(int)ALUreg[0];
        ALUresult=(int)ALUreg[2];
        readData1=(String)ALUreg[3];
        readData2=(String)ALUreg[4];
        rt=(String)ALUreg[5];
        rd=(String)ALUreg[6];
        signals=(boolean[])ALUreg[7];

        System.out.println("ALU result: "+ALUresult);
        if (!signals[3] && !signals[4]) {
            System.out.println("memory word read: xxxxxxx"
            +"\nrt/rd field: "+rt);
        }
        else{
            int add=ALUresult;
            if(signals[3]){
                dataFromMem=(memory[add]);
                System.out.println("memory word read: "+dataFromMem
                        +"\nrt/rd field: "+rt);
            }
            if (signals[4]){
                int wrtData=Integer.parseInt(readData2,2);
                String wrtD=intToBin(wrtData);
                memory[add]=wrtD;
                System.out.println("memory word read: "+readData2
                        +"\nrt/rd field: "+rt);
            }
        }
        System.out.println("WB controls: MemToReg: "+signals[6]+", RegWrite: "+signals[2]);
        Object[] reg=new Object[]{dataFromMem,rt,rd,readData2,ALUresult,zero,signals};
        return reg;
    }

    private static String intToBin(int n){
        String result=Integer.toBinaryString(n);
        while (result.length()<32){
            result="0"+result;
        }
        return result;
    }
}
