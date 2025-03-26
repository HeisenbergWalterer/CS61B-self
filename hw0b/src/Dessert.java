public class Dessert {
    public int flavor;
    public int price;
    static int numDesserts = 0; // 一般通过类名访问

    Dessert(int flavor, int price)
    {
        this.flavor = flavor;
        this.price = price;
        numDesserts++;
    }

    public void printDessert()
    {
        System.out.println(flavor + " " + price + " " + Dessert.numDesserts);
    }

    public static void main(String[] args)
    {
        System.out.println("I love dessert!");
    }


}
