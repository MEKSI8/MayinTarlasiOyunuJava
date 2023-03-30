import java.util.Random;
import java.util.Scanner;

public class MayinTarlasi {
    String[][] board;
    String[][] cleanBoard;



    public MayinTarlasi(int satir,int sutun){

        this.board= new String[satir][sutun];
        this.cleanBoard=new String[satir][sutun];

    }
    public void tahtaYazdir(String[][] temp){
        System.out.print("  ");
        for (int t = 0; t <temp[0].length ; t++){
            System.out.print(t+" ");
        }
        System.out.println();


        for (int i = 0; i < temp.length ; i++) {
            System.out.print(i+"[");

            for (int j = 0; j <temp[i].length ; j++) {

                System.out.print(temp[i][j]);
                if (j==temp[i].length-1){
                    System.out.print("]");
                }
                else {
                    System.out.print("|");
                }

            }
            System.out.println();

        }

    }

    //dizide var mı kontrolü. Mayın yerlerini belirlerken kullanıldı
    private static boolean check(int[] arr, int toCheckValue)
    {

        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]==toCheckValue){
                return true;
            }
        }

        return false;
    }

    public void tahtayıDuzle(String[][] temp){
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j]="-";
            }
        }
    }

    public String[][] tahtaDoldur(String[][] temp){
        //İlk olarak hepsini - yapıyoruz
        tahtayıDuzle(temp);

        int mayinSay = (temp.length*temp[0].length)/4;
        Random rand = new Random();
        int[] mayinYerleri = new int[mayinSay];
        int i=0;


        //Mayın yerlerinin random belirlenmesi

        while (mayinYerleri[mayinSay-1]==0){
            int rand_int = rand.nextInt(temp.length*temp[0].length);

            if (check(mayinYerleri,rand_int)==false){
                mayinYerleri[i]=rand_int;
                i++;
            }

        }
        //Mayınların tahtaya dizilmesi
        for (int j = 0; j < mayinYerleri.length; j++) {
            int satir=mayinYerleri[j]/temp[0].length;
            int sutun=(mayinYerleri[j]-satir*temp[0].length);

            temp[satir][sutun]="*";

        }

        return temp;
    }

    //verilen sayının tahtada var mı yok mu kontrolü
    public boolean tahtayaUygunMu(int x,String yön){
        if (x<0){
            return false;
        }
        if (yön=="satır"){

            if (x>=this.board.length){
                return false;
            }

        }else {
            if (x>=this.board[0].length){
                return false;
            }
        }

        return true;
    }


    public int yanindaBombaSay(int x,int y){
        int bombsay=0;
        //bulundugu satırın sağı solu
        if (tahtayaUygunMu(y-1,"sutun") && board[x][y-1]=="*"){
            bombsay+=1;
        }
        if (tahtayaUygunMu(y+1,"sutun") && board[x][y+1]=="*"){
            bombsay+=1;
        }
        //bir üst satır
        if (tahtayaUygunMu(x+1,"satır")){
            if (board[x+1][y]=="*"){
                bombsay+=1;
            }
            if (tahtayaUygunMu(y-1,"sutun") && board[x+1][y-1]=="*"){
                bombsay+=1;
            }
            if (tahtayaUygunMu(y+1,"sutun") && board[x+1][y+1]=="*"){
                bombsay+=1;
            }

        }

        //alt satır
        if (tahtayaUygunMu(x-1,"satır")){
            if (board[x-1][y]=="*"){
                bombsay+=1;
            }
            if (tahtayaUygunMu(y-1,"sutun") && board[x-1][y-1]=="*"){
                bombsay+=1;
            }
            if (tahtayaUygunMu(y+1,"sutun") && board[x-1][y+1]=="*"){
                bombsay+=1;
            }
        }

        return bombsay;
    }

    public void run(){
        tahtayıDuzle(cleanBoard);
        tahtaDoldur(board);

        tahtaYazdir(cleanBoard);
        int tur=1;

        boolean temp=true;
        while (temp){
            Scanner input = new Scanner(System.in);
            System.out.print("Satır Giriniz: ");
            int satir=input.nextInt();
            while (satir<0 || satir>=cleanBoard.length ){
                System.out.println("Lütfen 0 ile "+(board.length-1)+" arasında bir sayı girin(çıkmak için -1)");
                satir=input.nextInt();
                if (satir==-1){
                    break;
                }
            }
            System.out.print("Sütun Giriniz: ");
            int sutun=input.nextInt();
            while (sutun<0 || sutun>=cleanBoard[0].length){
                System.out.println("Lütfen 0 ile "+(board[0].length-1)+" arasında bir sayı girin(çıkmak için -1)");
                sutun=input.nextInt();
                if (sutun==-1){
                    break;
                }
            }
            if (satir==-1 || sutun==-1){
                temp=false;
                break;
            }


            if (this.board[satir][sutun]=="*"){
                System.out.println("GAME OVER");
                tahtaYazdir(board);
                temp=false;
            }
            else {
                cleanBoard[satir][sutun]=Integer.toString(yanindaBombaSay(satir,sutun));
                if (tur>=(board.length*board[0].length-(board.length*board[0].length/4))){
                    System.out.println("TEBRİKLER KAZANDINIZ");
                    tahtaYazdir(cleanBoard);
                    temp=false;
                    break;
                }
                tur+=1;


                tahtaYazdir(cleanBoard);
            }

        }



    }


}
