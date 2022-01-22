package Integrate;
import Stages.ALU;
import Stages.Fetch;
import Stages.InstructionDecode;
import Stages.MemoryAccess;
import Stages.WriteBack;

public class Main {
    public static Register[] regFile;
    public static String[] memory;
    public static String[] instructions= {"00000000000001000100010000000000","00000100000010000000100000000000","00001000000010000000100000000000","00001100000010000000100000000000","00010000000010000000100000000000","00010100000010000000100000000000","01011000000010000000100000000000","01011100000010000000100000000000","01100000000010000000100000000000","01100100000010000000100000000000","01101000000010000000100000000000","01101100000010000000100000000000","01110000000010000000100000000000","10110100000000000000000000000001"};
    public static boolean memBus=true;
    public static String[] cache=new String[100];
    static Object[][] pipRegs=new Object[4][];
    static int cycle=0;

    public static void main(String[] args) {
        Main n=new Main();
        n.initRegFile();
        n.initMem();
        n.initCache();
        while (Fetch.flag==1) {
            System.out.println("\nCycle: "+cycle);
            Object[] ifReg=Fetch.ProgCount();
            if(pipRegs[0]!=null){
                Object[] decodeReg=InstructionDecode.InstDecode(pipRegs[0],regFile);
                pipRegs[0]=ifReg;
                if(pipRegs[1]!=null){
                    Object[] ALUreg=ALU.Execute(pipRegs[1]);
                    pipRegs[1]=decodeReg;
                    if (pipRegs[2]!=null && memBus){
                        Object[] memReg=MemoryAccess.memAccess(memory,pipRegs[2]);
                        pipRegs[2]=ALUreg;
                        if (pipRegs[3]!=null){
                            WriteBack.writeBack(regFile,pipRegs[3]);
                            pipRegs[3]=memReg;
                        }else{
                            pipRegs[3]=memReg;
                        }
                    }else{
                        pipRegs[2]=ALUreg;
                    }
                }else{
                    pipRegs[1]=decodeReg;
                }

            }else{
                pipRegs[0]=ifReg;
            }
            cycle++;
        }
        for (int i = 0; i <4; i++) {
            System.out.println("\nCycle: "+cycle);
            Object[] ifReg=Fetch.ProgCount();
            if(pipRegs[0]!=null){
                Object[] decodeReg=InstructionDecode.InstDecode(pipRegs[0],regFile);
                pipRegs[0]=ifReg;
                if(pipRegs[1]!=null){
                    Object[] ALUreg=ALU.Execute(pipRegs[1]);
                    pipRegs[1]=decodeReg;
                    if (pipRegs[2]!=null && memBus){
                        Object[] memReg=MemoryAccess.memAccess(memory,pipRegs[2]);
                        pipRegs[2]=ALUreg;
                        if (pipRegs[3]!=null){
                            WriteBack.writeBack(regFile,pipRegs[3]);
                            pipRegs[3]=memReg;
                        }else{
                            pipRegs[3]=memReg;
                        }
                    }else{
                        pipRegs[2]=ALUreg;
                    }
                }else{
                    pipRegs[1]=decodeReg;
                }

            }else{
                pipRegs[0]=ifReg;
            }
            cycle++;
        }

        
    }


    public void initRegFile(){
        this.regFile=new Register[32];
        Register zero=new Register("zero","constant zero",0);
        this.regFile[0]=zero;
        for (int i = 0; i <16 ; i++) {
            Register argument=new Register("a"+Integer.toString(i),"Arguments",0);

            this.regFile[i+1]=argument;
        }
        for (int i = 17; i <28 ; i++) {
            Register temporary=new Register("t"+Integer.toString(i),"Temporaries",0);
            this.regFile[i]=temporary;
        }
        this.regFile[28]=new Register("gp","Global pointer",0);
        this.regFile[29]=new Register("sp","Stack pointer",0);
        this.regFile[30]=new Register("fp","frame pointer",0);
        this.regFile[31]=new Register("ra","return address",0);

    }

    public void initMem(){
        memory=new String[2048];
        for (int i = 0; i <1024 ; i++) {
            if (i<instructions.length)
                memory[i]=instructions[i];
        }
        for (int i = 1024; i <memory.length ; i++) {
            memory[i]="0";
        }
    }
    public void initCache(){
        int count=Integer.parseInt(Fetch.PCincrementedByOne,2);
        for (int i =count ; i <100+count ; i++) {
            if(memory[i]!=null && i<1024){
                cache[i]=memory[i];
            }
        }
    }


}
