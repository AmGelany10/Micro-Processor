package Stages;

import Integrate.Register;

public class InstructionDecode {
    String Inst;
    String nxtpc;
    static String Opcode;
    static String rs;
    static String ReadData1;
    static String ReadData2;
    static String rt;
    static String rd;
    static int shamt;
    static String immediate;
    static String address;
    static String SignExtend;
    static boolean[] signals;
    public static boolean PCsrc=false;
    public static boolean RegDst=false,RegWrite=false,ALUSrc=false,Branch=false,MemRead=false,MemWrite=false,MemToReg=false;

    public static Object[] InstDecode(Object[] IF_ID, Register[] regFile){
        System.out.println("\nDecode......."+"\n");
        String Inst=(String)IF_ID[1];
        String nxtpc=(String)IF_ID[0];
        immediate = Inst.substring(16,32);
        SignExtend(immediate);
        decode(Inst, regFile);
        ContUnit(Opcode);
        signals=new boolean[]{RegDst,ALUSrc,RegWrite,MemRead,MemWrite,Branch,MemToReg};
        Object [] PipReg = new Object[]{Opcode,ReadData1,ReadData2,rd,rt,rs,SignExtend,address,signals};
        System.out.println("sign-extend: "+SignExtend);
        System.out.println("Next-Pc : "+ nxtpc);
        System.out.println("rt : "+rt);
        System.out.println("rd : "+rd);
        System.out.println("WB controls: MemToReg: "+((MemToReg) ? 1 : 0)+", RegWrite: "+((RegWrite) ? 1 : 0));
        System.out.println("MEM controls: MemRead: "+((MemRead) ? 1 : 0)+", MemWrite: "+((MemWrite) ? 1 : 0)+", Branch: "+((Branch) ? 1 : 0));
        System.out.println("EX controls: RegDst: "+((RegDst) ? 1 : 0)+", ALUSrc: "+((ALUSrc) ? 1 : 0));
        return PipReg;
    }

    public static void decode(String Inst, Register[] regFile){
        Opcode = Inst.substring(0,6);
        int switchop = Integer.parseInt(Opcode.substring(0,2),2);

        switch(switchop){
            case 0 :
                /*R-Type*/
                rs = Inst.substring(6,11);
                ReadData1 = Integer.toBinaryString(regFile[Integer.parseInt(rs,2)].getValue());
                System.out.println("read data 1: "+ReadData1);
                rt =Inst.substring(11,16);
                ReadData2= Integer.toBinaryString(regFile[Integer.parseInt(rt,2)].getValue());
                System.out.println("read data 2: "+ReadData2);
                rd = Inst.substring(16,21);
                shamt = Integer.parseInt(Inst.substring(21),2);
                break;
            case 1 :
                /*I-Type*/
                rs = Inst.substring(6,11);
                ReadData1 = Integer.toBinaryString(regFile[Integer.parseInt(rs,2)].getValue()); /*To be Edited*/
                System.out.println("read data 1: "+ReadData1);
                rt =Inst.substring(11,16);
                ReadData2= Integer.toBinaryString(regFile[Integer.parseInt(rt,2)].getValue()); /*To be Edited*/
                System.out.println("read data 2: "+ReadData2);
                rd = "don't care";
               
                break;
            case 2 :
                /*J-Type*/
                address = Inst.substring(6,32);
                rs = "don't care";
                rt="don't care";
                rd = "don't care";
                break;
            default:

        }
    }

    public static void SignExtend(String immediate){
        String temp= immediate;
        char first = immediate.charAt(0);
        for(int i=0;i<16;i++){
            temp = first + temp;
        }
        SignExtend=temp;
    }

    public static void ContUnit(String opcode){
        switch(Integer.parseInt(opcode.substring(0, 2),2)){
            case 0 :
                /*R-type*/
                RegDst=true;
                ALUSrc=false;
                RegWrite=true;
                MemRead=false;
                MemWrite=false;
                Branch=false;
                MemToReg=false;
                PCsrc=false;
                break;
            case 1 :
                /*I-type*/
                switch(opcode.substring(2,6)){
                    case "0110" :
                        /*addim*/
                        RegDst=false;
                        ALUSrc=true;
                        RegWrite=true;
                        MemRead=false;
                        MemWrite=false;
                        Branch=false;
                        MemToReg=false;
                        PCsrc=false;
                        break;
                    case "0111" :
                        /*Andim*/
                        RegDst=false;
                        ALUSrc=true;
                        RegWrite=true;
                        MemRead=false;
                        MemWrite=false;
                        Branch=false;
                        MemToReg=false;
                        PCsrc=false;
                        break;
                    case "1000" :
                        /*Lw*/
                        RegDst=false;
                        ALUSrc=true;
                        RegWrite=true;
                        MemRead=true;
                        MemWrite=false;
                        Branch=false;
                        MemToReg=true;
                        PCsrc=false;
                        break;
                    case "1001" :
                        /*SW*/
                        ALUSrc=true;
                        RegWrite=false;
                        MemRead=false;
                        MemWrite=true;
                        Branch=false;
                        PCsrc=false;
                        break;
                    case "1010" :
                        /*BEQ*/
                        ALUSrc=false;
                        RegWrite=false;
                        MemRead=false;
                        MemWrite=false;
                        Branch=true;
                        PCsrc=false;
                        break;
                    case "1011" :
                        /*BLT*/
                        break;
                    case "1100" :
                        /*SLTim*/
                        RegDst=false;
                        ALUSrc=true;
                        RegWrite=true;
                        MemRead=false;
                        MemWrite=false;
                        Branch=false;
                        MemToReg=false;
                        PCsrc=false;
                        break;
                    default:

                }
            case 2 :
                /*J-type*/
                RegWrite=false;
                MemRead=false;
                MemWrite=false;
                PCsrc=true;
            default:
        }
    }
}
