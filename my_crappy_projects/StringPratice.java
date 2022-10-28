package my_crappy_projects;

public class StringPratice {
    public static void main(String[] args){
        String[][] arr1 = {
            {"abc","abd"},
            {"abc","ab"},
            {"abc","abcd"},
            {"abc","abce"},
            {"abc","abcde"},
            {"abc","abcdef"},
            {"abc","abddef"},
            {"abc","fbcdef"},
            {"abc","fbc"},
            {"mashed","Potato"}
        };

        for(String[] things : arr1){
            System.out.println("\""+things[0]+"\".compareTo(\""+things[1]+"\") = "+things[0].compareTo(things[1]));
        }
    }
}
