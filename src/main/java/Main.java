import service.WheaterService;

public class Main {
    public static void main(String[] args) {

        //test.
        //Reemplazar con %20 como en el ejemplo, los nombres de las ciudades que tengan espacios.

        WheaterService wheaterService=new WheaterService();

        System.out.println(wheaterService.findWheater("sfgdhgfd"));

    }
}