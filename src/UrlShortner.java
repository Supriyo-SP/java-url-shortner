import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class UrlShortner {
    private static HashMap<String,String> urlMap= new HashMap<>();
    private static Random random = new Random();
    private static String generateKey(){
        String chars ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index=random.nextInt(chars.length());
            key.append(chars.charAt(index));
        }
        return key.toString();
    }
    private static String shortenURL(String longURL){
        String shortKey =generateKey();
        urlMap.put(shortKey,longURL);
        return shortKey;

    }
    private static String resolveURL(String shortKey){
        return urlMap.get(shortKey);
    }

    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        while(true){
            System.out.println("\n--- URL Shortener ---");
            System.out.println("1. Shorten your URL");
            System.out.println("2. Resolve your short URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1 ->{
                    System.out.println("Enter the long URL: ");
                    String longURL=sc.nextLine();
                    String key =shortenURL(longURL);
                    System.out.println("Shortened URL:http://localhost:8080/"+ key);
                }
                case 2 -> {
                    System.out.print("Enter the short key: ");
                    String key = sc.nextLine();
                    String longUrl = resolveURL(key);
                    if (longUrl == null) {
                        System.out.println("No URL found for this key!");
                    } else {
                        System.out.println("Original URL: " + longUrl);
                    }
                }
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
